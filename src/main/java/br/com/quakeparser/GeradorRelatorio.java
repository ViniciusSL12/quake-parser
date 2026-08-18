package br.com.quakeparser;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GeradorRelatorio {

    public static void imprimirRelatorioPorJogo(List<Jogo> jogos) {
        for (Jogo jogo : jogos) {
            System.out.println(jogo);
        }
    }

    public static void imprimirRankingGeral(List<Jogo> jogos) {
        Map<String, Integer> ranking = new LinkedHashMap<>();

        for (Jogo jogo : jogos) {
            jogo.getJogadores().forEach((nome, jogador) ->
                    ranking.merge(nome, jogador.getKills(), Integer::sum));
        }

        System.out.println();
        System.out.println("ranking_geral:");

        ranking.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEach(entry -> System.out.println("  " + entry.getKey() + ": " + entry.getValue()));
    }
}