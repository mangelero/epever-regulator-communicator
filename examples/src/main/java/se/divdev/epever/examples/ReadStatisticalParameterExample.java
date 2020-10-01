package se.divdev.epever.examples;

import se.divdev.epever.api.RegulatorCommunicator;
import se.divdev.epever.core.RegulatorCommunicatorImpl;
import se.divdev.epever.api.RegulatorException;
import se.divdev.epever.api.data.StatisticalParameter;

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
