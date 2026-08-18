package br.com.quakeparser;

import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GeradorRelatorio {

    public static void main(String[] args) {
        Path caminho = Path.of(args.length > 0 ? args[0] : "games.log");

        try {
            LeitorLog leitor = new LeitorLog();
            ParserQuake parser = new ParserQuake();

            List<Jogo> jogos = parser.processar(leitor.ler(caminho));

            imprimirRelatorioPorJogo(jogos);
            imprimirRankingGeral(jogos);
        } catch (Exception e) {
            System.out.println("Erro ao gerar relatório: " + e.getMessage());
        }
    }

    private static void imprimirRelatorioPorJogo(List<Jogo> jogos) {
    for (Jogo jogo : jogos) {
        Map<String, Integer> kills = new LinkedHashMap<>();
        jogo.getJogadores().forEach((nome, jogador) -> kills.put(nome, jogador.getKills()));

        System.out.println("Relatorio do: ");
        System.out.println(jogo.getNome());
        System.out.println(" Tiveram: " + jogo.getTotalKills() + " mortes");
        System.out.println(" e os jogadores tiveram " + kills + " em abates");
    }
}

    private static void imprimirRankingGeral(List<Jogo> jogos) {
        Map<String, Integer> ranking = new LinkedHashMap<>();

        for (Jogo jogo : jogos) {
            jogo.getJogadores().forEach((nome, jogador) ->
                    ranking.merge(nome, jogador.getKills(), Integer::sum));
        }

        System.out.println();
        System.out.println("Ranking_Geral:");

        ranking.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEach(entry -> System.out.println("  " + entry.getKey() + ": " + entry.getValue()));
    }
}
