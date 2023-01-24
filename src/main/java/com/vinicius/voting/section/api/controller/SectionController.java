package com.vinicius.voting.section.api.controller;

import com.vinicius.voting.section.api.controller.request.SectionRequest;
import com.vinicius.voting.section.api.controller.response.SectionResponse;
import com.vinicius.voting.section.api.domain.service.SectionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("voting-section/v1/sections")
public class SectionController {

    private final SectionService sectionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<SectionResponse> save(@RequestBody @Valid final SectionRequest sectionRequest) {
        return Mono.just(sectionRequest)
                .doOnNext(request -> log.info("Iniciando criação de sessão - [{}]", request))
                .flatMap(request -> sectionService.save(sectionRequest))
                .map(SectionResponse::of)
                .doOnSuccess(response -> log.info("Finalizado criação de sessão - [{}]", response))
                .doOnError(error -> log.error("Ocorreu um erro na criação da sessão - [{}]", error.toString()));
    }

    @GetMapping("/{sectionUuid}")
    public Mono<SectionResponse> findByUuid(@PathVariable final String sectionUuid) {
        return Mono.just(sectionUuid)
                .doOnNext(request -> log.info("Iniciando busca da sessão com o id - [{}]", sectionUuid))
                .flatMap(request -> sectionService.findByUuid(sectionUuid))
                .map(SectionResponse::of)
                .doOnSuccess(response -> log.info("Finalizado busca da sessão - [{}]", response))
                .doOnError(error -> log.error("Ocorreu um erro na busca da sessão - [{}]", error.toString()));
    }
}
