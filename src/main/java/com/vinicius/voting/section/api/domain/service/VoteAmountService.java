package com.vinicius.voting.section.api.domain.service;

import com.vinicius.voting.section.api.controller.response.SectionResponse;
import reactor.core.publisher.Mono;

public interface VoteAmountService {
    Mono<SectionResponse> totalVotes(final String sectionUuid);
}
