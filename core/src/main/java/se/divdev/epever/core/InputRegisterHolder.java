package se.divdev.epever.core;

import com.intelligt.modbus.jlibmodbus.ModbusMaster;
import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusNumberException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InputRegisterHolder implements Holder {

    private final static Logger LOGGER = LoggerFactory.getLogger(InputRegisterHolder.class);

    private final ModbusMaster modbusMaster;

    private final int serverAddress;

    public InputRegisterHolder(final ModbusMaster modbusMaster,
                               final int serverAddress) {
        this.modbusMaster = modbusMaster;
        this.serverAddress = serverAddress;
    }

    @Override
    public boolean handlesAddress(int startAddress) {
        return startAddress >= 0x3000 && startAddress <= 0x331E;
    }

    @Override
    public int[] read(int startAddress, int quantity) {
        try {
            return modbusMaster.readInputRegisters(serverAddress, startAddress, quantity);
        } catch (ModbusProtocolException | ModbusNumberException | ModbusIOException e) {
            LOGGER.error("Error reading from input register. Server Address: {}, Start Address: {}, Quantity: {}", serverAddress, startAddress, quantity, e);
            // Lets be forgiving about errors and return an empty array
            return new int[0];
        }
    }

    @Override
    public boolean write(int startAddress, int[] data) {
        throw new UnsupportedOperationException("Input register can't be written to!");
    }
}
