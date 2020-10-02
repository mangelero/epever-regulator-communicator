package se.divdev.epever.core;

import com.intelligt.modbus.jlibmodbus.ModbusMaster;
import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusNumberException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HoldingRegisterHolder implements Holder<int[]> {

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
    public int[] readRaw(int startAddress, int quantity) throws ModbusProtocolException, ModbusNumberException, ModbusIOException {
        return modbusMaster.readHoldingRegisters(serverAddress, startAddress, quantity);
    }

    @Override
    public void writeRaw(int startAddress, int[] data) throws ModbusProtocolException, ModbusNumberException, ModbusIOException {
        modbusMaster.writeMultipleRegisters(serverAddress, startAddress, data);
    }
}
