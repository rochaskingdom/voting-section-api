package com.vinicius.voting.section.api.domain.repository;

import com.vinicius.voting.section.api.domain.model.Section;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SectionRepository {
    Mono<Section> save(final Section section);

    Flux<Section> listAll();

    Mono<Section> findByUuid(final String agendaUuid);
}
