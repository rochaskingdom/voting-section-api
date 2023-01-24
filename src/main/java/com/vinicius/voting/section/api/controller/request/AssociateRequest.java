package com.vinicius.voting.section.api.controller.request;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public record AssociateRequest(
        String documentNumber
) {
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("documentNumber", documentNumber)
                .toString();
    }
}
