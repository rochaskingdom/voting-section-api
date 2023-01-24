package com.vinicius.voting.section.api.controller;

import com.vinicius.voting.section.api.controller.request.SectionRequest;
import com.vinicius.voting.section.api.controller.request.VoteRequest;
import com.vinicius.voting.section.api.controller.response.AgendaResponse;
import com.vinicius.voting.section.api.controller.response.SectionResponse;
import com.vinicius.voting.section.api.controller.response.VoteResponse;
import com.vinicius.voting.section.api.domain.model.Agenda;
import com.vinicius.voting.section.api.domain.model.Section;
import com.vinicius.voting.section.api.domain.model.enums.VoteMessageEnum;
import com.vinicius.voting.section.api.domain.service.VoteAmountService;
import com.vinicius.voting.section.api.domain.service.VoteService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;

@ExtendWith(MockitoExtension.class)
@DisplayName("Controller - VoteController")
class VoteControllerTest extends BaseTestController<VoteController> {

    private static final String DOCUMENT_NUMBER = "66567832010";
    private static final String SECTION_UUID = "any sectionUuid";

    @Mock
    private VoteService sectionService;

    @Mock
    private VoteAmountService voteAmountService;

    @Override
    public VoteController getController() {
        return new VoteController(sectionService, voteAmountService);
    }

    @Override
    public String getBaseUrl() {
        return "/voting-section/v1/votes";
    }

    @Test
    @DisplayName("Deve retornar vazio e status code 204 no content.")
    void shouldReturnVoidAndStatusCode204NoContent() {
        final var voteRequest = VoteRequest.builder()
                .vote(VoteMessageEnum.YES)
                .documentNumber(DOCUMENT_NUMBER)
                .build();

        when(sectionService.addVote(any(), any()))
                .thenReturn(Mono.empty());

        webTestClient.post()
                .uri("/{sectionUuid}", SECTION_UUID)
                .bodyValue(voteRequest)
                .exchange()
                .expectStatus().isNoContent()
                .expectBody()
                .consumeWith(document("add-vote",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("sectionUuid").description("Identificador da sessão.")
                        )));
    }

    @Test
    @DisplayName("Deve retornar o cáculo de votos de uma sessão por uuid e status code 200 ok.")
    void shouldReturnCalculateVotesOfSectionByUuidAndStatusCode200Ok() {
        final var agenda = Agenda.newAgenda("agenda 1");
        final var dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        final var closingDateTime = LocalDateTime.now().plusMinutes(5L).format(dateTimeFormatter);
        final var sectionBodyRequest = SectionRequest.builder()
                .agendaUuid(agenda.getUuid())
                .description("Sessão 1")
                .closingDateTime(closingDateTime)
                .build();

        final var section = Section.newSection(sectionBodyRequest, agenda);

        final var voteRequest = VoteResponse.builder()
                .uuid(UUID.randomUUID().toString())
                .voteMessage(VoteMessageEnum.YES)
                .associateDocumentNumber(DOCUMENT_NUMBER)
                .createdAt(Instant.now())
                .build();

        final var votesAmount = Map.of(
                VoteMessageEnum.YES.name(), 1,
                VoteMessageEnum.NO.name(), 0,
                "totalAmount", 1
        );

        final var sectionResponse = SectionResponse.builder()
                .uuid(section.getUuid())
                .agenda(AgendaResponse.of(section.getAgenda()))
                .openingDate(section.getOpeningDate())
                .closingDate(section.getClosingDate())
                .votes(List.of(voteRequest))
                .votesAmount(votesAmount)
                .build();

        when(voteAmountService.totalVotes(any()))
                .thenReturn(Mono.just(sectionResponse));

        webTestClient.get()
                .uri("/result/{sectionUuid}", section.getUuid())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(document("find-by-uuid-votes-section-result",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("sectionUuid").description("Identificador da sessão.")
                        )
                ));
    }
}
