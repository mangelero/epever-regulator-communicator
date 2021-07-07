package se.divdev.epever.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import se.divdev.epever.api.data.BatterySettingParameter;
import se.divdev.epever.api.data.SettingParameter;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegulatorCommunicatorTest {

    RegulatorCommunicator communicator = new RegulatorCommunicator() {

        RegulatorRawData data = new RegulatorRawData();

        @Override
        public void setRetryAttempts(int retries) {

        }

        @Override
        public void setSleepBetweenRetriesMs(int sleep) {

        }

        @Override
        public boolean isConnected() {
            return true;
        }

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
                Integer value = data.get(startAddress + i);
                if (value == null) {
                    continue;
                }
                result[i] = value;
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

    @Test
    public void testGetRawDataInClusters() throws Exception {
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

        RegulatorRawData raw = Regulator.toRaw(settings);
        System.out.println("RAW: " + raw);

        Map<Integer, List<Integer>> clusterData = raw.clusterData();
        System.out.println("Cluster: " + clusterData);
    }

}
