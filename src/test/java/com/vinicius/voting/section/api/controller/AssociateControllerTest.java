package com.vinicius.voting.section.api.controller;

import com.vinicius.voting.section.api.controller.request.AssociateRequest;
import com.vinicius.voting.section.api.domain.model.Associate;
import com.vinicius.voting.section.api.domain.service.AssociateService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
@DisplayName("Controller - AssociateController")
class AssociateControllerTest extends BaseTestController<AssociateController> {

    private static final String DOCUMENT_NUMBER = "66567832010";

    @Mock
    private AssociateService associateService;

    @Override
    public AssociateController getController() {
        return new AssociateController(associateService);
    }

    @Override
    public String getBaseUrl() {
        return "/voting-section/v1/associates";
    }

    @Test
    @DisplayName("Deve retornar associado criado e status code 201 created.")
    void shouldReturnCreateAssociateAndStatusCode201Created() {
        final var associate = Associate.newAssociate(DOCUMENT_NUMBER);
        final var associateBodyRequest = new AssociateRequest(DOCUMENT_NUMBER);

        when(associateService.save(any()))
                .thenReturn(Mono.just(associate));

        webTestClient.post()
                .bodyValue(associateBodyRequest)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .consumeWith(document("post-associate",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("documentNumber").description("Número de documento (CPF/CNPJ) de identificação do associado.")
                        )
                ));
    }

    @Test
    @DisplayName("Deve retornar a busca de uma pauta por número de documento e status code 200 ok.")
    void shouldReturnFindAgendaByUuidAndStatusCode200Ok() {
        final var associate = Associate.newAssociate(DOCUMENT_NUMBER);

        when(associateService.findByDocumentNumber(any()))
                .thenReturn(Mono.just(associate));

        webTestClient.get()
                .uri("/{documentNumber}", associate.getUuid())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(document("find-by-document-number-associate",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("documentNumber").description("Número de documento do associado.")
                        )
                ));
    }
}
