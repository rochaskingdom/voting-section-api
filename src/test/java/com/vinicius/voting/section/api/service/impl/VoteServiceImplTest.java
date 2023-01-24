package com.vinicius.voting.section.api.service.impl;

import com.vinicius.voting.section.api.controller.request.SectionRequest;
import com.vinicius.voting.section.api.controller.request.VoteRequest;
import com.vinicius.voting.section.api.domain.model.Agenda;
import com.vinicius.voting.section.api.domain.model.Associate;
import com.vinicius.voting.section.api.domain.model.Section;
import com.vinicius.voting.section.api.domain.model.Vote;
import com.vinicius.voting.section.api.domain.model.enums.VoteMessageEnum;
import com.vinicius.voting.section.api.domain.repository.VoteRepository;
import com.vinicius.voting.section.api.domain.service.AssociateService;
import com.vinicius.voting.section.api.domain.service.SectionService;
import com.vinicius.voting.section.api.domain.service.impl.VoteServiceImpl;
import com.vinicius.voting.section.api.exception.InvalidAssociteVoteException;
import com.vinicius.voting.section.api.exception.InvalidSectionVotesException;
import com.vinicius.voting.section.api.exception.InvalidVoteSectionException;
import com.vinicius.voting.section.api.exception.VoteNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Service - VoteServiceImpl")
class VoteServiceImplTest {

    private static final String DOCUMENT_NUMBER = "66567832010";
    private static final String SECTION_UUID = "any sectionUuid";

    @Mock
    private VoteRepository voteRepository;

    @Mock
    private SectionService sectionService;

    @Mock
    private AssociateService associateService;

    @InjectMocks
    VoteServiceImpl voteService;

    @Test
    @DisplayName("Deve realizar voto.")
    void shouldVote() {
        final var associate = Associate.newAssociate(DOCUMENT_NUMBER);

        final var agenda = Agenda.newAgenda("agenda 1");

        final var sectionRequest = SectionRequest.builder()
                .agendaUuid(agenda.getUuid())
                .description("Sessão 1")
                .build();

        final var section = Section.newSection(sectionRequest, agenda);

        final var voteRequest = VoteRequest.builder()
                .documentNumber(DOCUMENT_NUMBER)
                .build();
        final var vote = Vote.newVote(voteRequest, section.getUuid());

        final var voteRequest2 = VoteRequest.builder()
                .vote(VoteMessageEnum.YES)
                .documentNumber(DOCUMENT_NUMBER)
                .build();

        final var vote2 = Vote.newVote(voteRequest2, section.getUuid());

        when(associateService.findByDocumentNumber(any()))
                .thenReturn(Mono.just(associate));

        when(sectionService.findByUuid(any()))
                .thenReturn(Mono.just(section));

        when(voteRepository.findByAssociateDocumentNumberAndSectionUuid(any(), any()))
                .thenReturn(Mono.just(vote));

        when(voteRepository.save(any()))
                .thenReturn(Mono.just(vote2));

        voteService.addVote(voteRequest2, section.getUuid())
                .as(StepVerifier::create)
                .verifyComplete();
    }

    @Test
    @DisplayName("Deve retornar o error InvalidVoteSectionException quando realizar voto em uma sessão fechada.")
    void shouldThrowErrorWhenInvalidVoteSection() {
        final var associate = Associate.newAssociate(DOCUMENT_NUMBER);

        final var agenda = Agenda.newAgenda("agenda 1");

        final var dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        final var closingDateTime = LocalDateTime.now().minusMinutes(5L).format(dateTimeFormatter);
        final var sectionRequest = SectionRequest.builder()
                .agendaUuid(agenda.getUuid())
                .description("Sessão 1")
                .closingDateTime(closingDateTime)
                .build();

        final var section = Section.newSection(sectionRequest, agenda);

        final var voteRequest = VoteRequest.builder()
                .vote(VoteMessageEnum.YES)
                .documentNumber(DOCUMENT_NUMBER)
                .build();

        when(associateService.findByDocumentNumber(any()))
                .thenReturn(Mono.just(associate));

        when(sectionService.findByUuid(any()))
                .thenReturn(Mono.just(section));

        voteService.addVote(voteRequest, section.getUuid())
                .as(StepVerifier::create)
                .expectError(InvalidVoteSectionException.class)
                .verify();
    }

    @Test
    @DisplayName("Deve retornar o error InvalidAssociteVoteException quando o associado tentar realizar mais de um voto.")
    void shouldThrowErrorWhenVote() {
        final var associate = Associate.newAssociate(DOCUMENT_NUMBER);

        final var agenda = Agenda.newAgenda("agenda 1");

        final var sectionRequest = SectionRequest.builder()
                .agendaUuid(agenda.getUuid())
                .description("Sessão 1")
                .build();

        final var section = Section.newSection(sectionRequest, agenda);

        final var voteRequest = VoteRequest.builder()
                .vote(VoteMessageEnum.YES)
                .documentNumber(DOCUMENT_NUMBER)
                .build();
        final var vote = Vote.newVote(voteRequest, section.getUuid());

        when(associateService.findByDocumentNumber(any()))
                .thenReturn(Mono.just(associate));

        when(sectionService.findByUuid(any()))
                .thenReturn(Mono.just(section));

        when(voteRepository.findByAssociateDocumentNumberAndSectionUuid(any(), any()))
                .thenReturn(Mono.just(vote));

        voteService.addVote(voteRequest, section.getUuid())
                .as(StepVerifier::create)
                .expectError(InvalidAssociteVoteException.class)
                .verify();
    }

    @Test
    @DisplayName("Deve buscar associado por número de documento.")
    void shouldFindVoteByDocumentNumber() {
        final var voteRequest = VoteRequest.builder()
                .vote(VoteMessageEnum.YES)
                .documentNumber(DOCUMENT_NUMBER)
                .build();

        final var vote = Vote.newVote(voteRequest, SECTION_UUID);

        when(voteRepository.findByAssociateDocumentNumber(any()))
                .thenReturn(Mono.just(vote));

        voteService.findByAssociateDocumentNumber(DOCUMENT_NUMBER)
                .as(StepVerifier::create)
                .assertNext(response -> {
                    assertNotNull(response);
                    assertEquals(vote.getId(), response.getId());
                    assertEquals(vote.getUuid(), response.getUuid());
                    assertEquals(vote.getVoteMessage(), VoteMessageEnum.YES);
                    assertEquals(vote.getAssociateDocumentNumber(), DOCUMENT_NUMBER);
                    assertEquals(vote.getSectionUuid(), SECTION_UUID);
                }).verifyComplete();
    }

    @Test
    @DisplayName("Deve retornar o erro VoteNotFoundException quando não encontrar o voto.")
    void shouldThrowErrorWhenVoteNotFound() {
        when(voteRepository.findByAssociateDocumentNumber(any()))
                .thenReturn(Mono.empty());

        voteService.findByAssociateDocumentNumber(DOCUMENT_NUMBER)
                .as(StepVerifier::create)
                .expectError(VoteNotFoundException.class)
                .verify();
    }

    @Test
    @DisplayName("Deve listar todos os votos por sessão uuid.")
    void shouldFindAllVotesBySectionUuid() {
        final var voteRequest = VoteRequest.builder()
                .vote(VoteMessageEnum.YES)
                .documentNumber(DOCUMENT_NUMBER)
                .build();

        final var vote = Vote.newVote(voteRequest, SECTION_UUID);

        when(voteRepository.findAllBySectionUuid(any()))
                .thenReturn(Flux.just(vote));

        voteService.findAllBySectionUuid(SECTION_UUID)
                .as(StepVerifier::create)
                .assertNext(response -> {
                    assertNotNull(response);
                    assertEquals(vote.getId(), response.getId());
                    assertEquals(vote.getUuid(), response.getUuid());
                    assertEquals(vote.getVoteMessage(), VoteMessageEnum.YES);
                    assertEquals(vote.getAssociateDocumentNumber(), DOCUMENT_NUMBER);
                    assertEquals(vote.getSectionUuid(), SECTION_UUID);
                }).verifyComplete();
    }

    @Test
    @DisplayName("Deve retornar o erro InvalidSectionVotesException quando não encontrar os voto pela sessão uuid.")
    void shouldThrowErrorWhenNotFoundVotesBySectionUuid() {
        when(voteRepository.findAllBySectionUuid(any()))
                .thenReturn(Flux.empty());

        voteService.findAllBySectionUuid(DOCUMENT_NUMBER)
                .as(StepVerifier::create)
                .expectError(InvalidSectionVotesException.class)
                .verify();
    }
}
