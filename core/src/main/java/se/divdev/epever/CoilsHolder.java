package se.divdev.epever;

import com.intelligt.modbus.jlibmodbus.ModbusMaster;
import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusNumberException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoilsHolder implements Holder {

    private final static Logger LOGGER = LoggerFactory.getLogger(CoilsHolder.class);

    protected final ModbusMaster modbusMaster;

    protected final int serverAddress;

    public CoilsHolder(final ModbusMaster modbusMaster,
                       final int serverAddress) {
        this.modbusMaster = modbusMaster;
        this.serverAddress = serverAddress;
    }

    @Override
    public boolean handlesAddress(int startAddress) {
        return startAddress >= 0x02 && startAddress <= 0x06;
    }

    protected int[] convert(boolean[] in) {
        int[] result = new int[in.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = in[i] ? 1 : 0;
        }
        return result;
    }

    protected boolean[] convert(int[] in) {
        boolean[] result = new boolean[in.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = in[i] == 1;
        }
        return result;
    }

    @Override
    public int[] read(int startAddress, int quantity) {
        try {
            return convert(modbusMaster.readCoils(serverAddress, startAddress, quantity));
        } catch (ModbusProtocolException | ModbusNumberException | ModbusIOException e) {
            LOGGER.error("Error reading from holding register. Server Address: {}, Start Address: {}, Quantity: {}", serverAddress, startAddress, quantity, e);
            // Lets be forgiving about errors and return an empty array
            return new int[0];
        }
    }

    @Override
    public boolean write(int startAddress, int[] data) {
        try {
            modbusMaster.writeMultipleCoils(serverAddress, startAddress, convert(data));
            return true;
        } catch (ModbusProtocolException | ModbusNumberException | ModbusIOException e) {
            LOGGER.error("Error writing to holding register. Server Address: {}, Start Address: {}, Quantity: {}", serverAddress, startAddress, data.length, e);
        }
        return false;
    }
}
