package com.vinicius.voting.section.api.domain.service.impl;

import com.vinicius.voting.section.api.controller.request.SectionRequest;
import com.vinicius.voting.section.api.domain.model.Section;
import com.vinicius.voting.section.api.domain.repository.SectionRepository;
import com.vinicius.voting.section.api.domain.service.AgendaService;
import com.vinicius.voting.section.api.domain.service.SectionService;
import com.vinicius.voting.section.api.exception.InvalidSectionException;
import com.vinicius.voting.section.api.exception.SectionNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class SectionServiceImpl implements SectionService {

    private final SectionRepository sectionRepository;
    private final AgendaService agendaService;

    @Override
    public Mono<Section> save(final SectionRequest sectionRequest) {
        return agendaService
                .findByUuid(sectionRequest.agendaUuid())
                .map(agenda -> Section.newSection(sectionRequest, agenda))
                .flatMap(this::dateValidation)
                .flatMap(sectionRepository::save)
                .doOnNext(section -> log.info("Sessão criada com sucesso. - [{}]", section));
    }

    private Mono<Section> dateValidation(final Section section) {
        if (section.getClosingDate().isAfter(section.getOpeningDate())) {
            return Mono.just(section);
        }
        return Mono.error(InvalidSectionException::new);
    }

    @Override
    public Flux<Section> listAll() {
        return sectionRepository.listAll();
    }

    @Override
    public Mono<Section> findByUuid(final String uuid) {
        return sectionRepository.findByUuid(uuid)
                .doOnNext(section -> log.info("Sessão encontrada com sucesso. - [{}]", section))
                .switchIfEmpty(Mono.error(SectionNotFoundException::new));
    }
}
