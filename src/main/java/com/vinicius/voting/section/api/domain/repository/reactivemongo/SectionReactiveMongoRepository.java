package com.vinicius.voting.section.api.domain.repository.reactivemongo;

import com.vinicius.voting.section.api.domain.model.Section;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface SectionReactiveMongoRepository extends ReactiveMongoRepository<Section, String> {
    Mono<Section> findByUuid(final String uuid);
}
