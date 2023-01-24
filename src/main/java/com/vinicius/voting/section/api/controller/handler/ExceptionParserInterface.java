package com.vinicius.voting.section.api.controller.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vinicius.voting.section.api.exception.VotingSectionException;
import org.reactivestreams.Publisher;

public interface ExceptionParserInterface {
    Publisher<Void> parse(VotingSectionException ex) throws JsonProcessingException;
}