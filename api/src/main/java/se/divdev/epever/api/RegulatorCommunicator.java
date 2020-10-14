package se.divdev.epever.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;

public interface RegulatorCommunicator {

    Logger LOGGER = LoggerFactory.getLogger(RegulatorCommunicator.class);

    /**
     * Check if modbus is connected
     *
     * @return true if connected, false otherwise
     */
    boolean isConnected();

    /**
     * Connect to the controller
     *
     * @throws RegulatorException.ConnectException When a connect failure occurs
     */
    void connect() throws RegulatorException.ConnectException;

    /**
     * Disconnect from the controller
     *
     * @throws RegulatorException.DisconnectException
     */
    void disconnect() throws RegulatorException.DisconnectException;

    /**
     * Read specified number of bytes from specified start address
     *
     * @param startAddress Where to start the reading
     * @param quantity     The number of integers to read
     * @return int array (16bit) with data read from the controller. The array will be empty on error
     */
    int[] read(int startAddress, int quantity);

    /**
     * Write array of integers (16 bit) to the controller
     *
     * @param data         The data to write
     * @param startAddress Where to start writing the data
     * @return true if successful
     */
    boolean write(int[] data, int startAddress);

    /**
     * Convenience method for setting the date/time in the controller
     *
     * @param date The date to set
     */
    default void setRtc(Instant date) {
        DataMapper.InstantMapper mapper = DataMapper.getInstance(DataMapper.InstantMapper.class);
        int[] data = mapper.encode(date);
        boolean success = write(data, 0x9013);
        LOGGER.info("Writing date ({}) to regulator: {}", data, success);
    }

    /**
     * Read a EpeverMapper annotated class from the regulator
     *
     * @param clazz The class to read from the controller
     * @return The instantiated class containing the read and decoded data
     */
    default <T> T read(Class<T> clazz) {
        return Regulator.from(readRaw(clazz), clazz);
    }

    /**
     * Read raw data from the controller using an EpeverMapper annotated class
     *
     * @param clazz The annotated class
     * @return Raw data read from the controller
     */
    default RegulatorRawData readRaw(Class<?> clazz) {
        RegulatorRawData result = new RegulatorRawData();
        Regulator.clusterInformation(clazz).stream()
                .forEach(cluster -> {
                    int[] data = read(cluster.address, cluster.quantity);
                    for (int i = 0; i < data.length; i++) {
                        result.put(cluster.address + i, data[i]);
                    }
                });
        return result;
    }

    /**
     * Write and EpeverMapper annotated object to the controller
     *
     * @param object The object to write
     */
    default void write(Object object) {
        RegulatorRawData raw = Regulator.toRaw(object);
        Regulator.fieldInformation(object.getClass()).stream().forEach(info -> {
            int address = info.address;
            int[] dataToWrite = new int[info.mapper.quantity()];
            for (int i = 0; i < dataToWrite.length; i++) {
                dataToWrite[i] = raw.get(address + i);
            }
            boolean success = write(dataToWrite, address);
            LOGGER.info("Wrote {} integers to address {}: {}", info.mapper.quantity(), address, success);
        });

    }
}
