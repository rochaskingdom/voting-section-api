package com.vinicius.voting.section.api.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vinicius.voting.section.api.domain.model.Section;
import com.vinicius.voting.section.api.domain.model.Vote;
import lombok.Builder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record SectionResponse(
        String uuid,
        String description,
        LocalDateTime openingDate,
        LocalDateTime closingDate,
        AgendaResponse agenda,
        Map<String, Integer> votesAmount,
        List<VoteResponse> votes
) {
    public static SectionResponse of(final Section section) {
        final var uuid = section.getUuid();
        final var description = section.getDescription();
        final var openingDate = section.getOpeningDate();
        final var closingDate = section.getClosingDate();
        final var agendaResponse = AgendaResponse.of(section.getAgenda());
        return new SectionResponse(uuid, description, openingDate, closingDate, agendaResponse, null, null);
    }

    public static SectionResponse of(final Section section, Map<String, Integer> votesAmount, List<Vote> votes) {
        final var uuid = section.getUuid();
        final var description = section.getDescription();
        final var openingDate = section.getOpeningDate();
        final var closingDate = section.getClosingDate();
        final var agendaResponse = AgendaResponse.of(section.getAgenda());
        var voteResponseList = VoteResponse.of(votes);
        return new SectionResponse(uuid, description, openingDate, closingDate, agendaResponse, votesAmount, voteResponseList);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("uuid", uuid)
                .append("description", description)
                .append("openingDate", openingDate)
                .append("closingDate", closingDate)
                .append("agenda", agenda)
                .toString();
    }
}
