package br.com.quakeparser.controlador;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import br.com.quakeparser.dominio.Jogo;
import br.com.quakeparser.servico.Servico;

@WebMvcTest(Controlador.class)
class ControladorTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private Servico servico;

    @Test
    void deveRetornarJogoEncontrado() throws Exception {
        Jogo jogo = new Jogo(1);
        jogo.registrarMorte("Alfa", "Bravo");
        when(servico.buscarJogo(1)).thenReturn(Optional.of(jogo));

        mockMvc.perform(get("/jogos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("game_1"))
                .andExpect(jsonPath("$.totalKills").value(1))
                .andExpect(jsonPath("$.jogadores.Alfa.kills").value(1));
    }

    @Test
    void deveRetornar404QuandoJogoNaoForEncontrado() throws Exception {
        when(servico.buscarJogo(99)).thenReturn(Optional.empty());

        mockMvc.perform(get("/jogos/99"))
                .andExpect(status().isNotFound());
    }
}