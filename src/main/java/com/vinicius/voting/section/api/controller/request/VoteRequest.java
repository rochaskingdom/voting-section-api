package com.vinicius.voting.section.api.controller.request;

import com.vinicius.voting.section.api.domain.model.enums.VoteMessageEnum;
import lombok.Builder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Builder
public record VoteRequest(
        VoteMessageEnum vote,
        String documentNumber
) {
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("vote", vote)
                .append("documentNumber", documentNumber)
                .toString();
    }
}
