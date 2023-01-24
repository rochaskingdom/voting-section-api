## Objetivo

No cooperativismo, cada associado possui um voto e as decisões são tomadas em assembleias, por votação. A partir disso, você precisa criar uma solução back-end para gerenciar essas sessões de votação. Essa solução deve ser executada na nuvem e promover as seguintes funcionalidades através de uma API REST:
- Cadastrar uma nova pauta;
- Abrir uma sessão de votação em uma pauta (a sessão de votação deve ficar aberta por um tempo determinado na chamada de abertura ou 1 minuto por default);
- Receber votos dos associados em pautas (os votos são apenas 'Sim'/'Não'. Cada associado é identificado por um id único e pode votar apenas uma vez por pauta);
- Contabilizar os votos e dar o resultado da votação na pauta.

Para fins de exercício, a segurança das interfaces pode ser abstraída e qualquer chamada para as interfaces pode ser considerada como autorizada. A escolha da linguagem, frameworks e bibliotecas é livre (desde que não infrinja direitos de uso).

É importante que as pautas e os votos sejam persistidos e que não sejam perdidos com o restart da aplicação.

## Tecnologias Utilizadas

- Java 17
- Gradle
- Spring Boot
- Spring Reactive Web (WebFlux)
- Spring Data Reactive MongoDB
- Spring REST Docs (Documentação da API)
- MongoDB
- JUnit
- Mockito
- Docker

## Funcionalidades
[Click aqui para acessarr a Documentacao da API](index.pdf)

Host: http://localhost:8080

Collection Postman: 
[Collection](collection/voting-section-api.postman_collection.json) e
[Environment](collection/dev.postman_environment.json)

### Associado
- POST: /voting-section/v1/associates - Cria um novo associado.
- GET: /voting-section/v1/associates/{documentNumber} - Busca associado pelo número de documento.

### Pauta
- POST  - /voting-section/v1/agendas - Cria uma nova pauta.
- GET  - /voting-section/v1/agendas - Lista todas as pautas.
- GET  - voting-section/v1/agendas/{agendaUuid} - Busca pauta pelo identificador uuid.

### Sessão
- POST - /voting-section/v1/sections - Cria uma nova sessão
- GET - /voting-section/v1/sections/{sectionuuid} - Busca sessão pelo identificador uuid.

### Voto
- POST - /voting-section/v1/votes/{sectionUuid} - Realiza o voto em uma sessão.

### Resultado de Votos
- GET - /voting-section/v1/votes/result/{sectionUuid} - Realiza a contagem de votos de uma determinada sessão.

## Instruções para Rodar a Aplicação
Gradle build
```shell
./gradlew clean build
```

Docker compose build
```shell
docker-compose build
```

Docker compose up
```shell
docker-compose up
```

