package se.divdev.epever.api.data;

import se.divdev.epever.api.DataMapper;
import se.divdev.epever.api.EpeverMapper;

public class RealtimeStatus {
    @EpeverMapper(address = 0x3200, clazz = DataMapper.BitMapper.class)
    public boolean[] batteryStatus;

    @EpeverMapper(address = 0x3201, clazz = DataMapper.BitMapper.class)
    public boolean[] chargingEquipmentStatus;
}
