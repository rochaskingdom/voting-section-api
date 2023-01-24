package com.vinicius.voting.section.api.controller.request;

import com.vinicius.voting.section.api.domain.model.enums.VoteMessageEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Builder
public record VoteRequest(
        @NotBlank(message = "Voto é obrigatório.")
        VoteMessageEnum vote,
        @NotBlank(message = "Número de documento é obrigatório.")
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
