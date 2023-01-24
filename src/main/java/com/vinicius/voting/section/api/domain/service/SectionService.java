package com.vinicius.voting.section.api.domain.service;

import com.vinicius.voting.section.api.controller.request.SectionRequest;
import com.vinicius.voting.section.api.domain.model.Section;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SectionService {
    Mono<Section> save(final SectionRequest section);

    Flux<Section> listAll();

    Mono<Section> findByUuid(final String uuid);

}