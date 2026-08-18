package br.com.quakeparser;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParserQuakeTest {

    private final ParserQuake parser = new ParserQuake();

    @Test
    void deveCriarUmaPartida() {
        List<Jogo> jogos = parser.processar(List.of(
                "0:00 InitGame:",
                "0:10 Kill: 2 3 10: Alpha killed Bravo by MOD_RAILGUN"
        ));

        assertEquals(1, jogos.size());
        assertEquals("game_1", jogos.get(0).getNome());
    }

    @Test
    void deveContarTodasAsMortes() {
        Jogo jogo = parser.processar(List.of(
                "0:00 InitGame:",
                "0:10 Kill: 2 3 10: Alpha killed Bravo by MOD_RAILGUN",
                "0:20 Kill: 4 2 10: Charlie killed Alpha by MOD_ROCKET",
                "0:30 Kill: 1022 3 22: <world> killed Bravo by MOD_FALLING"
        )).get(0);

        assertEquals(3, jogo.getTotalKills());
    }

    @Test
    void deveAdicionarKillAoAtacante() {
        Jogo jogo = parser.processar(List.of(
                "0:00 InitGame:",
                "0:10 Kill: 2 3 10: Alpha killed Bravo by MOD_RAILGUN"
        )).get(0);

        assertEquals(1, jogo.getJogadores().get("Alpha").getKills());
    }

    @Test
    void worldDeveTirarUmKillDaVitima() {
        Jogo jogo = parser.processar(List.of(
                "0:00 InitGame:",
                "0:10 Kill: 1022 3 22: <world> killed Bravo by MOD_FALLING"
        )).get(0);

        assertEquals(-1, jogo.getJogadores().get("Bravo").getKills());
        assertFalse(jogo.getJogadores().containsKey("<world>"));
    }

    @Test
    void deveSepararAsPartidas() {
        List<Jogo> jogos = parser.processar(List.of(
                "0:00 InitGame:",
                "0:10 Kill: 2 3 10: Alpha killed Bravo by MOD_RAILGUN",
                "1:00 InitGame:",
                "1:10 Kill: 4 5 10: Charlie killed Delta by MOD_ROCKET"
        ));

        assertEquals(2, jogos.size());
        assertEquals(1, jogos.get(0).getTotalKills());
        assertEquals(1, jogos.get(1).getTotalKills());
    }
}
