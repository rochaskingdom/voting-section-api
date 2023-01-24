package com.vinicius.voting.section.api.controller.response;

import com.vinicius.voting.section.api.domain.model.Associate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public record AssociateResponse(
        String uuid,
        String documentNumber
) {
    public static AssociateResponse of(final Associate associate) {
        final var uuid = associate.getUuid();
        final var documentNumber = associate.getDocumentNumber();
        return new AssociateResponse(uuid, documentNumber);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("uuid", uuid)
                .append("documentNumber", documentNumber)
                .toString();
    }
}
