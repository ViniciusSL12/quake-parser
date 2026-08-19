# Quake Parser

Parser em Java para o arquivo de log do Quake 3 Arena. O projeto implementa as
três tarefas do exercício: leitura e agrupamento do `games.log`, relatório com
ranking geral e API REST para consulta de uma partida.

## Requisitos

- JDK 17 ou superior;
- Maven 3.8 ou superior;
- `games.log` na raiz do projeto.

Confira as versões instaladas:

```bash
java -version
mvn -version
```

## Executar depois de clonar

Abra no VS Code a raiz do projeto, a pasta que contém `pom.xml` e `games.log`.
No terminal, execute os comandos a partir dessa pasta:

```bash
mvn clean package
java -cp target/classes br.com.quakeparser.Main
```

O projeto exige Java 17 ou superior. Se o Maven informar que está usando Java
8, configure `JAVA_HOME` para um JDK 17+ antes de executar os comandos.

## Testes automatizados

Execute todos os testes com:

```bash
mvn clean test
```

Os testes cobrem o parser, separação de partidas, contagem de mortes, regra do
`<world>`, relatório, ranking e API. O resultado esperado é de 8 testes passando.

## Executar a API

Gere o jar executável:

```bash
mvn clean package
```

Inicie a API Spring Boot:

```bash
java -jar target/quake-parser-1.0.0.jar
```

O servidor inicia na porta `8080`. Em outro terminal, consulte uma partida:

```bash
curl http://localhost:8080/jogos/1
```

Uma partida existente retorna `200` com os dados do jogo. Para validar o caso
de partida inexistente:

```bash
curl -i http://localhost:8080/jogos/999
```

Nesse caso, a API retorna `404`. O endpoint implementado é:

```text
GET /jogos/{id}
```

O ID `1` corresponde a `game_1`. A aplicação lê o arquivo `games.log` relativo
ao diretório em que foi iniciada.

## Executar relatório e ranking

O relatório em modo interativo é executado pela classe `Main`. Pela IDE, execute
`br.com.quakeparser.Main`; ou, depois de compilar, use:

```bash
java -cp target/classes br.com.quakeparser.Main
```

No menu:

1. escolha `1` para imprimir o relatório de cada jogo e o ranking geral;
2. escolha `2` para consultar uma partida pela API;
3. escolha `3` para encerrar.

Para usar a opção `2`, mantenha a API em execução em outro terminal.

## Regras implementadas

- Cada `InitGame:` inicia uma nova partida (`game_1`, `game_2`, ...).
- Cada linha `Kill:` registra uma morte na partida atual.
- `total_kills` inclui todas as mortes, inclusive as causadas por `<world>`.
- Em uma morte causada por `<world>`, a vítima perde um kill.
- `<world>` não aparece em `players` nem no mapa de kills.
- Em uma morte normal, o atacante recebe um kill.
- O ranking geral soma os kills do jogador em todas as partidas.

## Organização do código

- `dominio`: entidades `Jogo` e `Jogador`;
- `interpretador`: leitura do arquivo e parsing das linhas;
- `relatorio`: relatório por jogo e ranking geral;
- `servico`: carregamento e busca de partidas;
- `controlador`: endpoint REST `/jogos/{id}`;
- `cliente`: cliente HTTP usado pelo menu;
- `Main`: menu do relatório e consulta;
- `QuakeParserApplication`: inicialização da API Spring Boot.

## Solução e boas práticas

O parser recebe as linhas do log e não depende diretamente da camada HTTP. As
responsabilidades estão separadas por domínio, interpretação, serviço,
relatório e controlador, facilitando testes unitários e manutenção. O projeto
usa Maven, JUnit 5, Spring Boot e commits pequenos por funcionalidade.
