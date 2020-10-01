package se.divdev.epever.api.data;

import se.divdev.epever.api.DataMapper.IntMapper;
import se.divdev.epever.api.EpeverMapper;

public class DiscreteInput {
    @EpeverMapper(address = 0x2000, clazz = IntMapper.class)
    public int overTemperatureInsideTheDevice;

    @EpeverMapper(address = 0x200C, clazz = IntMapper.class)
    public int dayNight;
}
