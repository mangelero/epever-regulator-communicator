package se.divdev.epever.examples;

import se.divdev.epever.api.RegulatorCommunicator;
import se.divdev.epever.core.RegulatorCommunicatorImpl;
import se.divdev.epever.api.RegulatorException;
import se.divdev.epever.api.RegulatorRawData;
import se.divdev.epever.api.data.*;

import java.util.Arrays;

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
