package com.vinicius.voting.section.api.controller;

import com.vinicius.voting.section.api.controller.request.AgendaRequest;
import com.vinicius.voting.section.api.controller.response.AgendaResponse;
import com.vinicius.voting.section.api.domain.service.AgendaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("voting-section/v1/agendas")
public class AgendaController {

    private final AgendaService agendaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<AgendaResponse> save(@RequestBody final @Valid AgendaRequest agendaRequest) {
        return Mono.just(agendaRequest)
                .doOnNext(request -> log.info("Iniciando criação de pauta - [{}]", request))
                .flatMap(request -> agendaService.save(agendaRequest))
                .map(AgendaResponse::of)
                .doOnSuccess(response -> log.info("Finalizado criação de pauta - [{}]", response))
                .doOnError(error -> log.error("Ocorreu um erro na criação da pauta - [{}]", error.toString()));
    }

    @GetMapping
    public Flux<AgendaResponse> listAll() {
        log.info("Iniciando listagem de agendas");
        return agendaService.listAll()
                .map(AgendaResponse::of)
                .doOnComplete(() -> log.info("Finalizado listagem de pautas"))
                .doOnError(error -> log.info("Ocorreu um erro ao listar as pautas - [{}]", error.toString()));
    }

    @GetMapping("/{agendaUuid}")
    public Mono<AgendaResponse> findByUuid(@PathVariable final String agendaUuid) {
        return Mono.just(agendaUuid)
                .doOnNext(request -> log.info("Iniciando busca da pauta com o id - [{}]", agendaUuid))
                .flatMap(request -> agendaService.findByUuid(agendaUuid))
                .map(AgendaResponse::of)
                .doOnSuccess(response -> log.info("Finalizado busca da pauta - [{}]", response))
                .doOnError(error -> log.error("Ocorreu um erro na busca da pauta - [{}]", error.toString()));
    }
}
