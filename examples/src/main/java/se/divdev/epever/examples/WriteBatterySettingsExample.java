package se.divdev.epever.examples;

import se.divdev.epever.api.BatteryType;
import se.divdev.epever.api.RegulatorCommunicator;
import se.divdev.epever.core.RegulatorCommunicatorImpl;
import se.divdev.epever.api.RegulatorException;
import se.divdev.epever.api.data.BatterySettingParameter;

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
        settings.temperatureCompensationCoefficient = 3D;
        settings.highVoltageDisconnect = 32D;
        settings.chargingLimitVoltage = 30D;
        settings.overVoltageReconnect = 30D;
        settings.equalizationVoltage = 29.6;
        settings.boostVoltage = 29.2;
        settings.floatVoltage = 27.6;
        settings.boostReconnectVoltage = 26.4;
        settings.lowVoltageReconnect = 25.2;
        settings.underVoltageRecover = 24.4;
        settings.underVoltageWarning = 24D;
        settings.lowVoltageDisconnect = 22.2;
        settings.dischargingLimitVoltage = 21.2;

        communicator.write(settings);

        communicator.disconnect();
    }
}
