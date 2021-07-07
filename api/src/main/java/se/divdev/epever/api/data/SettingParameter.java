package se.divdev.epever.api.data;

import se.divdev.epever.api.DataMapper;
import se.divdev.epever.api.EpeverMapper;
import se.divdev.epever.api.Timer;

import java.time.Duration;
import java.time.Instant;

public class SettingParameter {
    @EpeverMapper(address = 0x9013, clazz = DataMapper.InstantMapper.class)
    public Instant regulatorDateTime;

    @EpeverMapper(address = 0x9016)
    public Double equalizationChargingCycle;

    @EpeverMapper(address = 0x9017, clazz = DataMapper.SignedDenominator100Mapper.class)
    public Double batteryTemperatureWarningUpperLimit;

    @EpeverMapper(address = 0x9018, clazz = DataMapper.SignedDenominator100Mapper.class)
    public Double batteryTemperatureWarningLowerLimit;

    @EpeverMapper(address = 0x9019, clazz = DataMapper.SignedDenominator100Mapper.class)
    public Double controllerInnerTemperatureUpperLimit;

    @EpeverMapper(address = 0x901A, clazz = DataMapper.SignedDenominator100Mapper.class)
    public Double controllerInnerTemperatureUpperLimitRecover;

    @EpeverMapper(address = 0x901B, clazz = DataMapper.SignedDenominator100Mapper.class)
    public Double powerComponentTemperatureUpperLimit;

    @EpeverMapper(address = 0x901C, clazz = DataMapper.SignedDenominator100Mapper.class)
    public Double powerComponentTemperatureUpperLimitRecover;

    @EpeverMapper(address = 0x901D)
    public Double lineImpedance;

    @EpeverMapper(address = 0x901E)
    public Double nightTimeThresholdVolt;

    @EpeverMapper(address = 0x901F, clazz = DataMapper.IntMapper.class)
    public Integer lightSignalStartupDelayTime;

    @EpeverMapper(address = 0x9020)
    public Double dayTimeThresholdVolt;

    @EpeverMapper(address = 0x9021, clazz = DataMapper.IntMapper.class)
    public Integer lightSignalTurnOffDelayTime;

    @EpeverMapper(address = 0x903D, clazz = DataMapper.IntMapper.class)
    public Integer loadControllingModes;

    @EpeverMapper(address = 0x903E, clazz = DataMapper.DurationMapper.class)
    public Duration workingTimeLength1;

    @EpeverMapper(address = 0x903F, clazz = DataMapper.DurationMapper.class)
    public Duration workingTimeLength2;

    @EpeverMapper(address = 0x9042, clazz = DataMapper.TimerMapper.class)
    public Timer turnOnTiming1;

    @EpeverMapper(address = 0x9045, clazz = DataMapper.TimerMapper.class)
    public Timer turnOffTiming1;

    @EpeverMapper(address = 0x9048, clazz = DataMapper.TimerMapper.class)
    public Timer turnOnTiming2;

    @EpeverMapper(address = 0x904B, clazz = DataMapper.TimerMapper.class)
    public Timer turnOffTiming2;

    @EpeverMapper(address = 0x9065, clazz = DataMapper.DurationMapper.class)
    public Duration lengthOfNight;

    @EpeverMapper(address = 0x9067, clazz = DataMapper.IntMapper.class)
    public Integer batteryRatedVoltage;

    @EpeverMapper(address = 0x9069, clazz = DataMapper.IntMapper.class)
    public Integer loadTimingControlSelection;

    @EpeverMapper(address = 0x906A, clazz = DataMapper.IntMapper.class)
    public Integer defaultLoadOnOffInManualMode;

    @EpeverMapper(address = 0x906B, clazz = DataMapper.IntMapper.class)
    public Integer equalizeDurationInMinutes;

    @EpeverMapper(address = 0x906C, clazz = DataMapper.IntMapper.class)
    public Integer boostDurationInMinutes;

    @EpeverMapper(address = 0x906D)
    public Double dischargingPercentage;

    @EpeverMapper(address = 0x906E)
    public Double chargingPercentage;
}
