package com.vinicius.voting.section.api.controller;

import com.vinicius.voting.section.api.controller.request.AgendaRequest;
import com.vinicius.voting.section.api.domain.model.Agenda;
import com.vinicius.voting.section.api.domain.service.AgendaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;

@ExtendWith(MockitoExtension.class)
@DisplayName("Controller - AgendaController")
class AgendaControllerTest extends BaseTestController<AgendaController> {

    @Mock
    private AgendaService agendaService;

    @Override
    public AgendaController getController() {
        return new AgendaController(agendaService);
    }

    @Override
    public String getBaseUrl() {
        return "/voting-section/v1/agendas";
    }

    @Test
    @DisplayName("Deve retornar pauta criada e status code 201 created.")
    void shouldReturnCreateAgendaAndStatusCode201Created() {
        final var agendaBodyRequest = new AgendaRequest("Pauta 1");
        final var agenda = Agenda.newAgenda(agendaBodyRequest.name());

        when(agendaService.save(any()))
                .thenReturn(Mono.just(agenda));

        webTestClient.post()
                .bodyValue(agendaBodyRequest)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .consumeWith(document("post-agenda",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("name").description("Nome da para identificação da pauta.")
                        )
                ));
    }

    @Test
    @DisplayName("Deve retornar a lista de todas as pautas e status code 200 ok.")
    void shouldReturnListAllAgendaAndStatusCode200Ok() {
        final var agenda = Agenda.newAgenda("Pauta 1");

        when(agendaService.listAll())
                .thenReturn(Flux.just(agenda));

        webTestClient.get()
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(document("list-all-agendas",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    @Test
    @DisplayName("Deve retornar a busca de uma pauta por uuid e status code 200 ok.")
    void shouldReturnFindAgendaByUuidAndStatusCode200Ok() {
        final var agenda = Agenda.newAgenda("Pauta 1");

        when(agendaService.findByUuid(agenda.getUuid()))
                .thenReturn(Mono.just(agenda));

        webTestClient.get()
                .uri("/{agendaUuid}", agenda.getUuid())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(document("find-by-uuid-agenda",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("agendaUuid").description("Identificador da pauta.")
                        )
                ));
    }
}
