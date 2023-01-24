package com.vinicius.voting.section.api.repository.impl;

import com.vinicius.voting.section.api.controller.request.SectionRequest;
import com.vinicius.voting.section.api.domain.model.Agenda;
import com.vinicius.voting.section.api.domain.model.Section;
import com.vinicius.voting.section.api.domain.repository.impl.SectionRepositoryImpl;
import com.vinicius.voting.section.api.domain.repository.reactivemongo.SectionReactiveMongoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Repository - SectionRepositoryImpl")
class SectionRepositoryImplTest {

    @Mock
    private SectionReactiveMongoRepository sectionReactiveMongoRepository;

    @InjectMocks
    private SectionRepositoryImpl sectionRepository;

    @Test
    @DisplayName("Deve criar sessão.")
    void shouldCreateSection() {
        final var agenda = Agenda.newAgenda("agenda 1");

        final var sectionRequest = SectionRequest.builder()
                .agendaUuid(agenda.getUuid())
                .description("Sessão 1")
                .build();

        final var section = Section.newSection(sectionRequest, agenda);

        when(sectionReactiveMongoRepository.save(any()))
                .thenReturn(Mono.just(section));

        sectionRepository.save(section)
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    @DisplayName("Deve listar todas as sessões.")
    void shouldListAllSection() {
        final var agenda = Agenda.newAgenda("agenda 1");

        final var sectionRequest = SectionRequest.builder()
                .agendaUuid(agenda.getUuid())
                .description("Sessão 1")
                .build();

        final var section = Section.newSection(sectionRequest, agenda);

        when(sectionReactiveMongoRepository.findAll())
                .thenReturn(Flux.just(section));

        sectionRepository.listAll()
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    @DisplayName("Deve buscar sessão por uuid.")
    void shouldFindSectionByUuid() {
        final var agenda = Agenda.newAgenda("agenda 1");

        final var sectionRequest = SectionRequest.builder()
                .agendaUuid(agenda.getUuid())
                .description("Sessão 1")
                .build();

        final var section = Section.newSection(sectionRequest, agenda);

        when(sectionReactiveMongoRepository.findByUuid(any()))
                .thenReturn(Mono.just(section));

        sectionRepository.findByUuid(section.getUuid())
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
    }
}
