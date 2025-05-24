package com.ural.security.encryption.spec;

public interface AlgorithmSpec {
    int getNonceLengthByte();
    int getKeyLengthBit();
    int getSaltLengthByte();
}
