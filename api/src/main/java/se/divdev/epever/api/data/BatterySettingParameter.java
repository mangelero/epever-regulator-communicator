package se.divdev.epever.api.data;

import se.divdev.epever.api.BatteryType;
import se.divdev.epever.api.DataMapper;
import se.divdev.epever.api.EpeverMapper;

public class BatterySettingParameter {
    @EpeverMapper(address = 0x9000, clazz = DataMapper.BatteryTypeMapper.class)
    public BatteryType batteryType;

    @EpeverMapper(address = 0x9001, clazz = DataMapper.IntMapper.class)
    public Integer batteryCapacity;

    @EpeverMapper(address = 0x9002, clazz = DataMapper.SignedDenominator100Mapper.class)
    public Double temperatureCompensationCoefficient;

    @EpeverMapper(address = 0x9003)
    public Double highVoltageDisconnect;

    @EpeverMapper(address = 0x9004)
    public Double chargingLimitVoltage;

    @EpeverMapper(address = 0x9005)
    public Double overVoltageReconnect;

    @EpeverMapper(address = 0x9006)
    public Double equalizationVoltage;

    @EpeverMapper(address = 0x9007)
    public Double boostVoltage;

    @EpeverMapper(address = 0x9008)
    public Double floatVoltage;

    @EpeverMapper(address = 0x9009)
    public Double boostReconnectVoltage;

    @EpeverMapper(address = 0x900A)
    public Double lowVoltageReconnect;

    @EpeverMapper(address = 0x900B)
    public Double underVoltageRecover;

    @EpeverMapper(address = 0x900C)
    public Double underVoltageWarning;

    @EpeverMapper(address = 0x900D)
    public Double lowVoltageDisconnect;

    @EpeverMapper(address = 0x900E)
    public Double dischargingLimitVoltage;

    @Override
    public String toString() {
        return "BatterySettingParameter{" +
                "batteryType=" + batteryType +
                ", batteryCapacity=" + batteryCapacity +
                ", temperatureCompensationCoefficient=" + temperatureCompensationCoefficient +
                ", highVoltageDisconnect=" + highVoltageDisconnect +
                ", chargingLimitVoltage=" + chargingLimitVoltage +
                ", overVoltageReconnect=" + overVoltageReconnect +
                ", equalizationVoltage=" + equalizationVoltage +
                ", boostVoltage=" + boostVoltage +
                ", floatVoltage=" + floatVoltage +
                ", boostReconnectVoltage=" + boostReconnectVoltage +
                ", lowVoltageReconnect=" + lowVoltageReconnect +
                ", underVoltageRecover=" + underVoltageRecover +
                ", underVoltageWarning=" + underVoltageWarning +
                ", lowVoltageDisconnect=" + lowVoltageDisconnect +
                ", dischargingLimitVoltage=" + dischargingLimitVoltage +
                '}';
    }
}
