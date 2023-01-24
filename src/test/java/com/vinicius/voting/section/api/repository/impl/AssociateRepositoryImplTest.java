package com.vinicius.voting.section.api.repository.impl;

import com.vinicius.voting.section.api.domain.model.Associate;
import com.vinicius.voting.section.api.domain.repository.impl.AssociateRepositoryImpl;
import com.vinicius.voting.section.api.domain.repository.reactivemongo.AssociateReactiveMongoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Repository - AssociateRepositoryImpl")
class AssociateRepositoryImplTest {

    private static final String DOCUMENT_NUMBER = "66567832010";

    @Mock
    private AssociateReactiveMongoRepository associateReactiveMongoRepository;

    @InjectMocks
    private AssociateRepositoryImpl associateRepository;

    @Test
    @DisplayName("Deve criar associado.")
    void shouldCreateAssociate() {
        final var associate = Associate.newAssociate(DOCUMENT_NUMBER);

        when(associateReactiveMongoRepository.save(any()))
                .thenReturn(Mono.just(associate));

        associateRepository.save(associate)
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    @DisplayName("Deve buscar associado por uuid.")
    void shouldFindAssociateByUuid() {
        final var associate = Associate.newAssociate(DOCUMENT_NUMBER);

        when(associateReactiveMongoRepository.findByUuid(any()))
                .thenReturn(Mono.just(associate));

        associateRepository.findByUuid(associate.getUuid())
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    @DisplayName("Deve buscar associado por número de documento.")
    void shouldFindAssociateByDocumentNumber() {
        final var associate = Associate.newAssociate(DOCUMENT_NUMBER);

        when(associateReactiveMongoRepository.findByDocumentNumber(any()))
                .thenReturn(Mono.just(associate));

        associateRepository.findByDocumentNumber(associate.getDocumentNumber())
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
    }
}
