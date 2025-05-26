package com.ural.manager.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

public interface Serializer {
    String serialize(Object data) throws JsonProcessingException;
    <T> T deserialize(String json, TypeReference<T> type) throws JsonProcessingException;
}
