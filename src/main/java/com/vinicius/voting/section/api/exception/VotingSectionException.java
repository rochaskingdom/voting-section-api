package com.vinicius.voting.section.api.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vinicius.voting.section.api.controller.handler.VotingSectionExceptionSerializer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;

import java.util.Map;

@Getter
@RequiredArgsConstructor
@JsonSerialize(using = VotingSectionExceptionSerializer.class)
public class VotingSectionException extends RuntimeException {

    private final HttpStatus status;
    private final String errorCode;
    private final String errorDescription;
    private Map<String, String> fields;

    public VotingSectionException(ClientResponse clientResponse, String errorCode, String errorDescription) {
        this.status = (HttpStatus) clientResponse.statusCode();
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

    public VotingSectionException(HttpStatus status, String errorCode, String errorDescription, Map<String, String> fields) {
        this(status, errorCode, errorDescription);
        this.fields = fields;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("status", status)
                .append("errorCode", errorCode)
                .append("errorDescription", errorDescription)
                .append("fields", fields)
                .append("exception", this.getClass().getSimpleName())
                .toString();
    }
}
