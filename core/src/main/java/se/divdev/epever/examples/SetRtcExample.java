package se.divdev.epever.examples;

import se.divdev.epever.RegulatorCommunicator;
import se.divdev.epever.RegulatorCommunicatorImpl;
import se.divdev.epever.RegulatorException;

import java.time.Instant;

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
