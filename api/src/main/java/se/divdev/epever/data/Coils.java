package se.divdev.epever.data;

import se.divdev.epever.EpeverMapper;
import se.divdev.epever.DataMapper;

public class Coils {
    @EpeverMapper(address = 0x02, clazz = DataMapper.IntMapper.class)
    public int manualControlTheLoad;

    @EpeverMapper(address = 0x05, clazz = DataMapper.IntMapper.class)
    public int enableLoadTestMode;

    @EpeverMapper(address = 0x06, clazz = DataMapper.IntMapper.class)
    public int forceLoadOnOff;
}
