package com.vinicius.voting.section.api.controller.response;

import com.vinicius.voting.section.api.domain.model.Agenda;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.Instant;

public record AgendaResponse(
        String uuid,
        String name,
        Instant createdDate
) {
    public static AgendaResponse of(final Agenda agenda) {
        final var uuid = agenda.getUuid();
        final var name = agenda.getName();
        final var createdAt = agenda.getCreatedAt();
        return new AgendaResponse(uuid, name, createdAt);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("uuid", uuid)
                .append("name", name)
                .append("createdDate", createdDate)
                .toString();
    }
}
