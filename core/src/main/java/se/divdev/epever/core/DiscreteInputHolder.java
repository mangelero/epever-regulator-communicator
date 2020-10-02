package se.divdev.epever.core;

import com.intelligt.modbus.jlibmodbus.ModbusMaster;
import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusNumberException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusProtocolException;

import java.util.function.Function;

public class DiscreteInputHolder implements Holder<boolean[]> {

    protected final ModbusMaster modbusMaster;

    protected final int serverAddress;

    public DiscreteInputHolder(final ModbusMaster modbusMaster,
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
        return modbusMaster.readDiscreteInputs(serverAddress, startAddress, quantity);
    }

    @Override
    public boolean handlesAddress(int startAddress) {
        return startAddress >= 0x2000 && startAddress <= 0x200C;
    }
}
