package se.divdev.epever.examples;

import se.divdev.epever.api.RegulatorCommunicator;
import se.divdev.epever.core.RegulatorCommunicatorImpl;
import se.divdev.epever.api.RegulatorException;
import se.divdev.epever.api.data.BatterySettingParameter;

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
