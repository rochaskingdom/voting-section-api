package com.vinicius.voting.section.api.domain.service.impl;

import com.vinicius.voting.section.api.domain.model.Associate;
import com.vinicius.voting.section.api.domain.repository.AssociateRepository;
import com.vinicius.voting.section.api.domain.service.AssociateService;
import com.vinicius.voting.section.api.exception.AssociateNotFoundException;
import com.vinicius.voting.section.api.exception.InvalidAssociateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class AssociateServiceImpl implements AssociateService {

    private final AssociateRepository associateRepository;

    @Override
    public Mono<Associate> save(final String documentNumber) {
        return associateRepository.findByDocumentNumber(documentNumber)
                .flatMap(this::validateAssociate)
                .then(Mono.defer(() -> saveAssociate(documentNumber)));
    }

    private Mono<Object> validateAssociate(Associate associate) {
        if (isNull(associate)) {
            return Mono.empty();
        }
        return Mono.error(InvalidAssociateException::new);
    }

    private Mono<Associate> saveAssociate(String documentNumber) {
        return associateRepository.save(Associate.newAssociate(documentNumber))
                .doOnNext(associate -> log.info("Associado criado com sucesso. - [{}]", associate));
    }

    @Override
    public Mono<Associate> findByDocumentNumber(final String documentNumber) {
        return associateRepository.findByDocumentNumber(documentNumber)
                .doOnNext(associate -> log.info("Associado encontrado com sucesso. - [{}]", associate))
                .switchIfEmpty(Mono.error(AssociateNotFoundException::new));
    }
}
