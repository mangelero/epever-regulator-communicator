package se.divdev.epever.core;

import com.intelligt.modbus.jlibmodbus.Modbus;
import com.intelligt.modbus.jlibmodbus.ModbusMaster;
import com.intelligt.modbus.jlibmodbus.ModbusMasterFactory;
import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.serial.SerialParameters;
import com.intelligt.modbus.jlibmodbus.serial.SerialPort.BaudRate;
import com.intelligt.modbus.jlibmodbus.serial.SerialPort.Parity;
import com.intelligt.modbus.jlibmodbus.serial.SerialPortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.divdev.epever.api.RegulatorCommunicator;
import se.divdev.epever.api.RegulatorException;

import java.util.ArrayList;
import java.util.List;

public class RegulatorCommunicatorImpl implements RegulatorCommunicator {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegulatorCommunicatorImpl.class);

    private final SerialParameters serialParameters = new SerialParameters();

    private final ModbusMaster modbusMaster;

    private final int serverAddress;

    private final List<Holder> holders = new ArrayList<>();

    private volatile int retryAttempts = 5;

    private volatile int sleepBetweenRetriesMs = 100;

    public RegulatorCommunicatorImpl(final String device) {
        this(device, 1);
    }

    public RegulatorCommunicatorImpl(final String device,
                                     final int serverAddress) {

        this(device, serverAddress, BaudRate.BAUD_RATE_115200, 8, Parity.NONE, 1);
    }

    public RegulatorCommunicatorImpl(final String device,
                                     final int serverAddress,
                                     final BaudRate baudRate,
                                     final int dataBits,
                                     final Parity parity,
                                     final int stopBits) {
        LOGGER.info("Using device: {}", device);
        LOGGER.info("Using server address: {}", serverAddress);
        LOGGER.info("Using baud rate: {}", baudRate);
        LOGGER.info("Using data bits: {}", dataBits);
        LOGGER.info("Using parity: {}", parity);
        LOGGER.info("Using stop bits: {}", stopBits);
        this.serverAddress = serverAddress;
        serialParameters.setDevice(device);
        serialParameters.setBaudRate(baudRate);
        serialParameters.setDataBits(dataBits);
        serialParameters.setParity(parity);
        serialParameters.setStopBits(stopBits);

        try {
            modbusMaster = ModbusMasterFactory.createModbusMasterRTU(serialParameters);
            registerHolders();
        } catch (SerialPortException e) {
            LOGGER.error("Error setting up regulator!", e);
            throw new RuntimeException("Error setting up regulator", e);
        }
    }

    /**
     * Constructor for testing
     *
     * @param serverAddress
     * @param modbusMaster
     */
    RegulatorCommunicatorImpl(final int serverAddress,
                              final ModbusMaster modbusMaster) {
        this.serverAddress = serverAddress;
        this.modbusMaster = modbusMaster;
    }

    private void registerHolders() {
        holders.add(new CoilsHolder(modbusMaster, serverAddress));
        holders.add(new DiscreteInputHolder(modbusMaster, serverAddress));
        holders.add(new HoldingRegisterHolder(modbusMaster, serverAddress));
        holders.add(new InputRegisterHolder(modbusMaster, serverAddress));
    }

    @Override
    public void setRetryAttempts(int retries) {
        retryAttempts = retries;
    }

    @Override
    public void setSleepBetweenRetriesMs(int sleep) {
        sleepBetweenRetriesMs = sleep;
    }

    @Override
    public boolean isConnected() {
        return modbusMaster.isConnected();
    }

    @Override
    public void connect() throws RegulatorException.ConnectException {
        try {
            if (isConnected()) {
                throw new RegulatorException.ConnectException("Already connected");
            }
            LOGGER.debug("Connecting to: {}", serialParameters.getDevice());
            modbusMaster.setResponseTimeout(Modbus.MAX_RESPONSE_TIMEOUT);
            modbusMaster.connect();
            LOGGER.debug("Connected to: {}", serialParameters.getDevice());
        } catch (ModbusIOException e) {
            throw new RegulatorException.ConnectException("Failed to connect", e);
        }
    }

    @Override
    public void disconnect() throws RegulatorException.DisconnectException {
        try {
            LOGGER.debug("Disconnecting from: {}", serialParameters.getDevice());
            if (modbusMaster.isConnected()) {
                modbusMaster.disconnect();
                LOGGER.debug("Disconnected from: {}", serialParameters.getDevice());
            } else {
                LOGGER.info("Not connected");
            }
        } catch (ModbusIOException e) {
            throw new RegulatorException.DisconnectException("Error disconnecting from regulator!", e);
        }
    }

    private Holder findHolder(int startAddress) {
        return holders.stream()
                .filter(holder -> holder.handlesAddress(startAddress))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No holder found for start address: " + startAddress));
    }

    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
        }
    }

    private void reconnect() {
        LOGGER.info("Reconnecting ...");
        try {
            disconnect();
            sleep(100);
            connect();
        } catch (Exception e) {
            // Deal with this somewhere
            throw new RuntimeException(e);
        }
    }

    @Override
    public int[] read(int startAddress, int quantity) {
        return read(startAddress, quantity, 0);
    }

    private int[] read(int startAddress, int quantity, int attempt) {
        try {
            return findHolder(startAddress).read(startAddress, quantity);
        } catch (RegulatorException e) {
            if (attempt >= retryAttempts) {
                LOGGER.error("Error reading from Start Address: {}, Quantity: {}", startAddress, quantity, e);
                reconnect();
                return new int[0];
            }
            sleep(sleepBetweenRetriesMs);
            return read(startAddress, quantity, attempt + 1);
        }
    }

    @Override
    public boolean write(int[] data, int startAddress) {
        return write(data, startAddress, 0);
    }

    private boolean write(int[] data, int startAddress, int attempt) {
        try {
            findHolder(startAddress).write(startAddress, data);
            return true;
        } catch (RegulatorException e) {
            if (attempt >= retryAttempts) {
                LOGGER.error("Error writing to Start Address: {}, Quantity: {}", startAddress, data.length, e);
                reconnect();
                return false;
            }
            sleep(sleepBetweenRetriesMs);
            return write(data, startAddress, attempt + 1);
        }
    }
}
