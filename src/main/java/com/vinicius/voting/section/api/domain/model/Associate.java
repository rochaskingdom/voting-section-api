package com.vinicius.voting.section.api.domain.model;

import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Getter
@Builder
@Document
public class Associate {

    @Id
    private String id;
    private String uuid;
    private String documentNumber;

    public static Associate newAssociate(final String documentNumber) {
        final var identifier = UUID.randomUUID().toString();
        return Associate.builder()
                .uuid(identifier)
                .documentNumber(documentNumber)
                .build();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("uuid", uuid)
                .append("documentNumber", documentNumber)
                .toString();
    }
}
