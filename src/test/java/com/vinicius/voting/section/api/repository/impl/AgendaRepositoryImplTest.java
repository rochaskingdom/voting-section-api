package com.vinicius.voting.section.api.repository.impl;

import com.vinicius.voting.section.api.domain.model.Agenda;
import com.vinicius.voting.section.api.domain.repository.impl.AgendaRepositoryImpl;
import com.vinicius.voting.section.api.domain.repository.reactivemongo.AgendaReactiveMongoRepository;
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
@DisplayName("Repository - AgendaRepositoryImpl")
class AgendaRepositoryImplTest {

    @Mock
    private AgendaReactiveMongoRepository agendaReactiveMongoRepository;

    @InjectMocks
    private AgendaRepositoryImpl agendaRepository;

    @Test
    @DisplayName("Deve criar pauta.")
    void shouldCreateAgenda() {
        final var agenda = Agenda.newAgenda("Pauta 1");

        when(agendaReactiveMongoRepository.save(any()))
                .thenReturn(Mono.just(agenda));

        agendaRepository.save(agenda)
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    @DisplayName("Deve listar todas as pautas.")
    void shouldListAllAgendas() {
        final var agenda = Agenda.newAgenda("Pauta 1");

        when(agendaReactiveMongoRepository.findAll())
                .thenReturn(Flux.just(agenda));

        agendaRepository.listAll()
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    @DisplayName("Deve buscar pauta por uuid.")
    void shouldFindAgendaByUuid() {
        final var agenda = Agenda.newAgenda("Pauta 1");

        when(agendaReactiveMongoRepository.findByUuid(agenda.getUuid()))
                .thenReturn(Mono.just(agenda));

        agendaRepository.findByUuid(agenda.getUuid())
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
    }
}
