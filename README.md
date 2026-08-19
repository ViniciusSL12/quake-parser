# Quake Parser

Parser em Java para o arquivo de log do Quake 3 Arena. O projeto lê e agrupa o
`games.log`, gera um relatório com ranking geral e oferece uma API REST para
consulta de partidas.

## Requisitos

- JDK 21 ou superior;
- Maven 3.9 ou superior;
- `games.log` na raiz do projeto.

Abra no VS Code a pasta `quake-parser`, que contém `pom.xml` e `games.log`.
Execute os comandos a partir dessa pasta.

## Executar a API

No primeiro terminal, gere o jar executável:

```powershell
mvn clean package
```

Inicie a API Spring Boot:

```powershell
java -jar target/quake-parser-1.0.0.jar
```

O servidor inicia na porta `8080`. Em outro terminal, consulte uma partida:

```powershell
curl http://localhost:8080/jogos/1
```

O endpoint implementado é `GET /jogos/{id}`. O ID `1` corresponde a `game_1`.
Para consultar uma partida inexistente:

```powershell
curl -i http://localhost:8080/jogos/999
```

Nesse caso, a API retorna `404`. Para encerrar a API, pressione `Ctrl+C` no
terminal em que ela está rodando.

## Executar o menu

Abra um terminal também com a pasta `quake-parser` aberta e compile o projeto:

```powershell
mvn compile
java -cp target/classes br.com.quakeparser.Main
```

No menu:

1. escolha `1` para ver o relatório dos jogos e o ranking geral;
2. escolha `2` para consultar uma partida pela API (a API precisa estar
	rodando em outro terminal);
3. escolha `3` para encerrar.

A opção `1` funciona sem iniciar a API. Se a API já estiver rodando, use
`mvn compile` em vez de `mvn clean compile`, pois o `clean` tenta apagar o jar
que está sendo usado pelo processo da API.

## Testes

Execute todos os testes com:

```powershell
mvn clean test
```

Antes de executar esse comando, pare a API com `Ctrl+C` caso ela esteja
rodando. O Maven precisa substituir os arquivos dentro de `target`.

Os testes cobrem o parser, a separação de partidas, a contagem de mortes, a
regra do `<world>`, o relatório, o ranking e a API.

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
relatório e controlador, facilitando os testes unitários e a manutenção. O
projeto usa Maven, JUnit 5 e Spring Boot.
