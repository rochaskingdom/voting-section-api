package com.vinicius.voting.section.api.domain.service.impl;

import com.vinicius.voting.section.api.controller.request.VoteRequest;
import com.vinicius.voting.section.api.domain.model.Associate;
import com.vinicius.voting.section.api.domain.model.Section;
import com.vinicius.voting.section.api.domain.model.Vote;
import com.vinicius.voting.section.api.domain.repository.VoteRepository;
import com.vinicius.voting.section.api.domain.service.AssociateService;
import com.vinicius.voting.section.api.domain.service.SectionService;
import com.vinicius.voting.section.api.domain.service.VoteService;
import com.vinicius.voting.section.api.exception.InvalidAssociteVoteException;
import com.vinicius.voting.section.api.exception.InvalidSectionVotesException;
import com.vinicius.voting.section.api.exception.InvalidVoteSectionException;
import com.vinicius.voting.section.api.exception.VoteNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;
    private final SectionService sectionService;
    private final AssociateService associateService;

    @Override
    public Mono<Void> addVote(final VoteRequest voteRequest, final String sectionUuid) {
        return associateService
                .findByDocumentNumber(voteRequest.documentNumber())
                .flatMap(associate -> validateSectionExpiration(sectionUuid).thenReturn(associate))
                .flatMap(associate -> validateVote(sectionUuid, associate))
                .switchIfEmpty(Mono.defer(() -> saveVote(voteRequest, sectionUuid)))
                .then();
    }

    private Mono<Section> validateSectionExpiration(final String sectionUuid) {
        log.info("Validando tempo de abertura da sessão.");
        return sectionService
                .findByUuid(sectionUuid)
                .flatMap(this::sectionVerifyClosingDate);
    }

    private Mono<Section> sectionVerifyClosingDate(final Section section) {
        final var now = LocalDateTime.now();
        if (now.isBefore(section.getClosingDate())) {
            log.info("Tempo de abertura da sessão validada.");
            return Mono.just(section);
        }
        return Mono.error(new InvalidVoteSectionException());
    }

    private Mono<Vote> validateVote(final String sectionUuid, final Associate associate) {
        log.info("Validando se o associado já realizou algum voto na sessão.");
        return voteRepository.findByAssociateDocumentNumberAndSectionUuid(associate.getDocumentNumber(), sectionUuid)
                .flatMap(this::validateVote);
    }

    private Mono<Vote> validateVote(final Vote vote) {
        if (isNull(vote.getVoteMessage())) {
            log.info("Associado validado.");
            return Mono.empty();
        }
        return Mono.error(InvalidAssociteVoteException::new);
    }

    private Mono<Vote> saveVote(final VoteRequest voteRequest, final String sectionUuid) {
        return voteRepository.save(Vote.newVote(voteRequest, sectionUuid));
    }

    @Override
    public Mono<Vote> findByAssociateDocumentNumber(final String associateUuid) {
        return voteRepository.findByAssociateDocumentNumber(associateUuid)
                .switchIfEmpty(Mono.error(VoteNotFoundException::new));
    }

    @Override
    public Flux<Vote> findAllBySectionUuid(final String sectionUuid) {
        return voteRepository.findAllBySectionUuid(sectionUuid)
                .switchIfEmpty(Mono.error(InvalidSectionVotesException::new));
    }

}
