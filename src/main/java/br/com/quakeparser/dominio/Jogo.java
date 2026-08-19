package br.com.quakeparser.dominio;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Jogo {

    private final String nome;
    private int totalKills;

    
    private final Map<String, Jogador> jogadores = new LinkedHashMap<>();

    public Jogo(int numero) {
        nome = "game_" + numero;
    }

    public void registrarMorte(String atacante, String vitima) {
        totalKills++;

        adicionarJogador(vitima);

        
        if ("<world>".equals(atacante)) {
            jogadores.get(vitima).perderKill();
            return;
        }

        adicionarJogador(atacante);
        jogadores.get(atacante).adicionarKill();
    }

    public void adicionarJogador(String nome) {
        if (!"<world>".equals(nome)) {
            jogadores.putIfAbsent(nome, new Jogador(nome));
        }
    }

    public String getNome() {
        return nome;
    }

    public int getTotalKills() {
        return totalKills;
    }

    public Map<String, Jogador> getJogadores() {
        return Collections.unmodifiableMap(jogadores);
    }

    @Override
    public String toString() {
        String nomes = jogadores.keySet()
                .stream()
                .collect(Collectors.joining(", "));

        String kills = jogadores.values()
                .stream()
                .map(jogador -> "  " + jogador.getNome() + ": " + jogador.getKills())
                .collect(Collectors.joining("\n"));

        return nome + ":\n"
                + "total_kills: " + totalKills + "\n"
                + "players: [" + nomes + "]\n"
                + "kills:\n" + kills + "\n";
    }
}
