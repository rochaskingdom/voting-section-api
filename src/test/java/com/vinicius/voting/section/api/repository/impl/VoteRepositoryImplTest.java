package com.vinicius.voting.section.api.repository.impl;

import com.vinicius.voting.section.api.controller.request.VoteRequest;
import com.vinicius.voting.section.api.domain.model.Vote;
import com.vinicius.voting.section.api.domain.repository.impl.VoteRepositoryImpl;
import com.vinicius.voting.section.api.domain.repository.reactivemongo.VoteReactiveMongoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Repository - VoteRepositoryImpl")
class VoteRepositoryImplTest {

    private static final String DOCUMENT_NUMBER = "66567832010";
    private static final String SECTION_UUID = "any sectionUuid";

    @Mock
    private VoteReactiveMongoRepository voteReactiveMongoRepository;

    @InjectMocks
    private VoteRepositoryImpl voteRepository;

    @Test
    @DisplayName("Deve criar voto.")
    void shouldCreateVote() {
        final var voteRequest = VoteRequest.builder()
                .documentNumber(DOCUMENT_NUMBER)
                .build();
        final var vote = Vote.newVote(voteRequest, SECTION_UUID);

        when(voteReactiveMongoRepository.save(Mockito.any(Vote.class)))
                .thenReturn(Mono.just(vote));

        voteRepository.save(vote)
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    @DisplayName("Deve listar todos os votos.")
    void shouldListAllVotes() {
        final var voteRequest = VoteRequest.builder()
                .documentNumber(DOCUMENT_NUMBER)
                .build();
        final var vote = Vote.newVote(voteRequest, SECTION_UUID);

        when(voteReactiveMongoRepository.findAll())
                .thenReturn(Flux.just(vote));

        voteRepository.listAll()
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    @DisplayName("Deve buscar voto por uuid.")
    void shouldFindVoteByUuid() {
        final var voteRequest = VoteRequest.builder()
                .documentNumber(DOCUMENT_NUMBER)
                .build();
        final var vote = Vote.newVote(voteRequest, SECTION_UUID);

        when(voteReactiveMongoRepository.findByUuid(vote.getUuid()))
                .thenReturn(Mono.just(vote));

        voteRepository.findByUuid(vote.getUuid())
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    @DisplayName("Deve listar todos os votos por sessão uuid.")
    void shouldFindAllVotesBySectionUuid() {
        final var voteRequest = VoteRequest.builder()
                .documentNumber(DOCUMENT_NUMBER)
                .build();
        final var vote = Vote.newVote(voteRequest, SECTION_UUID);

        when(voteReactiveMongoRepository.findAllBySectionUuid(vote.getSectionUuid()))
                .thenReturn(Flux.just(vote));

        voteRepository.findAllBySectionUuid(vote.getSectionUuid())
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    @DisplayName("Deve buscar voto por número de documento do associado.")
    void shouldFindVoteByAssociateDocumentNumber() {
        final var voteRequest = VoteRequest.builder()
                .documentNumber(DOCUMENT_NUMBER)
                .build();
        final var vote = Vote.newVote(voteRequest, SECTION_UUID);

        when(voteReactiveMongoRepository.findByAssociateDocumentNumber(vote.getAssociateDocumentNumber()))
                .thenReturn(Mono.just(vote));

        voteRepository.findByAssociateDocumentNumber(vote.getAssociateDocumentNumber())
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    @DisplayName("Deve buscar voto por número de documento do associado e uuid da sessão.")
    void shouldFindVoteByAssociateDocumentNumberAndSectionUuid() {
        final var voteRequest = VoteRequest.builder()
                .documentNumber(DOCUMENT_NUMBER)
                .build();
        final var vote = Vote.newVote(voteRequest, SECTION_UUID);

        when(voteReactiveMongoRepository.findByAssociateDocumentNumberAndSectionUuid(
                        vote.getAssociateDocumentNumber(), vote.getSectionUuid()
                )
        ).thenReturn(Mono.just(vote));

        voteRepository.findByAssociateDocumentNumberAndSectionUuid(vote.getAssociateDocumentNumber(), vote.getSectionUuid())
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
    }
}
