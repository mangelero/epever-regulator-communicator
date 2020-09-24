package se.divdev.epever.data;

import se.divdev.epever.EpeverMapper;
import se.divdev.epever.DataMapper;

public class RatedData {
    @EpeverMapper(address = 0x3000)
    public double chargingEquipmentRatedInputVoltage;

    @EpeverMapper(address = 0x3001)
    public double chargingEquipmentRatedInputCurrent;

    @EpeverMapper(address = 0x3002, clazz = DataMapper.BigEndianDenominator100Mapper.class)
    public double chargingEquipmentRatedInputPower;

    @EpeverMapper(address = 0x3004)
    public double chargingEquipmentRatedOutputVoltage;

    @EpeverMapper(address = 0x3005)
    public double chargingEquipmentRatedOutputCurrent;

    @EpeverMapper(address = 0x3006)
    public double chargingEquipmentRatedOutputPower;

    @EpeverMapper(address = 0x3008, clazz = DataMapper.ChargingModeMapper.class)
    public String chargingMode;

    @EpeverMapper(address = 0x300E)
    public double ratedOutputCurrentOfLoad;
}
