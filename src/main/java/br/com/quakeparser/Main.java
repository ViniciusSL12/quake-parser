package br.com.quakeparser;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

import br.com.quakeparser.cliente.ClienteApi;
import br.com.quakeparser.dominio.Jogo;
import br.com.quakeparser.interpretador.LeitorLog;
import br.com.quakeparser.interpretador.ParserQuake;
import br.com.quakeparser.relatorio.GeradorRelatorio;

public class Main {

    public static void main(String[] args) {
        Path caminho = args.length > 0 ? Path.of(args[0]) : Path.of("games.log");

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
        boolean executando = true;

        System.out.println("bem vindo ao Log do quake 3 arena!");

        while (executando) {
            System.out.println();
            System.out.println("escolha uma opção abaixo:");
            System.out.println("1 - ver relatório dos jogos");
            System.out.println("2 - procurar um game específico");
            System.out.println("3 - sair");
            System.out.print("> ");

            int opcao = lerInteiro(scanner);

            if (opcao == 1) {
                GeradorRelatorio.imprimirRelatorioPorJogo(jogos);
                GeradorRelatorio.imprimirRankingGeral(jogos);

            } else if (opcao == 2) {
                procurarJogo(jogos, scanner);

            } else if (opcao == 3) {
                executando = false;
                System.out.println("encerrando aplicação...");

            } else {
                System.out.println("opção inválida.");
            }
        }

        scanner.close();
    }

    private static void procurarJogo(List<Jogo> jogos, Scanner scanner) {
        System.out.println("você possui " + jogos.size()
                + " games. qual deseja ver? digite um número de 1 a " + jogos.size());

        int numero = lerInteiro(scanner);

        try {
            ClienteApi clienteApi = new ClienteApi();

            String resposta = clienteApi.buscarJogo(numero);

            if (resposta != null) {
                System.out.println();
                System.out.println("resultado da consulta:");
                System.out.println(resposta);
            } else {
                System.out.println("jogo não encontrado: game_" + numero);
            }

        } catch (IOException | InterruptedException e) {
            System.out.println("erro ao consultar a API: " + e.getMessage());
        }
    }

    private static int lerInteiro(Scanner scanner) {
        if (scanner.hasNextInt()) {
            return scanner.nextInt();
        }
        scanner.next();
        return -1;
    }
}