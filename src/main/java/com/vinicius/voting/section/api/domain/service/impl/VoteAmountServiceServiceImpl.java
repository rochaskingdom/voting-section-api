package com.vinicius.voting.section.api.domain.service.impl;

import com.vinicius.voting.section.api.controller.response.SectionResponse;
import com.vinicius.voting.section.api.domain.model.Vote;
import com.vinicius.voting.section.api.domain.model.enums.VoteMessageEnum;
import com.vinicius.voting.section.api.domain.service.SectionService;
import com.vinicius.voting.section.api.domain.service.VoteAmountService;
import com.vinicius.voting.section.api.domain.service.VoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class VoteAmountServiceServiceImpl implements VoteAmountService {

    private final VoteService voteService;
    private final SectionService sectionService;

    @Override
    public Mono<SectionResponse> totalVotes(final String sectionUuid) {
        return voteService.findAllBySectionUuid(sectionUuid)
                .collectList()
                .flatMap(votes -> totalVotesAmount(sectionUuid, votes));
    }

    private Mono<SectionResponse> totalVotesAmount(final String sectionUuid, final List<Vote> votes) {
        final var totalVotes = calculateVotes(votes);
        return sectionService.findByUuid(sectionUuid)
                .map(section -> SectionResponse.of(section, totalVotes, votes));
    }

    private Map<String, Integer> calculateVotes(final List<Vote> votes) {
        final var totalVotesAmount = votes.size();
        final var totalVotesYES = countVotesWithMessageYES(votes);
        final var totalVotesNO = totalVotesAmount - totalVotesYES;
        return Map.of(
                VoteMessageEnum.YES.name(), totalVotesYES,
                VoteMessageEnum.NO.name(), totalVotesNO,
                "totalVotes", totalVotesAmount
        );
    }

    private int countVotesWithMessageYES(final List<Vote> votes) {
        return (int) votes.stream()
                .filter(vote -> VoteMessageEnum.YES.equals(vote.getVoteMessage()))
                .count();
    }
}
