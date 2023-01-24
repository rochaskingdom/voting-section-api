package com.vinicius.voting.section.api.controller.request;

import com.vinicius.voting.section.api.annotation.DocumentNumber;
import jakarta.validation.constraints.NotBlank;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public record AssociateRequest(
        @DocumentNumber
        @NotBlank(message = "Número de documento é obrigatório.")
        String documentNumber
) {
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("documentNumber", documentNumber)
                .toString();
    }
}
