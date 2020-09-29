package se.divdev.epever;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import se.divdev.epever.data.BatterySettingParameter;
import se.divdev.epever.data.Coils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegulatorTest {

    @Test
    public void testRegulatorUtils() {
        List<Regulator.ClusterInformation> clusters = Regulator.clusterInformation(Coils.class);
        clusters.forEach(System.out::println);
        assertEquals(2, clusters.size());
    }

    @Test
    public void testToRaw() {
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

        RegulatorRawData data = Regulator.toRaw(settings);
        System.out.println(data);

        BatterySettingParameter settings2 = Regulator.from(data, BatterySettingParameter.class);
        Regulator.fieldInformation(BatterySettingParameter.class).forEach(field -> {
                    try {
                        Object o1 = field.field.get(settings);
                        Object o2 = field.field.get(settings2);
                        System.out.println(o1 + " = " + o2);
                        Assertions.assertEquals(o1, o2);
                    } catch (IllegalAccessException e) {
                        Assertions.fail(e.getMessage());
                    }
                }
        );
    }

}
