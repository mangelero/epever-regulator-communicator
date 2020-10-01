package se.divdev.epever.core;

import com.intelligt.modbus.jlibmodbus.ModbusMaster;
import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusNumberException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DiscreteInputHolder extends CoilsHolder {

    private final static Logger LOGGER = LoggerFactory.getLogger(DiscreteInputHolder.class);

    public DiscreteInputHolder(final ModbusMaster modbusMaster,
                               final int serverAddress) {
        super(modbusMaster, serverAddress);
    }

    @Override
    public boolean handlesAddress(int startAddress) {
        return startAddress >= 0x2000 && startAddress <= 0x200C;
    }

    @Override
    public int[] read(int startAddress, int quantity) {
        try {
            return convert(modbusMaster.readDiscreteInputs(serverAddress, startAddress, quantity));
        } catch (ModbusProtocolException | ModbusNumberException | ModbusIOException e) {
            LOGGER.error("Error reading from discrete input. Server Address: {}, Start Address: {}, Quantity: {}", serverAddress, startAddress, quantity, e);
            // Lets be forgiving about errors and return an empty array
            return new int[0];
        }
    }

    @Override
    public boolean write(int startAddress, int[] data) {
        throw new UnsupportedOperationException("Discrete input can't be written to!");
    }
}
