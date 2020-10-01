package se.divdev.epever.api;


import org.junit.jupiter.api.Test;
import se.divdev.epever.api.DataMapper;
import se.divdev.epever.api.DataMapper.BigEndianDenominator100Mapper;
import se.divdev.epever.api.DataMapper.InstantMapper;

import java.time.Duration;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataMapperTest {

    @Test
    public void testInstantMapper() {
        InstantMapper mapper = DataMapper.getInstance(InstantMapper.class);
        Instant now = Instant.now();
        // Nanos are not handled in the regulator.. lets remove them
        now = now.minusNanos(now.getNano());
        int[] encoded = mapper.encode(now);
        Instant decoded = mapper.decode(encoded);

        System.out.println(decoded);
        assertEquals(now, decoded);
    }

    @Test
    public void testBigEndianDenominator100() {
        BigEndianDenominator100Mapper mapper = DataMapper.getInstance(BigEndianDenominator100Mapper.class);
        int[] expected = new int[]{1, 1};
        double value = mapper.decode(expected);
        System.out.println(value);
        assertEquals(655.37, value, 0.1d);
        int[] result = mapper.encode(value);
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], result[i]);
        }
    }

    @Test
    public void bitMapperTest() {
        DataMapper.BitMapper mapper = DataMapper.getInstance(DataMapper.BitMapper.class);
        int value = 65535;
        boolean expected[] = {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true};
        boolean[] result = mapper.decode(value);
        assertArrayEquals(expected, result);
        assertEquals(value, mapper.encode(result)[0]);

        int value2 = (int) (Math.random() * 60000);
        boolean[] decoded = mapper.decode(value2);
        assertEquals(value2, mapper.encode(decoded)[0]);
    }

    @Test
    public void durationMapperTest() {
        DataMapper.DurationMapper mapper = DataMapper.getInstance(DataMapper.DurationMapper.class);
        int i1 = 2560;
        int i2 = 768;

        Duration d1 = mapper.decode(i1);
        Duration d2 = mapper.decode(i2);

        System.out.println("D1: " + d1);
        System.out.println("D2: " + d2);

        assertEquals(i1, mapper.encode(d1)[0]);
        assertEquals(i2, mapper.encode(d2)[0]);
    }
}
