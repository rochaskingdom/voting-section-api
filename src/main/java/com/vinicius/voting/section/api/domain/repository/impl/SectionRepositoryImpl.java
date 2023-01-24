package com.vinicius.voting.section.api.domain.repository.impl;

import com.vinicius.voting.section.api.domain.model.Section;
import com.vinicius.voting.section.api.domain.repository.SectionRepository;
import com.vinicius.voting.section.api.domain.repository.reactivemongo.SectionReactiveMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class SectionRepositoryImpl implements SectionRepository {

    private final SectionReactiveMongoRepository repository;

    @Override
    public Mono<Section> save(final Section section) {
        return repository.save(section);
    }

    @Override
    public Flux<Section> listAll() {
        return repository.findAll();
    }

    @Override
    public Mono<Section> findByUuid(final String agendaUuid) {
        return repository.findByUuid(agendaUuid);
    }
}
