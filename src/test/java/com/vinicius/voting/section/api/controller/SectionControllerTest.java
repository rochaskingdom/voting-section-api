package com.vinicius.voting.section.api.controller;

import com.vinicius.voting.section.api.controller.request.SectionRequest;
import com.vinicius.voting.section.api.domain.model.Agenda;
import com.vinicius.voting.section.api.domain.model.Section;
import com.vinicius.voting.section.api.domain.service.SectionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;

@ExtendWith(MockitoExtension.class)
@DisplayName("Controller - SectionController")
class SectionControllerTest extends BaseTestController<SectionController> {

    @Mock
    private SectionService sectionService;

    @Override
    public SectionController getController() {
        return new SectionController(sectionService);
    }

    @Override
    public String getBaseUrl() {
        return "/voting-section/v1/sections";
    }

    @Test
    @DisplayName("Deve retornar sessão criada e status code 201 created.")
    void shouldReturnCreateSectionAndStatusCode201Created() {
        final var agenda = Agenda.newAgenda("agenda 1");
        final var dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        final var closingDateTime = LocalDateTime.now().plusMinutes(5L).format(dateTimeFormatter);
        final var sectionBodyRequest = SectionRequest.builder()
                .agendaUuid(agenda.getUuid())
                .description("Sessão 1")
                .closingDateTime(closingDateTime)
                .build();

        final var section = Section.newSection(sectionBodyRequest, agenda);

        when(sectionService.save(any()))
                .thenReturn(Mono.just(section));

        webTestClient.post()
                .bodyValue(sectionBodyRequest)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .consumeWith(document("post-section",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("agendaUuid").description("Identificador da pauta para vincular a sessão."),
                                fieldWithPath("description").description("Descrição da sessão."),
                                fieldWithPath("closingDateTime")
                                        .description("Data/hora de fechamento da sessão. Caso não seja definido o padrão será 1 minuto").optional()
                        )
                ));
    }

    @Test
    @DisplayName("Deve retornar a busca de uma sessão por uuid e status code 200 ok.")
    void shouldReturnFindSectionByUuidAndStatusCode200Ok() {
        final var agenda = Agenda.newAgenda("agenda 1");
        final var dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        final var closingDateTime = LocalDateTime.now().plusMinutes(5L).format(dateTimeFormatter);
        final var sectionBodyRequest = SectionRequest.builder()
                .agendaUuid(agenda.getUuid())
                .description("Sessão 1")
                .closingDateTime(closingDateTime)
                .build();

        final var section = Section.newSection(sectionBodyRequest, agenda);

        when(sectionService.findByUuid(any()))
                .thenReturn(Mono.just(section));

        webTestClient.get()
                .uri("/{sectionUuid}", section.getUuid())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(document("find-by-uuid-section",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("sectionUuid").description("Identificador da sessão.")
                        )
                ));
    }
}
