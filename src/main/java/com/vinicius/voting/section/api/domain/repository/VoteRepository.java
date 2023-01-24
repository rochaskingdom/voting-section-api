package com.vinicius.voting.section.api.domain.repository;

import com.vinicius.voting.section.api.domain.model.Vote;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface VoteRepository {
    Mono<Vote> save(final Vote vote);

    Flux<Vote> listAll();

    Mono<Vote> findByUuid(final String voteUuid);

    Flux<Vote> findAllBySectionUuid(final String sectionUuid);

    Mono<Vote> findByAssociateDocumentNumber(final String associateUuid);

    Mono<Vote> findByAssociateDocumentNumberAndSectionUuid(final String associateUuid, final String sectionUuid);
}
