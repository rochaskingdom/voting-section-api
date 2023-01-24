package com.vinicius.voting.section.api.controller.response;

import com.vinicius.voting.section.api.domain.model.Vote;
import com.vinicius.voting.section.api.domain.model.enums.VoteMessageEnum;
import lombok.Builder;

import java.time.Instant;
import java.util.List;

@Builder
public record VoteResponse(
        String uuid,
        String associateDocumentNumber,
        VoteMessageEnum voteMessage,
        Instant createdAt
) {

    public static VoteResponse of(final Vote vote) {
        final var uuid = vote.getUuid();
        final var associateDocumentNumber = vote.getAssociateDocumentNumber();
        final var voteMessage = vote.getVoteMessage();
        final var createdAt = vote.getCreatedAt();
        return VoteResponse.builder()
                .uuid(uuid)
                .associateDocumentNumber(associateDocumentNumber)
                .voteMessage(voteMessage)
                .createdAt(createdAt)
                .build();
    }

    public static List<VoteResponse> of(final List<Vote> votes) {
        return votes
                .stream()
                .map(VoteResponse::of)
                .toList();
    }
}
