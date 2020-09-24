package se.divdev.epever;

import java.util.concurrent.ConcurrentHashMap;

public class RegulatorRawData extends ConcurrentHashMap<Integer, Integer> {

    @Override
    public Integer put(Integer key, Integer value) {
        if (value == 0) {
            // Don't put 0 values to save space and memory
            return 0;
        }
        return super.put(key, value);
    }

    @Override
    public Integer get(Object key) {
        Integer value = super.get(key);
        if (value == null) {
            return 0;
        }
        return value;
    }

    public RegulatorRawData updatesSince(final RegulatorRawData data) {
        RegulatorRawData diff = new RegulatorRawData();

        this.entrySet().stream()
                .filter(entry -> !entry.getValue().equals(data.get(entry.getKey())))
                .forEach(diff::put);

        return diff;
    }

    public RegulatorRawData merge(RegulatorRawData other) {
        this.putAll(other);
        return this;
    }

    private void put(Entry<Integer, Integer> entry) {
        put(entry.getKey(), entry.getValue());
    }
}
