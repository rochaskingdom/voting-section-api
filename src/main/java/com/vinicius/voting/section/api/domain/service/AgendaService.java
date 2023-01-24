package com.vinicius.voting.section.api.domain.service;

import com.vinicius.voting.section.api.controller.request.AgendaRequest;
import com.vinicius.voting.section.api.domain.model.Agenda;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AgendaService {
    Mono<Agenda> save(final AgendaRequest agendaRequest);

    Flux<Agenda> listAll();

    Mono<Agenda> findByUuid(final String uuid);
}
