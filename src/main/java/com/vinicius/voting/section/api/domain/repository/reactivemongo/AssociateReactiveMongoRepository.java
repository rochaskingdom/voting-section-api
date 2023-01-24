package com.vinicius.voting.section.api.domain.repository.reactivemongo;

import com.vinicius.voting.section.api.domain.model.Associate;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface AssociateReactiveMongoRepository extends ReactiveMongoRepository<Associate, String> {
    Mono<Associate> findByUuid(final String uuid);

    Mono<Associate> findByDocumentNumber(final String documentNumber);
}
