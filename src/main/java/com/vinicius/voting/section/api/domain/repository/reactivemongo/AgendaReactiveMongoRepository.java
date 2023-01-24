package com.vinicius.voting.section.api.domain.repository.reactivemongo;

import com.vinicius.voting.section.api.domain.model.Agenda;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface AgendaReactiveMongoRepository extends ReactiveMongoRepository<Agenda, String> {
    Mono<Agenda> findByUuid(final String uuid);
}
