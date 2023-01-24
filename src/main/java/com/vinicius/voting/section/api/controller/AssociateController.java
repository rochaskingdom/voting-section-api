package com.vinicius.voting.section.api.controller;

import com.vinicius.voting.section.api.controller.request.AssociateRequest;
import com.vinicius.voting.section.api.controller.response.AssociateResponse;
import com.vinicius.voting.section.api.domain.service.AssociateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("voting-section/v1/associates")
public class AssociateController {

    private final AssociateService associateService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<AssociateResponse> save(@RequestBody final AssociateRequest associateRequest) {
        return Mono.just(associateRequest)
                .doOnNext(request -> log.info("Iniciando criação de associado - [{}]", request))
                .flatMap(request -> associateService.save(associateRequest.documentNumber()))
                .map(AssociateResponse::of)
                .doOnSuccess(response -> log.info("Finalizado criação de associado - [{}]", response))
                .doOnError(error -> log.error("Ocorreu um erro na criação do associado - [{}]", error.toString()));
    }

    @GetMapping("/{documentNumber}")
    public Mono<AssociateResponse> findByDocumentNumber(@PathVariable final String documentNumber) {
        return Mono.just(documentNumber)
                .doOnNext(request -> log.info("Iniciando busca de associado com o número de documento - [{}]", documentNumber))
                .flatMap(request -> associateService.findByDocumentNumber(documentNumber))
                .map(AssociateResponse::of)
                .doOnSuccess(response -> log.info("Finalizado busca do associado - [{}]", response))
                .doOnError(error -> log.error("Ocorreu um erro na busca do associado - [{}]", error.toString()));
    }
}
