package se.divdev.epever.data;

import se.divdev.epever.EpeverMapper;
import se.divdev.epever.DataMapper;

public class RealtimeData {
    @EpeverMapper(address = 0x3100)
    public double chargingEquipmentInputVoltage;

    @EpeverMapper(address = 0x3101)
    public double chargingEquipmentInputCurrent;

    @EpeverMapper(address = 0x3102,  clazz = DataMapper.BigEndianDenominator100Mapper.class)
    public double chargingEquipmentInputPower;

    @EpeverMapper(address = 0x3104)
    public double chargingEquipmentOutputVoltage;

    @EpeverMapper(address = 0x3105)
    public double chargingEquipmentOutputCurrent;

    @EpeverMapper(address = 0x3106, clazz = DataMapper.BigEndianDenominator100Mapper.class)
    public double chargingEquipmentOutputPower;

    @EpeverMapper(address = 0x310C)
    public double dischargingEquipmentOutputVoltage;

    @EpeverMapper(address = 0x310D)
    public double dischargingEquipmentOutputCurrent;

    @EpeverMapper(address = 0x310E, clazz = DataMapper.BigEndianDenominator100Mapper.class)
    public double dischargingEquipmentOutputPower;

    @EpeverMapper(address = 0x3110)
    public double batteryTemperature;

    @EpeverMapper(address = 0x3111)
    public double temperatureInsideEquipment;

    @EpeverMapper(address = 0x3112)
    public double powerComponentsTemperature;

    @EpeverMapper(address = 0x311A)
    public double batterySOC;

    @EpeverMapper(address = 0x311B)
    public double remoteBatteryTemperature;

    @EpeverMapper(address = 0x311D)
    public double batteryRealRatedPower;
}
