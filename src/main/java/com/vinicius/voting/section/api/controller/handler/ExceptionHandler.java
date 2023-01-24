package com.vinicius.voting.section.api.controller.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import com.vinicius.voting.section.api.exception.VotingSectionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@Order(-2)
@RequiredArgsConstructor
public class ExceptionHandler implements WebExceptionHandler {

    private static final String USER_AGENT_HEADER = "User-Agent";
    private static final String LOG_MESSAGE_ERROR = "Ocorreu a seguinte exception na chamada [{}] " +
            "com user-agent [{}]: [{}] - Erro retornado: [{}]";

    private final ObjectMapper mapper;
    private final DataBufferFactory dataBufferFactory;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        String requestPath = exchange.getRequest().getPath().value();

        ExceptionParserInterface parser = votingSectionException -> {
            exchange.getResponse().setStatusCode(votingSectionException.getStatus());
            exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
            var body = Mono.just(dataBufferFactory.wrap(mapper.writeValueAsBytes(votingSectionException)));
            log.error(LOG_MESSAGE_ERROR, requestPath, exchange.getRequest().getHeaders().getFirst(USER_AGENT_HEADER),
                    ex.getClass().getSimpleName(), votingSectionException.toString()
            );
            return exchange.getResponse().writeWith(body);
        };

        try {
            if (ex instanceof VotingSectionException votingSectionEx) {
                return Mono.from(parser.parse(votingSectionEx));
            }

            if (Throwables.getRootCause(ex) instanceof VotingSectionException votingSectionEx) {
                return Mono.from(parser.parse(votingSectionEx));
            }

            if (ex instanceof WebExchangeBindException exchangeBindException) {
                Map<String, String> fields = new HashMap<>();
                for (FieldError fieldError : exchangeBindException.getBindingResult().getFieldErrors()) {
                    fields.put(fieldError.getField(), fieldError.getDefaultMessage());
                }

                VotingSectionException exception = new VotingSectionException(
                        HttpStatus.BAD_REQUEST, "Erro de validação", "Ocorreu um erro na validação", fields
                );
                return Mono.from(parser.parse(exception));
            }

            if (ex instanceof ResponseStatusException responseStatusException) {
                VotingSectionException exception = new VotingSectionException(
                        (HttpStatus) responseStatusException.getStatusCode(),
                        responseStatusException.getDetailMessageCode(),
                        responseStatusException.getReason()
                );
                return Mono.from(parser.parse(exception));
            }

            VotingSectionException internalException = new VotingSectionException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Ops", "Ocorreu um erro interno"
            );
            ex.printStackTrace();
            return Mono.from(parser.parse(internalException));
        } catch (JsonProcessingException e) {
            log.error("Não foi possível mapear a exceção na chamada [{}] - [{}]", requestPath, ex.toString());
            exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            return exchange.getResponse().setComplete();
        }
    }


}
