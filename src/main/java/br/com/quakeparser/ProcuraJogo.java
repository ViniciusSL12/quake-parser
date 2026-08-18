package br.com.quakeparser;

import java.util.List;
import java.util.Optional;

public class ProcuraJogo {

    public static Optional<Jogo> buscarPorNumero(List<Jogo> jogos, int numero) {
        return jogos.stream()
                .filter(jogo -> jogo.getNome().equals("game_" + numero))
                .findFirst();
    }
}