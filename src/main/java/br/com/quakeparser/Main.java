package br.com.quakeparser;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Path caminho = Path.of("games.log");

        try {
            LeitorLog leitor = new LeitorLog();
            ParserQuake parser = new ParserQuake();

            List<Jogo> jogos = parser.processar(leitor.ler(caminho));

            executarMenu(jogos);
        } catch (Exception e) {
            System.out.println("erro ao ler o arquivo: " + e.getMessage());
        }
    }

    private static void executarMenu(List<Jogo> jogos) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("bem vindo ao Log do Quake 3 Arena!");
        System.out.println();
        System.out.println("escolha uma opção abaixo:");
        System.out.println("1 - ver relatório dos jogos");
        System.out.println("2 - procurar um game específico");
        System.out.print("> ");

        int opcao = lerInteiro(scanner);

        if (opcao == 1) {
            GeradorRelatorio.imprimirRelatorioPorJogo(jogos);
            GeradorRelatorio.imprimirRankingGeral(jogos);
        } else if (opcao == 2) {
            procurarJogo(jogos, scanner);
        } else {
            System.out.println("opção inválida.");
        }
    }

    private static void procurarJogo(List<Jogo> jogos, Scanner scanner) {
        System.out.println("você possui " + jogos.size()
                + " games. qual deseja ver? digite um número de 1 a " + jogos.size());

        int numero = lerInteiro(scanner);

        Optional<Jogo> jogo = ProcuraJogo.buscarPorNumero(jogos, numero);

        jogo.ifPresentOrElse(
                System.out::println,
                () -> System.out.println("jogo não encontrado: game_" + numero)
        );
    }

    private static int lerInteiro(Scanner scanner) {
        if (scanner.hasNextInt()) {
            return scanner.nextInt();
        }
        scanner.next();
        return -1;
    }
}