package se.divdev.epever.core;

import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusNumberException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusProtocolException;
import jssc.SerialPortException;
import jssc.SerialPortTimeoutException;
import se.divdev.epever.api.RegulatorException;

import java.util.function.Function;

public interface Holder<T> {

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

    default int[] read(int startAddress, int quantity) throws RegulatorException {
        try {
            return decodeMethod().apply(readRaw(startAddress, quantity));
        } catch (ModbusProtocolException | ModbusNumberException | ModbusIOException e) {
            throw mapException(e);
        }
    }

    default void write(int startAddress, int[] data) throws RegulatorException {
        try {
            writeRaw(startAddress, encodeMethod().apply(data));
        } catch (ModbusProtocolException | ModbusNumberException | ModbusIOException e) {
            throw mapException(e);
        }
    }

    T readRaw(int startAddress, int quantity) throws ModbusProtocolException, ModbusNumberException, ModbusIOException;

    default void writeRaw(int startAddress, T data) throws ModbusProtocolException, ModbusNumberException, ModbusIOException {
        throw new UnsupportedOperationException("Write not supported to start address " + startAddress);
    }

    default RegulatorException mapException(Exception e) throws RegulatorException {
        Throwable cause = e;
        do {
            if (cause instanceof SerialPortTimeoutException) {
                return new RegulatorException.TimeoutException(e);
            } else if (cause instanceof SerialPortException) {
                if (cause.getMessage().contains("Port not opened")) {
                    return new RegulatorException.PortNotOpenedException(e);
                }
            }
        } while ((cause = cause.getCause()) != null);
        return new RegulatorException("Unknown error occurred", e);
    }
}

