package se.divdev.epever.data;

import se.divdev.epever.EpeverMapper;
import se.divdev.epever.DataMapper;

public class StatisticalParameter {
    @EpeverMapper(address = 0x3300)
    public double maximumInputVoltToday;

    @EpeverMapper(address = 0x3301)
    public double minimumInputVoltToday;

    @EpeverMapper(address = 0x3302)
    public double maximumBatteryVoltToday;

    @EpeverMapper(address = 0x3303)
    public double minimumBatteryVoltToday;

    @EpeverMapper(address = 0x3304, clazz = DataMapper.BigEndianDenominator100Mapper.class)
    public double consumedEnergyToday;

    @EpeverMapper(address = 0x3306, clazz = DataMapper.BigEndianDenominator100Mapper.class)
    public double consumedEnergyThisMonth;

    @EpeverMapper(address = 0x3308, clazz = DataMapper.BigEndianDenominator100Mapper.class)
    public double consumedEnergyThisYear;

    @EpeverMapper(address = 0x330A, clazz = DataMapper.BigEndianDenominator100Mapper.class)
    public double totalConsumedEnergy;

    @EpeverMapper(address = 0x330C, clazz = DataMapper.BigEndianDenominator100Mapper.class)
    public double generatedEnergyToday;

    @EpeverMapper(address = 0x330E, clazz = DataMapper.BigEndianDenominator100Mapper.class)
    public double generatedEnergyThisMonth;

    @EpeverMapper(address = 0x3310, clazz = DataMapper.BigEndianDenominator100Mapper.class)
    public double generatedEnergyThisYear;

    @EpeverMapper(address = 0x3312, clazz = DataMapper.BigEndianDenominator100Mapper.class)
    public double generatedEnergyTotal;

    @EpeverMapper(address = 0x3314, clazz = DataMapper.BigEndianDenominator100Mapper.class)
    public double carbonDioxideReduction;

    @EpeverMapper(address = 0x331B, clazz = DataMapper.BigEndianDenominator100Mapper.class)
    public double batteryCurrent;

    @EpeverMapper(address = 0x331D)
    public double batteryTemperature2;

    @EpeverMapper(address = 0x331E)
    public double ambientTemperature;
}
