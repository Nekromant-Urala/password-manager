package com.ural.manager.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

public class JsonSerializer implements Serializer {
    private final ObjectMapper mapper;

    public JsonSerializer() {
        this.mapper = new ObjectMapper();
        this.mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Override
    public String serialize(Object data) throws JsonProcessingException {
        return mapper.writeValueAsString(data);
    }

    @Override
    public <T> T deserialize(String json, TypeReference<T> type) throws JsonProcessingException {
        return mapper.readValue(json, type);
    }
}
