package com.ural.manager.security.encryption.spec;

public interface AlgorithmSpec {
    int getNonceLengthByte();

    int getSaltLengthByte();
}
