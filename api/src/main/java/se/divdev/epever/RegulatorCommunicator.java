package se.divdev.epever;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;

public interface RegulatorCommunicator {

    Logger LOGGER = LoggerFactory.getLogger(RegulatorCommunicator.class);

    void connect() throws RegulatorException.ConnectException;

    void disconnect() throws RegulatorException.DisconnectException;

    int[] read(int startAddress, int quantity) throws RegulatorException;

    boolean write(int[] data, int startAddress) throws RegulatorException;

    void setRTC(Instant date) throws RegulatorException;

    default <T> T read(Class<T> clazz) {
        return Regulator.from(readRaw(clazz), clazz);
    }

    default RegulatorRawData readRaw(Class<?> clazz) {
        RegulatorRawData result = new RegulatorRawData();
        Regulator.clusterInformation(clazz).stream()
                .forEach(cluster -> {
                    try {
                        int[] data = read(cluster.address, cluster.quantity);
                        for (int i = 0; i < data.length; i++) {
                            result.put(cluster.address + i, data[i]);
                        }
                    } catch (RegulatorException e) {
                        LOGGER.error("Error reading from regulator", e);
                    }
                });
        return result;
    }
}
