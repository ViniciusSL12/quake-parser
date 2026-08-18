package br.com.quakeparser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserQuake {

    private static final Pattern PADRAO_KILL = Pattern.compile(
        "^\\s*\\S+\\s+Kill:\\s+\\d+\\s+\\d+\\s+\\d+:\\s+(.+?) killed (.+?) by .+$"
    );

    public List<Jogo> processar(List<String> linhas) {
        List<Jogo> jogos = new ArrayList<>();
        Jogo jogoAtual = null;
        int numeroJogo = 0;

        for (String linha : linhas) {

            if (linha.contains("InitGame:")) {
                numeroJogo++;
                jogoAtual = new Jogo(numeroJogo);
                jogos.add(jogoAtual);
                continue;
            }

            if (jogoAtual == null) {
                continue;
            }

            Matcher matcher = PADRAO_KILL.matcher(linha);

            if (matcher.matches()) {
                String atacante = matcher.group(1).trim();
                String vitima = matcher.group(2).trim();

                jogoAtual.registrarMorte(atacante, vitima);
            }
        }

        return jogos;
    }
}