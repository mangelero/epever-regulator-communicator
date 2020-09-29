package se.divdev.epever;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import se.divdev.epever.data.BatterySettingParameter;
import se.divdev.epever.data.SettingParameter;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegulatorCommunicatorTest {

    RegulatorCommunicator communicator = new RegulatorCommunicator() {

        RegulatorRawData data = new RegulatorRawData();

        @Override
        public void connect() {
            data.clear();
        }

        @Override
        public void disconnect() {
            data.clear();
        }

        @Override
        public int[] read(int startAddress, int quantity) {
            int[] result = new int[quantity];
            for (int i = 0; i < quantity; i++) {
                result[i] = data.get(startAddress + i);
            }
            return result;
        }

        @Override
        public boolean write(int[] data, int startAddress) {
            for (int i = 0; i < data.length; i++) {
                this.data.put(startAddress + i, data[i]);
            }
            return true;
        }
    };

    @Test
    public void testReadAndWriteObject() throws Exception {
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

        communicator.connect();
        communicator.write(settings);

        BatterySettingParameter settings2 = communicator.read(BatterySettingParameter.class);

        Regulator.fieldInformation(BatterySettingParameter.class).forEach(field -> {
                    try {
                        Object o1 = field.field.get(settings);
                        Object o2 = field.field.get(settings2);
                        System.out.println(o1 + " = " + o2);
                        assertEquals(o1, o2);
                    } catch (IllegalAccessException e) {
                        Assertions.fail(e.getMessage());
                    }
                }
        );

        communicator.disconnect();
    }

    @Test
    public void testSetRtc() throws Exception {
        communicator.connect();
        Instant expected = Instant.now();
        // Trim off the nanos
        expected = expected.minusNanos(expected.getNano());
        communicator.setRtc(expected);
        SettingParameter settingParameter = communicator.read(SettingParameter.class);
        assertEquals(expected, settingParameter.regulatorDateTime);
        communicator.disconnect();
    }

}
