package se.divdev.epever.core;

import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusNumberException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

public interface Holder<T> {

    Logger LOGGER = LoggerFactory.getLogger(Holder.class);

    boolean handlesAddress(int startAddress);

    default Function<int[], T> encodeMethod() {
        return (i) -> (T) i;
    }

    default Function<T, int[]> decodeMethod() {
        return (i) -> (int[]) i;
    }

    default boolean[] encodeBoolean(int[] in) {
        boolean[] result = new boolean[in.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = in[i] == 1;
        }
        return result;
    }

    default int[] decodeBoolean(boolean[] in) {
        int[] result = new int[in.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = in[i] ? 1 : 0;
        }
        return result;
    }

    default int[] read(int startAddress, int quantity) {
        try {
            return decodeMethod().apply(readRaw(startAddress, quantity));
        } catch (ModbusProtocolException | ModbusNumberException | ModbusIOException e) {
            LOGGER.error("Error reading from Start Address: {}, Quantity: {}", startAddress, quantity, e);
            // Lets be forgiving about errors and return an empty array
            return new int[0];
        }
    }

    default boolean write(int startAddress, int[] data) {
        try {
            writeRaw(startAddress, encodeMethod().apply(data));
            return true;
        } catch (ModbusProtocolException | ModbusNumberException | ModbusIOException e) {
            LOGGER.error("Error writing to Start Address: {}, Quantity: {}", startAddress, data.length, e);
        }
        return false;
    }

    T readRaw(int startAddress, int quantity) throws ModbusProtocolException, ModbusNumberException, ModbusIOException;

    default void writeRaw(int startAddress, T data) throws ModbusProtocolException, ModbusNumberException, ModbusIOException {
        throw new UnsupportedOperationException("Write not supported to start address " + startAddress);
    }
}

