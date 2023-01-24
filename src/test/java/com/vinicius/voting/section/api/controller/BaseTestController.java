package com.vinicius.voting.section.api.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.modifyUris;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.documentationConfiguration;

@ActiveProfiles("test")
@ExtendWith(RestDocumentationExtension.class)
abstract class BaseTestController<Controller> {

    protected WebTestClient webTestClient;

    public abstract Controller getController();

    public String getBaseUrl() {
        return "";
    }

    @BeforeEach
    void setup(RestDocumentationContextProvider restDocumentation) {

        webTestClient = WebTestClient
                .bindToController(getController())
                .configureClient()
                .baseUrl(getBaseUrl())
                .filter(documentationConfiguration(restDocumentation)
                        .operationPreprocessors()
                        .withResponseDefaults(prettyPrint())
                        .withRequestDefaults(
                                prettyPrint(),
                                modifyUris()
                                        .scheme("http")
                                        .host("localhost")
                                        .port(8080)
                        )
                )
                .build();
    }

}
