package se.divdev.epever.core;

import com.intelligt.modbus.jlibmodbus.ModbusMaster;
import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusNumberException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusProtocolException;

public class InputRegisterHolder implements Holder<int[]> {

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
    public int[] readRaw(int startAddress, int quantity) throws ModbusProtocolException, ModbusNumberException, ModbusIOException {
        return modbusMaster.readInputRegisters(serverAddress, startAddress, quantity);
    }
}
