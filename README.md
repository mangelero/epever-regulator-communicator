# **Epever Regulator Communicator**

Read/write to your Epever Solar Panel Controller/Regulator

**Fields that can be read/written had been separated into the following classes:**

* RealtimeData.class
* RealtimeStatus.class
* StatisticalParameter.class
* BatterySettingParameter.class
* SettingParameter.class
* Coils.class
* DiscreteInput.class
* RatedData.class

#### **Examples:**

**Set date and time**

    public class SetRtcExample {
    
        public static void main(String... args) throws Exception {
            new SetRtcExample().setRtc();
        }
    
        public void setRtc() throws RegulatorException {
            RegulatorCommunicator communicator = new RegulatorCommunicatorImpl("/dev/ttyXRUSB0");
            communicator.connect();
            communicator.setRtc(Instant.now());
            communicator.disconnect();
        }
    }

**Read from Statistical Parameter**
    
    public class ReadStatisticalParameterExample {
    
        public static void main(String... args) throws Exception {
            new ReadStatisticalParameterExample().readStatisticalParameter();
        }
    
        public void readStatisticalParameter() throws RegulatorException {
            RegulatorCommunicator communicator = new RegulatorCommunicatorImpl("/dev/ttyXRUSB0");
            communicator.connect();
            StatisticalParameter o = communicator.read(StatisticalParameter.class);
            System.out.println(o.generatedEnergyTotal);
            communicator.disconnect();
        }
    }


**Read Battery Setting Parameter**
    
    public class ReadBatterySettingsParameterExample {
    
        public static void main(String... args) throws Exception {
            new ReadBatterySettingsParameterExample().readBatterySettingsParameter();
        }
    
        public void readBatterySettingsParameter() throws RegulatorException {
            RegulatorCommunicator communicator = new RegulatorCommunicatorImpl("/dev/ttyXRUSB0");
            communicator.connect();
            BatterySettingParameter o = communicator.read(BatterySettingParameter.class);
            System.out.println(o.batteryType);
            communicator.disconnect();
        }
    }


**Write Battery Settings**
    
    public class WriteBatterySettingsExample {
    
        public static void main(String... args) throws Exception {
            new WriteBatterySettingsExample().writeBatterySettings();
        }
    
        public void writeBatterySettings() throws RegulatorException {
            RegulatorCommunicator communicator = new RegulatorCommunicatorImpl("/dev/ttyXRUSB0");
            communicator.connect();
    
            BatterySettingParameter settings = new BatterySettingParameter();
            settings.batteryType = BatteryType.FLOODED;
            settings.batteryCapacity = 200;
            settings.temperatureCompensationCoefficient = 3;
            settings.highVoltageDisconnect = 32;
            settings.chargingLimitVoltage = 30;
            settings.overVoltageReconnect = 30;
            settings.equalizationVoltage = 29.6;
            settings.boostVoltage = 29.2;
            settings.floatVoltage = 27.6;
            settings.boostReconnectVoltage = 26.4;
            settings.lowVoltageReconnect = 25.2;
            settings.underVoltageRecover = 24.4;
            settings.underVoltageWarning = 24;
            settings.lowVoltageDisconnect = 22.2;
            settings.dischargingLimitVoltage = 21.2;
    
            communicator.write(settings);
    
            communicator.disconnect();
        }
    }

**Read data from all fields into raw data map**

    public class ReadAllDataExample {
    
        public static void main(String... args) throws Exception {
            new ReadAllDataExample().readAllData();
        }
    
        public void readAllData() throws RegulatorException {
            RegulatorCommunicator communicator = new RegulatorCommunicatorImpl("/dev/ttyXRUSB0");
            communicator.connect();
            RegulatorRawData raw = new RegulatorRawData();
    
            Arrays.asList(
                    RealtimeData.class,
                    RealtimeStatus.class,
                    StatisticalParameter.class,
                    BatterySettingParameter.class,
                    SettingParameter.class,
                    Coils.class,
                    DiscreteInput.class,
                    RatedData.class
            ).stream()
                    .map(communicator::readRaw)
                    .forEach(raw::merge);
    
            System.out.println(raw);
            communicator.disconnect();
        }
    }

**Using ScheduledRegulatorCommunicator for scheduled reading**

    public class ScheduledRegulatorCommunicatorExample {
    
        public static void main(String... args) throws Exception {
    
            ScheduledRegulatorCommunicator communicator = new ScheduledRegulatorCommunicator(
                    new RegulatorCommunicatorImpl("/dev/ttyXRUSB0"), Executors.newScheduledThreadPool(2)
            );
    
            communicator.addCallback(ScheduledRegulatorCommunicatorExample::onData);
            communicator.start(Duration.ofSeconds(5));
    
            Thread.sleep(60_000);
            communicator.stop();
        }
    
        private static void onData(RegulatorRawData data) {
            System.out.println("Got raw data: " + data);
            SettingParameter settings = Regulator.from(data, SettingParameter.class);
            System.out.println("Regulator date/time: " + settings.regulatorDateTime);
        }
    }
    


Note:
- Only tested on Epever 4210N, but should work on all models using the same protocol
- Only tested on linux

