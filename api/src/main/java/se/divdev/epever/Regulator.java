package se.divdev.epever;

import se.divdev.epever.data.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Regulator {

    private static final ConcurrentHashMap<Class<?>, List<FieldInformation>> FIELD_INFORMATION = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Class<?>, List<ClusterInformation>> CLUSTER_INFORMATION = new ConcurrentHashMap<>();

    private Regulator() {
    }

    static List<FieldInformation> fieldInformation(Class<?> clazz) {
        return FIELD_INFORMATION.computeIfAbsent(clazz, Regulator::extractFieldInformationFromClass);
    }

    private static List<FieldInformation> extractFieldInformationFromClass(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .map(Regulator::extractInformationFromField)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private static Optional<FieldInformation> extractInformationFromField(final Field field) {
        field.setAccessible(true);
        Annotation[] annotations = field.getDeclaredAnnotationsByType(EpeverMapper.class);
        if (annotations == null || annotations.length == 0) {
            return Optional.empty();
        }
        return Optional.of(new FieldInformation(field, (EpeverMapper) annotations[0]));
    }

    public static List<ClusterInformation> clusterInformation(final Class<?> clazz) {
        return CLUSTER_INFORMATION.computeIfAbsent(clazz, Regulator::clusterInformationFromClass);
    }

    private static List<ClusterInformation> clusterInformationFromClass(final Class<?> clazz) {
        List<Integer> addresses = new ArrayList<>();
        for (FieldInformation information : fieldInformation(clazz)) {
            int quantity = DataMapper.getInstance(information.epeverMapper.clazz()).quantity();
            for (int i = 0; i < quantity; i++) {
                addresses.add(information.epeverMapper.address() + i);
            }
        }

        List<ClusterInformation> result = new ArrayList<>();

        Collections.sort(addresses);
        ClusterInformation clusterInformation = null;

        for (int address : addresses) {
            if (clusterInformation == null || clusterInformation.nextAddress() != address) {
                clusterInformation = new ClusterInformation(address, 1);
                result.add(clusterInformation);
                continue;
            }
            clusterInformation.increase();
        }
        return result;
    }

    public static class FieldInformation {

        public final Field field;

        public final EpeverMapper epeverMapper;

        public FieldInformation(final Field field, final EpeverMapper epeverMapper) {
            this.field = field;
            this.epeverMapper = epeverMapper;
        }
    }

    public static class ClusterInformation {
        public final int address;

        public int quantity;

        public ClusterInformation(int address, int quantity) {
            this.address = address;
            this.quantity = quantity;
        }

        private void increase() {
            this.quantity++;
        }

        private int nextAddress() {
            return this.address + this.quantity;
        }

        @Override
        public String toString() {
            return "ClusterInformation{" +
                    "address=" + address +
                    ", quantity=" + quantity +
                    '}';
        }
    }

    public static RegulatorData from(final RegulatorRawData data) {
        RegulatorData regulatorData = new RegulatorData();
        regulatorData.coils = from(data, Coils.class);
        regulatorData.discreteInput = from(data, DiscreteInput.class);
        regulatorData.ratedData = from(data, RatedData.class);
        regulatorData.realtimeData = from(data, RealtimeData.class);
        regulatorData.realtimeStatus = from(data, RealtimeStatus.class);
        regulatorData.batterySettingParameter = from(data, BatterySettingParameter.class);
        regulatorData.settingParameter = from(data, SettingParameter.class);
        regulatorData.statisticalParameter = from(data, StatisticalParameter.class);
        return regulatorData;
    }

    public static <T> T from(final RegulatorRawData data, Class<T> from) {
        try {
            T result = from.getDeclaredConstructor().newInstance();
            for (FieldInformation information : fieldInformation(from)) {
                EpeverMapper epeverMapper = information.epeverMapper;
                DataMapper mapper = DataMapper.getInstance(epeverMapper.clazz());
                int[] values = new int[mapper.quantity()];
                for (int i = 0; i < values.length; i++) {
                    values[i] = data.get(epeverMapper.address() + i);
                }

                information.field.set(result, mapper.decode(values));
            }
            return result;
        } catch (Exception e) {
            // TODO: Fix exception
            throw new RuntimeException(e);
        }
    }
}
