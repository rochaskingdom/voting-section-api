package com.vinicius.voting.section.api.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Builder
public record SectionRequest(
        @NotBlank(message = "Descrição é obrigatório.")
        String description,
        String closingDateTime,
        @NotBlank(message = "Identificar da pauta é obrigatório.")
        String agendaUuid
) {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("description", description)
                .append("closingDateTime", closingDateTime)
                .append("agendaUuid", agendaUuid)
                .toString();
    }
}
