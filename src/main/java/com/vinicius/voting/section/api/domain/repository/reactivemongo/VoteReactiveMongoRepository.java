package com.vinicius.voting.section.api.domain.repository.reactivemongo;

import com.vinicius.voting.section.api.domain.model.Vote;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface VoteReactiveMongoRepository extends ReactiveMongoRepository<Vote, String> {
    Mono<Vote> findByUuid(final String uuid);

    Flux<Vote> findAllBySectionUuid(final String sectionUuid);

    Mono<Vote> findByAssociateDocumentNumber(final String associateUuid);

    Mono<Vote> findByAssociateDocumentNumberAndSectionUuid(final String associateUuid, final String sectionUuid);

    Mono<Integer> countVotesBySectionUuid(final String sectionUuid);

}
