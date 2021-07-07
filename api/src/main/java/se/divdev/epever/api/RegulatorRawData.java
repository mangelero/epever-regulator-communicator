package se.divdev.epever.api;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class RegulatorRawData extends ConcurrentHashMap<Integer, Integer> {

    public int[] get(int address, int length) {
        int[] result = new int[length];
        for (int i = 0; i < length; i++) {
            Integer value = get(address + i);
            if (value == null) {
                return null;
            }
            result[i] = value;
        }
        return result;
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

    public Map<Integer, List<Integer>> clusterData() {
        List<Integer> addresses = new ArrayList<>(keySet());
        Collections.sort(addresses);
        Map<Integer, List<Integer>> result = new HashMap<>();
        List<Integer> current = null;

        Integer lastAddress = null;

        for (Integer address : addresses) {
            if (lastAddress == null || lastAddress != address - 1) {
                current = result.computeIfAbsent(address, k -> new ArrayList<>());
            }
            current.add(get(address));
            lastAddress = address;
        }
        return result;
    }
}
