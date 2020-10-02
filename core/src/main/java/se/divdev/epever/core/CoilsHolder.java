package se.divdev.epever.core;

import com.intelligt.modbus.jlibmodbus.ModbusMaster;
import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusNumberException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusProtocolException;

import java.util.function.Function;

public class CoilsHolder implements Holder<boolean[]> {

    protected final ModbusMaster modbusMaster;

    protected final int serverAddress;

    public CoilsHolder(final ModbusMaster modbusMaster,
                       final int serverAddress) {
        this.modbusMaster = modbusMaster;
        this.serverAddress = serverAddress;
    }

    @Override
    public Function<int[], boolean[]> encodeMethod() {
        return this::encodeBoolean;
    }

    @Override
    public Function<boolean[], int[]> decodeMethod() {
        return this::decodeBoolean;
    }

    @Override
    public boolean[] readRaw(int startAddress, int quantity) throws ModbusProtocolException, ModbusNumberException, ModbusIOException {
        return modbusMaster.readCoils(serverAddress, startAddress, quantity);
    }

    @Override
    public void writeRaw(int startAddress, boolean[] data) throws ModbusProtocolException, ModbusNumberException, ModbusIOException {
        modbusMaster.writeMultipleCoils(serverAddress, startAddress, data);
    }

    @Override
    public boolean handlesAddress(int startAddress) {
        return startAddress >= 0x02 && startAddress <= 0x06;
    }
}
