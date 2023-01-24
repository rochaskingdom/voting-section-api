package com.vinicius.voting.section.api.domain.repository.impl;

import com.vinicius.voting.section.api.domain.model.Vote;
import com.vinicius.voting.section.api.domain.repository.VoteRepository;
import com.vinicius.voting.section.api.domain.repository.reactivemongo.VoteReactiveMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class VoteRepositoryImpl implements VoteRepository {

    private final VoteReactiveMongoRepository repository;

    @Override
    public Mono<Vote> save(final Vote vote) {
        return repository.save(vote);
    }

    @Override
    public Flux<Vote> listAll() {
        return repository.findAll();
    }

    @Override
    public Mono<Vote> findByUuid(final String agendaUuid) {
        return repository.findByUuid(agendaUuid);
    }

    @Override
    public Flux<Vote> findAllBySectionUuid(final String sectionUuid) {
        return repository.findAllBySectionUuid(sectionUuid);
    }

    @Override
    public Mono<Vote> findByAssociateDocumentNumber(final String associateUuid) {
        return repository.findByAssociateDocumentNumber(associateUuid);
    }

    @Override
    public Mono<Vote> findByAssociateDocumentNumberAndSectionUuid(final String associateUuid, final String sectionUuid) {
        return repository.findByAssociateDocumentNumberAndSectionUuid(associateUuid, sectionUuid);
    }
}
