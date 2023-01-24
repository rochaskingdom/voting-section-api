package com.vinicius.voting.section.api.domain.model;

import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
@Document
public class Agenda {

    @Id
    private String id;
    private String uuid;
    private String name;
    private Instant createdAt;

    public static Agenda newAgenda(final String name) {
        final var uuid = UUID.randomUUID().toString();
        final var now = Instant.now();
        return Agenda.builder()
                .uuid(uuid)
                .name(name)
                .createdAt(now)
                .build();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("uuid", uuid)
                .append("name", name)
                .append("createdAt", createdAt)
                .toString();
    }
}
