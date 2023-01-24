package com.vinicius.voting.section.api.domain.repository.impl;

import com.vinicius.voting.section.api.domain.model.Associate;
import com.vinicius.voting.section.api.domain.repository.AssociateRepository;
import com.vinicius.voting.section.api.domain.repository.reactivemongo.AssociateReactiveMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class AssociateRepositoryImpl implements AssociateRepository {

    private final AssociateReactiveMongoRepository repository;

    @Override
    public Mono<Associate> save(final Associate associate) {
        return repository.save(associate);
    }

    @Override
    public Mono<Associate> findByUuid(final String associateUuid) {
        return repository.findByUuid(associateUuid);
    }

    @Override
    public Mono<Associate> findByDocumentNumber(final String documentNumber) {
        return repository.findByDocumentNumber(documentNumber);
    }
}
