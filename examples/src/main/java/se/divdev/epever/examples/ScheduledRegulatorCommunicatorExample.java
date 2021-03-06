package se.divdev.epever.examples;

import se.divdev.epever.api.Regulator;
import se.divdev.epever.core.RegulatorCommunicatorImpl;
import se.divdev.epever.api.RegulatorRawData;
import se.divdev.epever.core.ScheduledRegulatorCommunicator;
import se.divdev.epever.api.data.SettingParameter;

import java.time.Duration;
import java.util.concurrent.Executors;

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

