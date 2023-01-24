package com.vinicius.voting.section.api.service.impl;

import com.vinicius.voting.section.api.controller.request.SectionRequest;
import com.vinicius.voting.section.api.domain.model.Agenda;
import com.vinicius.voting.section.api.domain.model.Section;
import com.vinicius.voting.section.api.domain.repository.SectionRepository;
import com.vinicius.voting.section.api.domain.service.AgendaService;
import com.vinicius.voting.section.api.domain.service.impl.SectionServiceImpl;
import com.vinicius.voting.section.api.exception.InvalidSectionException;
import com.vinicius.voting.section.api.exception.SectionNotFoundException;
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
@DisplayName("Service - SectionServiceImpl")
class SectionServiceImplTest {

    @Mock
    private SectionRepository sectionRepository;

    @Mock
    private AgendaService agendaService;

    @InjectMocks
    private SectionServiceImpl sectionService;

    @Test
    @DisplayName("Deve criar uma sessão.")
    void shouldCreateSection() {
        final var agenda = Agenda.newAgenda("agenda 1");

        final var sectionRequest = SectionRequest.builder()
                .agendaUuid(agenda.getUuid())
                .description("Sessão 1")
                .build();

        final var section = Section.newSection(sectionRequest, agenda);

        when(agendaService.findByUuid(any()))
                .thenReturn(Mono.just(agenda));

        when(sectionRepository.save(any()))
                .thenReturn(Mono.just(section));

        sectionService.save(sectionRequest)
                .as(StepVerifier::create)
                .assertNext(response -> {
                    assertNotNull(response);
                    assertEquals(section.getId(), response.getId());
                    assertEquals(section.getUuid(), response.getUuid());
                    assertEquals(section.getDescription(), response.getDescription());
                    assertEquals(section.getAgenda(), response.getAgenda());
                    assertEquals(section.getOpeningDate(), response.getOpeningDate());
                    assertEquals(section.getClosingDate(), response.getClosingDate());
                }).verifyComplete();
    }

    @Test
    @DisplayName("Deve retornar o erro InvalidSectionException ao criar uma sessão com fechamento inválido.")
    void shouldThrowErrorCreateSection() {
        final var agenda = Agenda.newAgenda("agenda 1");
        final var dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        final var closingDateTime = LocalDateTime.now().minusMinutes(5L).format(dateTimeFormatter);
        final var sectionRequest = SectionRequest.builder()
                .agendaUuid(agenda.getUuid())
                .description("Sessão 1")
                .closingDateTime(closingDateTime)
                .build();

        when(agendaService.findByUuid(any()))
                .thenReturn(Mono.just(agenda));

        sectionService.save(sectionRequest)
                .as(StepVerifier::create)
                .expectError(InvalidSectionException.class)
                .verify();
    }

    @Test
    @DisplayName("Deve listar todas sessões.")
    void shouldListAllSections() {
        final var agenda = Agenda.newAgenda("agenda 1");

        final var sectionRequest = SectionRequest.builder()
                .agendaUuid(agenda.getUuid())
                .description("Sessão 1")
                .build();

        final var section = Section.newSection(sectionRequest, agenda);

        when(sectionRepository.listAll())
                .thenReturn(Flux.just(section));

        sectionService.listAll()
                .as(StepVerifier::create)
                .assertNext(response -> {
                    assertNotNull(response);
                    assertEquals(section.getId(), response.getId());
                    assertEquals(section.getUuid(), response.getUuid());
                    assertEquals(section.getDescription(), response.getDescription());
                    assertEquals(section.getAgenda(), response.getAgenda());
                    assertEquals(section.getOpeningDate(), response.getOpeningDate());
                    assertEquals(section.getClosingDate(), response.getClosingDate());
                }).verifyComplete();
    }

    @Test
    @DisplayName("Deve listar todas sessões.")
    void shouldFindSectionByUuid() {
        final var agenda = Agenda.newAgenda("agenda 1");

        final var sectionRequest = SectionRequest.builder()
                .agendaUuid(agenda.getUuid())
                .description("Sessão 1")
                .build();

        final var section = Section.newSection(sectionRequest, agenda);

        when(sectionRepository.findByUuid(any()))
                .thenReturn(Mono.just(section));

        sectionService.findByUuid(section.getUuid())
                .as(StepVerifier::create)
                .assertNext(response -> {
                    assertNotNull(response);
                    assertEquals(section.getId(), response.getId());
                    assertEquals(section.getUuid(), response.getUuid());
                    assertEquals(section.getDescription(), response.getDescription());
                    assertEquals(section.getAgenda(), response.getAgenda());
                    assertEquals(section.getOpeningDate(), response.getOpeningDate());
                    assertEquals(section.getClosingDate(), response.getClosingDate());
                }).verifyComplete();
    }

    @Test
    @DisplayName("Deve retornar o erro SectionNotFoundException quando não encontrar a sessão.")
    void shouldThrowErrorWhenSectionNotFound() {
        when(sectionRepository.findByUuid(any()))
                .thenReturn(Mono.empty());

        sectionService.findByUuid("any uuid")
                .as(StepVerifier::create)
                .expectError(SectionNotFoundException.class)
                .verify();
    }

}
