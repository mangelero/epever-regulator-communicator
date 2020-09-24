# **Epever Regulator Communicator**

Read/write to your Epever Solar Panel Controller/Regulator

**Fields that can be read/written had been separated into the following classes:**

#### **Examples:**

**Set date and time**

`
RegulatorCommunicator communicator = new RegulatorCommunicatorImpl("/dev/ttyXRUSB0");
communicator.connect();
communicator.setRTC(Instant.now());
`


**Read from Statistical Parameter**

`
RegulatorCommunicator communicator = new RegulatorCommunicatorImpl("/dev/ttyXRUSB0");
communicator.connect();
StatisticalParameter o = communicator.read(StatisticalParameter.class);
System.out.println(o.generatedEnergyTotal);
`


**Read Battery Setting Parameter**

`
RegulatorCommunicator communicator = new RegulatorCommunicatorImpl("/dev/ttyXRUSB0");
communicator.connect();
StatisticalParameter o = communicator.read(BatterySettingParameter.class);
System.out.println(o.batteryType);
` 

**Read data from all fields into raw data map**

    private void readAllData() {
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
    }

Note:
- Only tested on Epever 4210N, but should work on all models using the same protocol
- Only tested on linux

