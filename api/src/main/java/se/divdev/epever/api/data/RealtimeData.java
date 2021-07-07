package se.divdev.epever.api.data;

import se.divdev.epever.api.DataMapper;
import se.divdev.epever.api.EpeverMapper;

public class RealtimeData {
    @EpeverMapper(address = 0x3100)
    public Double chargingEquipmentInputVoltage;

    @EpeverMapper(address = 0x3101)
    public Double chargingEquipmentInputCurrent;

    @EpeverMapper(address = 0x3102, clazz = DataMapper.BigEndianDenominator100Mapper.class)
    public Double chargingEquipmentInputPower;

    @EpeverMapper(address = 0x3104)
    public Double chargingEquipmentOutputVoltage;

    @EpeverMapper(address = 0x3105)
    public Double chargingEquipmentOutputCurrent;

    @EpeverMapper(address = 0x3106, clazz = DataMapper.BigEndianDenominator100Mapper.class)
    public Double chargingEquipmentOutputPower;

    @EpeverMapper(address = 0x310C)
    public Double dischargingEquipmentOutputVoltage;

    @EpeverMapper(address = 0x310D)
    public Double dischargingEquipmentOutputCurrent;

    @EpeverMapper(address = 0x310E, clazz = DataMapper.BigEndianDenominator100Mapper.class)
    public Double dischargingEquipmentOutputPower;

    @EpeverMapper(address = 0x3110, clazz = DataMapper.SignedDenominator100Mapper.class)
    public Double batteryTemperature;

    @EpeverMapper(address = 0x3111, clazz = DataMapper.SignedDenominator100Mapper.class)
    public Double temperatureInsideEquipment;

    @EpeverMapper(address = 0x3112, clazz = DataMapper.SignedDenominator100Mapper.class)
    public Double powerComponentsTemperature;

    @EpeverMapper(address = 0x311A)
    public Double batterySOC;

    @EpeverMapper(address = 0x311B)
    public Double remoteBatteryTemperature;

    @EpeverMapper(address = 0x311D)
    public Double batteryRealRatedPower;
}
