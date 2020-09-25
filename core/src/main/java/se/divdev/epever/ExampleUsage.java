package se.divdev.epever;

import se.divdev.epever.data.*;

import java.time.Instant;
import java.util.Arrays;

public class ExampleUsage {

    public static void main(String... args) {

    }

    public void setRtc() throws RegulatorException {
        RegulatorCommunicator communicator = new RegulatorCommunicatorImpl("/dev/ttyXRUSB0");
        communicator.connect();
        communicator.setRTC(Instant.now());
        communicator.disconnect();
    }

    public void readStatisticalParameter() throws RegulatorException {
        RegulatorCommunicator communicator = new RegulatorCommunicatorImpl("/dev/ttyXRUSB0");
        communicator.connect();
        StatisticalParameter o = communicator.read(StatisticalParameter.class);
        System.out.println(o.generatedEnergyTotal);
        communicator.disconnect();
    }

    public void readBatterySettingParameter() throws RegulatorException {
        RegulatorCommunicator communicator = new RegulatorCommunicatorImpl("/dev/ttyXRUSB0");
        communicator.connect();
        BatterySettingParameter o = communicator.read(BatterySettingParameter.class);
        System.out.println(o.batteryType);
        communicator.disconnect();
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
