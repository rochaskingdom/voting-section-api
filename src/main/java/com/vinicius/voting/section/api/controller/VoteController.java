package com.vinicius.voting.section.api.controller;

import com.vinicius.voting.section.api.controller.request.VoteRequest;
import com.vinicius.voting.section.api.controller.response.SectionResponse;
import com.vinicius.voting.section.api.domain.service.VoteAmountService;
import com.vinicius.voting.section.api.domain.service.VoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("voting-section/v1/votes")
public class VoteController {

    private final VoteService voteService;
    private final VoteAmountService voteAmountService;

    @PostMapping("/{sectionUuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> vote(@RequestBody @Valid final VoteRequest voteRequest, @PathVariable final String sectionUuid) {
        return Mono.just(voteRequest)
                .doOnNext(request -> log.info("Iniciando criação de voto - [{}]", request))
                .flatMap(request -> voteService.addVote(voteRequest, sectionUuid))
                .doOnSuccess(response -> log.info("Finalizado criação de voto"))
                .doOnError(error -> log.error("Ocorreu um erro na criação do voto - [{}]", error.toString()));
    }

    @GetMapping("/result/{sectionUuid}")
    public Mono<SectionResponse> result(@PathVariable final String sectionUuid) {
        return Mono.just(sectionUuid)
                .doOnNext(request -> log.info("Iniciando o calculo de votos - [{}]", sectionUuid))
                .flatMap(request -> voteAmountService.totalVotes(sectionUuid))
                .doOnSuccess(response -> log.info("Finalizado cálculo de votos - [{}]", response))
                .doOnError(error -> log.error("Ocorreu um erro ao realizar o calculo de votos - [{}]", error.toString()));
    }
}
