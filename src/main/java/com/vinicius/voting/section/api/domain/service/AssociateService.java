package com.vinicius.voting.section.api.domain.service;

import com.vinicius.voting.section.api.domain.model.Associate;
import reactor.core.publisher.Mono;

public interface AssociateService {
    Mono<Associate> save(final String documentNumber);

    Mono<Associate> findByDocumentNumber(final String documentNumber);
}
