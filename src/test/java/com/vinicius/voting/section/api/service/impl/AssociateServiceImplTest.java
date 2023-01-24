package com.vinicius.voting.section.api.service.impl;

import com.vinicius.voting.section.api.domain.model.Associate;
import com.vinicius.voting.section.api.domain.repository.AssociateRepository;
import com.vinicius.voting.section.api.domain.service.impl.AssociateServiceImpl;
import com.vinicius.voting.section.api.exception.AssociateNotFoundException;
import com.vinicius.voting.section.api.exception.InvalidAssociateException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@DisplayName("Service - AssociateServiceImpl")
class AssociateServiceImplTest {

    private static final String DOCUMENT_NUMBER = "66567832010";

    @Mock
    private AssociateRepository associateRepository;

    @InjectMocks
    private AssociateServiceImpl associateService;

    @Test
    @DisplayName("Deve criar um associado.")
    void shouldCreateAssociate() {
        final var associate = Associate.newAssociate(DOCUMENT_NUMBER);

        when(associateRepository.findByDocumentNumber(any()))
                .thenReturn(Mono.empty());

        when(associateRepository.save(any()))
                .thenReturn(Mono.just(associate));

        associateService.save(DOCUMENT_NUMBER)
                .as(StepVerifier::create)
                .assertNext(response -> {
                    assertNotNull(response);
                    assertEquals(associate.getId(), response.getId());
                    assertEquals(associate.getUuid(), response.getUuid());
                    assertEquals(associate.getDocumentNumber(), DOCUMENT_NUMBER);
                }).verifyComplete();
    }

    @Test
    @DisplayName("Deve retornar o erro InvalidAssociateException ao tentar criar um associado já existente.")
    void shouldThrowErrorWhenCreateExistingAssociate() {
        final var associate = Associate.newAssociate(DOCUMENT_NUMBER);

        when(associateRepository.findByDocumentNumber(any()))
                .thenReturn(Mono.just(associate));

        associateService.save(DOCUMENT_NUMBER)
                .as(StepVerifier::create)
                .expectError(InvalidAssociateException.class)
                .verify();
    }

    @Test
    @DisplayName("Deve buscar associado por número de documento.")
    void shouldFindAssociateByDocumentNumber() {
        final var associate = Associate.newAssociate(DOCUMENT_NUMBER);

        when(associateRepository.findByDocumentNumber(any()))
                .thenReturn(Mono.just(associate));

        associateService.findByDocumentNumber(DOCUMENT_NUMBER)
                .as(StepVerifier::create)
                .assertNext(response -> {
                    assertNotNull(response);
                    assertEquals(associate.getId(), response.getId());
                    assertEquals(associate.getUuid(), response.getUuid());
                    assertEquals(associate.getDocumentNumber(), DOCUMENT_NUMBER);
                }).verifyComplete();
    }

    @Test
    @DisplayName("Deve retornar o erro AssociateNotFoundException ao tentar buscar associado por número de documento.")
    void shouldThrowErrorWhenFindAssociateByDocumentNumber() {
        when(associateRepository.findByDocumentNumber(any()))
                .thenReturn(Mono.empty());

        associateService.findByDocumentNumber(DOCUMENT_NUMBER)
                .as(StepVerifier::create)
                .expectError(AssociateNotFoundException.class)
                .verify();
    }
}
