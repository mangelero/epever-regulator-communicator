package se.divdev.epever.api.data;

import se.divdev.epever.api.DataMapper;
import se.divdev.epever.api.EpeverMapper;

public class Coils {
    @EpeverMapper(address = 0x02, clazz = DataMapper.IntMapper.class)
    public int manualControlTheLoad;

    @EpeverMapper(address = 0x05, clazz = DataMapper.IntMapper.class)
    public int enableLoadTestMode;

    @EpeverMapper(address = 0x06, clazz = DataMapper.IntMapper.class)
    public int forceLoadOnOff;
}
