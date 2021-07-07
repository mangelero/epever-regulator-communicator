package se.divdev.epever.api.data;

import se.divdev.epever.api.DataMapper;
import se.divdev.epever.api.EpeverMapper;

public class Coils {
    @EpeverMapper(address = 0x02, clazz = DataMapper.IntMapper.class)
    public Integer manualControlTheLoad;

    @EpeverMapper(address = 0x05, clazz = DataMapper.IntMapper.class)
    public Integer enableLoadTestMode;

    @EpeverMapper(address = 0x06, clazz = DataMapper.IntMapper.class)
    public Integer forceLoadOnOff;
}
