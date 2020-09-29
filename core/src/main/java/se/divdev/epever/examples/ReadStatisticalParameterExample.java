package se.divdev.epever.examples;

import se.divdev.epever.RegulatorCommunicator;
import se.divdev.epever.RegulatorCommunicatorImpl;
import se.divdev.epever.RegulatorException;
import se.divdev.epever.data.StatisticalParameter;

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
