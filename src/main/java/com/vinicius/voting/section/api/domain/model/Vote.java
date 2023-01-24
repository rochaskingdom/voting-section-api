package com.vinicius.voting.section.api.domain.model;

import com.vinicius.voting.section.api.controller.request.VoteRequest;
import com.vinicius.voting.section.api.domain.model.enums.VoteMessageEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.UUID;

@Getter
@Document
@AllArgsConstructor
public class Vote {

    @Id
    private String id;
    private String uuid;
    private String associateDocumentNumber;
    private VoteMessageEnum voteMessage;
    private Instant createdAt;
    private String sectionUuid;

    public static Vote newVote(final VoteRequest voteRequest, final String sectionUuid) {
        final var identifier = UUID.randomUUID().toString();
        final var now = Instant.now();
        final var documentNumber = voteRequest.documentNumber();
        final var voteMessage = voteRequest.vote();
        return new Vote(null, identifier, documentNumber, voteMessage, now, sectionUuid);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("uuid", uuid)
                .append("voteMessage", voteMessage)
                .append("createdAt", createdAt)
                .append("sectionUuid", sectionUuid)
                .toString();
    }
}
