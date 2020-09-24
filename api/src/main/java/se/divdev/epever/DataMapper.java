package se.divdev.epever;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface DataMapper<T> {
    Map<Class<? extends DataMapper>, DataMapper> CACHE = new ConcurrentHashMap<>();

    T decode(int... data);

    int quantity();

    default int[] encode(T data) {
        throw new UnsupportedOperationException();
    }

    static <M extends DataMapper> M getInstance(Class<M> clazz) {
        return (M) CACHE.computeIfAbsent(clazz, c -> {
            try {
                return clazz.getConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    class IntMapper implements DataMapper<Integer> {
        @Override
        public Integer decode(int... data) {
            return data[0];
        }

        @Override
        public int[] encode(Integer data) {
            return new int[]{data};
        }

        @Override
        public int quantity() {
            return 1;
        }
    }

    class Denominator100Mapper implements DataMapper<Double> {
        @Override
        public Double decode(int... data) {
            return (double) data[0] / 100.d;
        }

        @Override
        public int[] encode(Double data) {
            return new int[]{(int) (data * 100.d)};
        }

        @Override
        public int quantity() {
            return 1;
        }
    }

    class BigEndianDenominator100Mapper extends Denominator100Mapper {
        @Override
        public Double decode(int... data) {
            return super.decode((data[1] << 16) + data[0]);
        }

        @Override
        public int[] encode(Double data) {
            int dataToUse = super.encode(data)[0];
            return new int[]{dataToUse & 0xFF, dataToUse >> 16};
        }

        @Override
        public int quantity() {
            return 2;
        }
    }

    class ChargingModeMapper implements DataMapper<String> {
        @Override
        public String decode(int... data) {
            if (data[0] == 1) {
                return "PWM";
            }
            return "Unknown";
        }

        @Override
        public int quantity() {
            return 1;
        }
    }

    class BatteryTypeMapper implements DataMapper<BatteryType> {
        @Override
        public BatteryType decode(int... data) {
            if (data[0] >= BatteryType.values().length) {
                return null;
            }
            return BatteryType.values()[data[0]];
        }

        @Override
        public int[] encode(BatteryType data) {
            return new int[]{data.ordinal()};
        }

        @Override
        public int quantity() {
            return 1;
        }
    }

    class InstantMapper implements DataMapper<Instant> {
        @Override
        public Instant decode(int... data) {
            int seconds = data[0] & 0xFF;
            int minutes = (data[0] >> 8) & 0xFF;

            int hour = data[1] & 0xFF;
            int day = (data[1] >> 8) & 0xFF;

            int month = data[2] & 0xFF;
            int year = (data[2] >> 8) & 0xFF;

            return ZonedDateTime.of(2000 + year, month, day, hour, minutes, seconds, 0, ZoneId.systemDefault()).toInstant();
        }

        @Override
        public int[] encode(Instant data) {
            ZonedDateTime date = data.atZone(ZoneId.systemDefault());
            int result[] = new int[quantity()];
            result[0] = (date.getMinute() << 8) + date.getSecond();
            result[1] = (date.getDayOfMonth() << 8) + date.getHour();
            result[2] = ((date.getYear() - 2000) << 8) + date.getMonthValue();
            return result;
        }

        @Override
        public int quantity() {
            return 3;
        }
    }

    class TimerMapper implements DataMapper<Timer> {

        @Override
        public Timer decode(int... data) {
            return new Timer(data[0], data[1], data[2]);
        }

        @Override
        public int[] encode(Timer data) {
            return new int[]{data.second, data.minute, data.hour};
        }

        @Override
        public int quantity() {
            return 3;
        }
    }

    class BitMapper implements DataMapper<boolean[]> {
        @Override
        public boolean[] decode(int... data) {
            boolean[] result = new boolean[quantity() * 16];
            for (int i = 0; i < result.length; i++) {
                result[i] = ((data[0] >> i) & 1) == 1;
            }
            return result;
        }

        @Override
        public int quantity() {
            return 1;
        }

        @Override
        public int[] encode(boolean[] data) {
            int result = 0;
            for (int i = 0; i < data.length; i++) {
                if (data[i]) {
                    result += 1 << i;
                }
            }
            return new int[]{result};
        }
    }

    class DurationMapper implements DataMapper<Duration> {

        @Override
        public Duration decode(int... data) {
            int minutes = data[0] & 0xFF;
            int hours = (data[0] >> 8) & 0xFF;
            return Duration.ofMinutes(hours * 60 + minutes);
        }

        @Override
        public int quantity() {
            return 1;
        }

        @Override
        public int[] encode(Duration data) {
            return new int[]{(int) ((data.toHours() << 8) + (data.toMinutes() % 60))};
        }
    }
}
