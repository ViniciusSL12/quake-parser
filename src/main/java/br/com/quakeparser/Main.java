package br.com.quakeparser;

import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Path caminho = Path.of("games.log");

        try {
            LeitorLog leitor = new LeitorLog();
            ParserQuake parser = new ParserQuake();

            List<Jogo> jogos = parser.processar(leitor.ler(caminho));

            jogos.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }
}
