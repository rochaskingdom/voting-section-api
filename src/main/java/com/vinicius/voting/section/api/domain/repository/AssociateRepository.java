package com.vinicius.voting.section.api.domain.repository;

import com.vinicius.voting.section.api.domain.model.Associate;
import reactor.core.publisher.Mono;

public interface AssociateRepository {
    Mono<Associate> save(final Associate associate);

    Mono<Associate> findByUuid(final String associateUuid);

    Mono<Associate> findByDocumentNumber(final String documentNumber);
}
