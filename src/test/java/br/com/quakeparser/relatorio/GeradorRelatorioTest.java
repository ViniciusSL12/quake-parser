package br.com.quakeparser.relatorio;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.quakeparser.dominio.Jogo;

class GeradorRelatorioTest {

    private final ByteArrayOutputStream saida = new ByteArrayOutputStream();
    private PrintStream saidaOriginal;

    @BeforeEach
    void redirecionarSaida() {
        saidaOriginal = System.out;
        System.setOut(new PrintStream(saida, true, StandardCharsets.UTF_8));
    }

    @AfterEach
    void restaurarSaida() {
        System.setOut(saidaOriginal);
    }

    @Test
    void deveImprimirRelatorioEOrdenarRanking() {
        Jogo jogo = new Jogo(1);
        jogo.registrarMorte("Alfa", "Bravo");
        jogo.registrarMorte("Alfa", "Bravo");
        jogo.registrarMorte("Bravo", "Alfa");

        GeradorRelatorio.imprimirRelatorioPorJogo(List.of(jogo));
        GeradorRelatorio.imprimirRankingGeral(List.of(jogo));

        String resultado = saida.toString(StandardCharsets.UTF_8);

        assertTrue(resultado.contains("game_1:"));
        assertTrue(resultado.contains("total_kills: 3"));
        assertTrue(resultado.contains("ranking_geral:"));

        String ranking = resultado.substring(resultado.indexOf("ranking_geral:"));
        assertTrue(ranking.indexOf("Alfa: 2") < ranking.indexOf("Bravo: 1"));
    }
}