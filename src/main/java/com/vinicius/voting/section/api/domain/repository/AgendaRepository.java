package com.vinicius.voting.section.api.domain.repository;

import com.vinicius.voting.section.api.domain.model.Agenda;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AgendaRepository {
    Mono<Agenda> save(final Agenda agenda);

    Flux<Agenda> listAll();

    Mono<Agenda> findByUuid(final String agendaUuid);
}
