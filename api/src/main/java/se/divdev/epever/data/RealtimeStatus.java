package se.divdev.epever.data;

import se.divdev.epever.EpeverMapper;
import se.divdev.epever.DataMapper;

public class RealtimeStatus {
    @EpeverMapper(address = 0x3200, clazz = DataMapper.BitMapper.class)
    public boolean[] batteryStatus;

    @EpeverMapper(address = 0x3201, clazz = DataMapper.BitMapper.class)
    public boolean[] chargingEquipmentStatus;
}
