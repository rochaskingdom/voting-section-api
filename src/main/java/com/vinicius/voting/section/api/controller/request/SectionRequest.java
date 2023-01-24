package com.vinicius.voting.section.api.controller.request;

import lombok.Builder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Builder
public record SectionRequest(
        String description,
        String closingDateTime,
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
