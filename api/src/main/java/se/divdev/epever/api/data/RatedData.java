package se.divdev.epever.api.data;

import se.divdev.epever.api.DataMapper;
import se.divdev.epever.api.EpeverMapper;

public class RatedData {
    @EpeverMapper(address = 0x3000)
    public Double chargingEquipmentRatedInputVoltage;

    @EpeverMapper(address = 0x3001)
    public Double chargingEquipmentRatedInputCurrent;

    @EpeverMapper(address = 0x3002, clazz = DataMapper.BigEndianDenominator100Mapper.class)
    public Double chargingEquipmentRatedInputPower;

    @EpeverMapper(address = 0x3004)
    public Double chargingEquipmentRatedOutputVoltage;

    @EpeverMapper(address = 0x3005)
    public Double chargingEquipmentRatedOutputCurrent;

    @EpeverMapper(address = 0x3006)
    public Double chargingEquipmentRatedOutputPower;

    @EpeverMapper(address = 0x3008, clazz = DataMapper.ChargingModeMapper.class)
    public String chargingMode;

    @EpeverMapper(address = 0x300E)
    public Double ratedOutputCurrentOfLoad;
}
