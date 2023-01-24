package com.vinicius.voting.section.api.controller.handler;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.vinicius.voting.section.api.exception.VotingSectionException;

import java.io.IOException;

public class VotingSectionExceptionSerializer extends StdSerializer<VotingSectionException> {

    public VotingSectionExceptionSerializer() {
        this(null);
    }

    protected VotingSectionExceptionSerializer(Class<VotingSectionException> t) {
        super(t);
    }

    public void serialize(
            VotingSectionException value,
            JsonGenerator jsonGenerator,
            SerializerProvider provider
    ) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("error", value.getErrorCode());
        jsonGenerator.writeStringField("error_description", value.getErrorDescription());

        if (value.getFields() != null && !value.getFields().isEmpty()) {
            jsonGenerator.writeObjectField("fields", value.getFields());
        }

        jsonGenerator.writeEndObject();
    }
}