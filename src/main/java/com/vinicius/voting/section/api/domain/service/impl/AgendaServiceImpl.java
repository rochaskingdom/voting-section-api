package com.vinicius.voting.section.api.domain.service.impl;

import com.vinicius.voting.section.api.controller.request.AgendaRequest;
import com.vinicius.voting.section.api.domain.model.Agenda;
import com.vinicius.voting.section.api.domain.repository.AgendaRepository;
import com.vinicius.voting.section.api.domain.service.AgendaService;
import com.vinicius.voting.section.api.exception.AgendaNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class AgendaServiceImpl implements AgendaService {

    private final AgendaRepository agendaRepository;

    @Override
    public Mono<Agenda> save(final AgendaRequest agendaRequest) {
        return agendaRepository.save(Agenda.newAgenda(agendaRequest.name()))
                .doOnNext(agenda -> log.info("Pauta criada com sucesso. - [{}]", agenda));
    }

    @Override
    public Flux<Agenda> listAll() {
        return agendaRepository.listAll()
                .doOnNext(agenda -> log.info("Listagem de pautas realizada com sucesso. - [{}]", agenda));
    }

    @Override
    public Mono<Agenda> findByUuid(final String uuid) {
        return agendaRepository.findByUuid(uuid)
                .doOnNext(agenda -> log.info("Pauta encontrada com sucesso. - [{}]", agenda))
                .switchIfEmpty(Mono.error(AgendaNotFoundException::new));
    }
}
