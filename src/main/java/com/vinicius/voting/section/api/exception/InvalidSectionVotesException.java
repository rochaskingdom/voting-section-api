package com.vinicius.voting.section.api.exception;

import org.springframework.http.HttpStatus;

public class InvalidSectionVotesException extends VotingSectionException {
    public InvalidSectionVotesException() {
        super(HttpStatus.BAD_REQUEST, "invalid-section-votes", "Sessão ainda não possui votos.");
    }
}
