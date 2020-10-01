package se.divdev.epever.core;

public interface Holder {

    boolean handlesAddress(int startAddress);

    int[] read(int startAddress, int quantity);

    boolean write(int startAddress, int[] data);
}

