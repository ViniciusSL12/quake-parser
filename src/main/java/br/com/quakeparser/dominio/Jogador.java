package br.com.quakeparser.dominio;

public class Jogador {

    private final String nome;

    private int kills;

    public Jogador(String nome) {
        this.nome = nome;
    }

    public void adicionarKill() {
        kills++;
    }

    public void perderKill() {
        kills--;
    }

    public String getNome() {
        return nome;
    }

    public int getKills() {
        return kills;
    }
}
