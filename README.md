# Quake Parser

Parser em Java para o arquivo de log do Quake 3 Arena.

## Requisitos

- Java 17 ou superior
- Maven 3.8 ou superior

## Como executar

O arquivo `games.log` está na raiz do projeto.

Para executar os testes:

```bash
mvn clean test
```

Para gerar o `.jar`:

```bash
mvn clean package
```

Depois:

```bash
java -jar target/quake-parser-1.0.0.jar
```

Também é possível executar a classe `Main` diretamente pela IDE.

## Solução

O programa lê o arquivo linha por linha e identifica o início de cada partida através de `InitGame:`.

As linhas que possuem `Kill:` são interpretadas pelo parser. A partir delas são identificados o jogador que matou e o jogador que morreu.

Em uma morte normal, o atacante recebe um kill.

Quando o atacante é `<world>`, ele não é considerado jogador e a vítima perde um kill.

O `total_kills` conta todas as mortes registradas, incluindo as causadas pelo `<world>`.

A solução foi dividida em algumas classes simples para separar as responsabilidades:

- `Main`: inicia a aplicação.
- `LeitorLog`: lê o arquivo.
- `ParserQuake`: interpreta as linhas.
- `Jogo`: guarda os dados de uma partida.
- `Jogador`: guarda o nome e a quantidade de kills.

## Testes

Foram adicionados testes para as principais regras da Task 1:

- criação de partidas;
- contagem de mortes;
- kills dos jogadores;
- penalidade causada pelo `<world>`;
- `<world>` não aparecer como jogador;
- separação de partidas.
