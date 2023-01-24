package com.vinicius.voting.section.api.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public record AgendaRequest(
        @NotBlank(message = "Nome é obrigatório.")
        @Size(min = 3, max = 255, message = "Nome nao pode ser menor do que 3 e maior do que 255 caracteres")
        String name
) {
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("name", name)
                .toString();
    }
}
