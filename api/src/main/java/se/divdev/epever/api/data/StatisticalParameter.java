package se.divdev.epever.api.data;

import se.divdev.epever.api.DataMapper;
import se.divdev.epever.api.EpeverMapper;

public class StatisticalParameter {
    @EpeverMapper(address = 0x3300)
    public Double maximumInputVoltToday;

    @EpeverMapper(address = 0x3301)
    public Double minimumInputVoltToday;

    @EpeverMapper(address = 0x3302)
    public Double maximumBatteryVoltToday;

    @EpeverMapper(address = 0x3303)
    public Double minimumBatteryVoltToday;

    @EpeverMapper(address = 0x3304, clazz = DataMapper.BigEndianDenominator100Mapper.class)
    public Double consumedEnergyToday;

    @EpeverMapper(address = 0x3306, clazz = DataMapper.BigEndianDenominator100Mapper.class)
    public Double consumedEnergyThisMonth;

    @EpeverMapper(address = 0x3308, clazz = DataMapper.BigEndianDenominator100Mapper.class)
    public Double consumedEnergyThisYear;

    @EpeverMapper(address = 0x330A, clazz = DataMapper.BigEndianDenominator100Mapper.class)
    public Double totalConsumedEnergy;

    @EpeverMapper(address = 0x330C, clazz = DataMapper.BigEndianDenominator100Mapper.class)
    public Double generatedEnergyToday;

    @EpeverMapper(address = 0x330E, clazz = DataMapper.BigEndianDenominator100Mapper.class)
    public Double generatedEnergyThisMonth;

    @EpeverMapper(address = 0x3310, clazz = DataMapper.BigEndianDenominator100Mapper.class)
    public Double generatedEnergyThisYear;

    @EpeverMapper(address = 0x3312, clazz = DataMapper.BigEndianDenominator100Mapper.class)
    public Double generatedEnergyTotal;

    @EpeverMapper(address = 0x3314, clazz = DataMapper.BigEndianDenominator100Mapper.class)
    public Double carbonDioxideReduction;

    @EpeverMapper(address = 0x331B, clazz = DataMapper.BigEndianDenominator100Mapper.class)
    public Double batteryCurrent;

    @EpeverMapper(address = 0x331D, clazz = DataMapper.SignedDenominator100Mapper.class)
    public Double batteryTemperature2;

    @EpeverMapper(address = 0x331E, clazz = DataMapper.SignedDenominator100Mapper.class)
    public Double ambientTemperature;
}
