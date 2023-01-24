package com.vinicius.voting.section.api.service.impl;

import com.vinicius.voting.section.api.controller.request.SectionRequest;
import com.vinicius.voting.section.api.controller.request.VoteRequest;
import com.vinicius.voting.section.api.domain.model.Agenda;
import com.vinicius.voting.section.api.domain.model.Section;
import com.vinicius.voting.section.api.domain.model.Vote;
import com.vinicius.voting.section.api.domain.model.enums.VoteMessageEnum;
import com.vinicius.voting.section.api.domain.service.SectionService;
import com.vinicius.voting.section.api.domain.service.VoteService;
import com.vinicius.voting.section.api.domain.service.impl.VoteAmountServiceServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Service - VoteAmountServiceImpl")
class VoteAmountServiceImplTest {

    private static final String DOCUMENT_NUMBER = "66567832010";

    @Mock
    private VoteService voteService;

    @Mock
    private SectionService sectionService;

    @InjectMocks
    VoteAmountServiceServiceImpl voteAmountServiceService;

    @Test
    @DisplayName("Deve realizar o cálculo de votos")
    void shouldCalculateVotes() {
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

        when(voteService.findAllBySectionUuid(any()))
                .thenReturn(Flux.just(vote));

        when(sectionService.findByUuid(any()))
                .thenReturn(Mono.just(section));

        voteAmountServiceService.totalVotes(section.getUuid())
                .as(StepVerifier::create)
                .assertNext(response -> {
                    assertNotNull(response);
                    assertNotNull(response.votes());
                    assertNotNull(response.votesAmount());
                    assertEquals(section.getUuid(), response.uuid());
                    assertEquals(1, response.votesAmount().get("totalVotes"));
                    assertEquals(1, response.votesAmount().get(VoteMessageEnum.YES.name()));
                    assertEquals(0, response.votesAmount().get(VoteMessageEnum.NO.name()));
                    assertEquals(section.getDescription(), response.description());
                    assertEquals(section.getAgenda().getUuid(), response.agenda().uuid());
                    assertEquals(section.getAgenda().getName(), response.agenda().name());
                    assertEquals(section.getOpeningDate(), response.openingDate());
                    assertEquals(section.getClosingDate(), response.closingDate());
                }).verifyComplete();
    }
}
