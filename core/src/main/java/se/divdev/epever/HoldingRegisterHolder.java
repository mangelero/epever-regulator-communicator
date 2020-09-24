package se.divdev.epever;

import com.intelligt.modbus.jlibmodbus.ModbusMaster;
import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusNumberException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HoldingRegisterHolder implements Holder {

    private final static Logger LOGGER = LoggerFactory.getLogger(HoldingRegisterHolder.class);

    private final ModbusMaster modbusMaster;

    private final int serverAddress;

    public HoldingRegisterHolder(final ModbusMaster modbusMaster,
                                 final int serverAddress) {
        this.modbusMaster = modbusMaster;
        this.serverAddress = serverAddress;
    }

    @Override
    public boolean handlesAddress(int startAddress) {
        return startAddress >= 0x9000 && startAddress <= 0x9070;
    }

    @Override
    public int[] read(int startAddress, int quantity) {
        try {
            return modbusMaster.readHoldingRegisters(serverAddress, startAddress, quantity);
        } catch (ModbusProtocolException | ModbusNumberException | ModbusIOException e) {
            LOGGER.error("Error reading from holding register. Server Address: {}, Start Address: {}, Quantity: {}", serverAddress, startAddress, quantity, e);
            // Lets be forgiving about errors and return an empty array
            return new int[0];
        }
    }

    @Override
    public boolean write(int startAddress, int[] data) {
        try {
            modbusMaster.writeMultipleRegisters(serverAddress, startAddress, data);
            return true;
        } catch (ModbusProtocolException | ModbusNumberException | ModbusIOException e) {
            LOGGER.error("Error writing to holding register. Server Address: {}, Start Address: {}, Quantity: {}", serverAddress, startAddress, data.length, e);
            return false;
        }
    }
}
