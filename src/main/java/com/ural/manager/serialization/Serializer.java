package com.ural.manager.serialization;

import com.fasterxml.jackson.core.type.TypeReference;

public interface Serializer {
    String serialize(Object data);
    <T> T deserialize(String json, TypeReference<T> type);
}
