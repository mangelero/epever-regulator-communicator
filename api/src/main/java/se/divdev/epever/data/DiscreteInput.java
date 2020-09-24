package se.divdev.epever.data;

import se.divdev.epever.EpeverMapper;
import se.divdev.epever.DataMapper.IntMapper;

public class DiscreteInput {
    @EpeverMapper(address = 0x2000, clazz = IntMapper.class)
    public int overTemperatureInsideTheDevice;

    @EpeverMapper(address = 0x200C, clazz = IntMapper.class)
    public int dayNight;
}
