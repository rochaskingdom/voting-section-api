package com.vinicius.voting.section.api.domain.service;

import com.vinicius.voting.section.api.controller.request.VoteRequest;
import com.vinicius.voting.section.api.domain.model.Vote;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface VoteService {
    Mono<Void> addVote(final VoteRequest voteRequest, final String sectionUuid);

    Mono<Vote> findByAssociateDocumentNumber(final String associateUuid);

    Flux<Vote> findAllBySectionUuid(final String sectionUuid);
}
