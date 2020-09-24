package se.divdev.epever.data;

import se.divdev.epever.EpeverMapper;
import se.divdev.epever.BatteryType;
import se.divdev.epever.DataMapper;

public class BatterySettingParameter {
    @EpeverMapper(address = 0x9000, clazz = DataMapper.BatteryTypeMapper.class)
    public BatteryType batteryType;

    @EpeverMapper(address = 0x9001, clazz = DataMapper.IntMapper.class)
    public int batteryCapacity;

    @EpeverMapper(address = 0x9002)
    public double temperatureCompensationCoefficient;

    @EpeverMapper(address = 0x9003)
    public double highVoltageDisconnect;

    @EpeverMapper(address = 0x9004)
    public double chargingLimitVoltage;

    @EpeverMapper(address = 0x9005)
    public double overVoltageReconnect;

    @EpeverMapper(address = 0x9006)
    public double equalizationVoltage;

    @EpeverMapper(address = 0x9007)
    public double boostVoltage;

    @EpeverMapper(address = 0x9008)
    public double floatVoltage;

    @EpeverMapper(address = 0x9009)
    public double boostReconnectVoltage;

    @EpeverMapper(address = 0x900A)
    public double lowVoltageReconnect;

    @EpeverMapper(address = 0x900B)
    public double underVoltageRecover;

    @EpeverMapper(address = 0x900C)
    public double underVoltageWarning;

    @EpeverMapper(address = 0x900D)
    public double lowVoltageDisconnect;

    @EpeverMapper(address = 0x900E)
    public double dischargingLimitVoltage;
}
