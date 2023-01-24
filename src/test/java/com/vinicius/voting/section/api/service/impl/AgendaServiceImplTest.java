package com.vinicius.voting.section.api.service.impl;

import com.vinicius.voting.section.api.controller.request.AgendaRequest;
import com.vinicius.voting.section.api.domain.model.Agenda;
import com.vinicius.voting.section.api.domain.repository.AgendaRepository;
import com.vinicius.voting.section.api.domain.service.impl.AgendaServiceImpl;
import com.vinicius.voting.section.api.exception.AgendaNotFoundException;
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
@DisplayName("Service - AgendaServiceImpl")
class AgendaServiceImplTest {

    @Mock
    private AgendaRepository agendaRepository;

    @InjectMocks
    private AgendaServiceImpl agendaService;

    @Test
    @DisplayName("Deve criar uma pauta.")
    void shouldCreateAgenda() {
        final var agenda = Agenda.newAgenda("Pauta 1");
        final var agendaRequest = new AgendaRequest("Pauta 1");

        when(agendaRepository.save(any()))
                .thenReturn(Mono.just(agenda));

        agendaService.save(agendaRequest)
                .as(StepVerifier::create)
                .assertNext(response -> {
                    assertNotNull(response);
                    assertEquals(agenda.getId(), response.getId());
                    assertEquals(agenda.getName(), response.getName());
                    assertEquals(agenda.getUuid(), response.getUuid());
                }).verifyComplete();
    }

    @Test
    @DisplayName("Deve listar todas as pautas.")
    void shouldListAllAgendas() {
        final var agenda = Agenda.newAgenda("Pauta 1");

        when(agendaRepository.listAll())
                .thenReturn(Flux.just(agenda));

        agendaService.listAll()
                .as(StepVerifier::create)
                .assertNext(response -> {
                    assertNotNull(response);
                    assertEquals(agenda.getId(), response.getId());
                    assertEquals(agenda.getName(), response.getName());
                    assertEquals(agenda.getUuid(), response.getUuid());
                }).verifyComplete();
    }

    @Test
    @DisplayName("Deve buscar pauta por uuid.")
    void shouldFindAgendaByUuid() {
        final var agenda = Agenda.newAgenda("Pauta 1");
        final var agendaUuid = agenda.getUuid();

        when(agendaRepository.findByUuid(agendaUuid))
                .thenReturn(Mono.just(agenda));

        agendaService.findByUuid(agendaUuid)
                .as(StepVerifier::create)
                .assertNext(response -> {
                    assertNotNull(response);
                    assertEquals(agenda.getId(), response.getId());
                    assertEquals(agenda.getName(), response.getName());
                    assertEquals(agenda.getUuid(), response.getUuid());
                }).verifyComplete();
    }

    @Test
    @DisplayName("Deve retornar o erro AgendaNotFoundException quando não encontrar a pauta.")
    void shouldThrowErrorWhenAgendaNotFound() {
        when(agendaRepository.findByUuid(any()))
                .thenReturn(Mono.empty());

        agendaService.findByUuid("any uuid")
                .as(StepVerifier::create)
                .expectError(AgendaNotFoundException.class)
                .verify();
    }
}
