package com.vinicius.voting.section.api.domain.repository.impl;

import com.vinicius.voting.section.api.domain.model.Agenda;
import com.vinicius.voting.section.api.domain.repository.AgendaRepository;
import com.vinicius.voting.section.api.domain.repository.reactivemongo.AgendaReactiveMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class AgendaRepositoryImpl implements AgendaRepository {

    private final AgendaReactiveMongoRepository repository;

    @Override
    public Mono<Agenda> save(final Agenda agenda) {
        return repository.save(agenda);
    }

    @Override
    public Flux<Agenda> listAll() {
        return repository.findAll();
    }

    @Override
    public Mono<Agenda> findByUuid(final String agendaUuid) {
        return repository.findByUuid(agendaUuid);
    }
}
