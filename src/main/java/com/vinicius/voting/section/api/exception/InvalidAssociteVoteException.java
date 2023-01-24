package com.vinicius.voting.section.api.exception;

import org.springframework.http.HttpStatus;

public class InvalidAssociteVoteException extends VotingSectionException {
    public InvalidAssociteVoteException() {
        super(HttpStatus.BAD_REQUEST, "invalid-associate-vote", "Não é possível votar novamente nesta mesma sessão.");
    }
}
