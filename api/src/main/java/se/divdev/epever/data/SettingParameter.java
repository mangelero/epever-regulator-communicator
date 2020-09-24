package se.divdev.epever.data;

import se.divdev.epever.EpeverMapper;
import se.divdev.epever.DataMapper;
import se.divdev.epever.Timer;

import java.time.Duration;
import java.time.Instant;

public class SettingParameter {
    @EpeverMapper(address = 0x9013, clazz = DataMapper.InstantMapper.class)
    public Instant regulatorDateTime;

    @EpeverMapper(address = 0x9016)
    public double equalizationChargingCycle;

    @EpeverMapper(address = 0x9017)
    public double batteryTemperatureWarningUpperLimit;

    @EpeverMapper(address = 0x9018)
    public double batteryTemperatureWarningLowerLimit;

    @EpeverMapper(address = 0x9019)
    public double controllerInnerTemperatureUpperLimit;

    @EpeverMapper(address = 0x901A)
    public double controllerInnerTemperatureUpperLimitRecover;

    @EpeverMapper(address = 0x901B)
    public double powerComponentTemperatureUpperLimit;

    @EpeverMapper(address = 0x901C)
    public double powerComponentTemperatureUpperLimitRecover;

    @EpeverMapper(address = 0x901D)
    public double lineImpedance;

    @EpeverMapper(address = 0x901E)
    public double nightTimeThresholdVolt;

    @EpeverMapper(address = 0x901F, clazz = DataMapper.IntMapper.class)
    public int lightSignalStartupDelayTime;

    @EpeverMapper(address = 0x9020)
    public double dayTimeThresholdVolt;

    @EpeverMapper(address = 0x9021, clazz = DataMapper.IntMapper.class)
    public int lightSignalTurnOffDelayTime;

    @EpeverMapper(address = 0x903D, clazz = DataMapper.IntMapper.class)
    public int loadControllingModes;

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
    public int batteryRatedVoltage;

    @EpeverMapper(address = 0x9069, clazz = DataMapper.IntMapper.class)
    public int loadTimingControlSelection;

    @EpeverMapper(address = 0x906A, clazz = DataMapper.IntMapper.class)
    public int defaultLoadOnOffInManualMode;

    @EpeverMapper(address = 0x906B, clazz = DataMapper.IntMapper.class)
    public int equalizeDurationInMinutes;

    @EpeverMapper(address = 0x906C, clazz = DataMapper.IntMapper.class)
    public int boostDurationInMinutes;

    @EpeverMapper(address = 0x906D)
    public double dischargingPercentage;

    @EpeverMapper(address = 0x906E)
    public double chargingPercentage;
}
