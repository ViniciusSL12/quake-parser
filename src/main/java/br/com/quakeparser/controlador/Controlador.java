package br.com.quakeparser.controlador;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import br.com.quakeparser.servico.Servico;

@RestController
public class Controlador {

    private final Servico servico;

    public Controlador(Servico servico) {
        this.servico = servico;
    }

    @GetMapping("/jogos/{id}")
    public ResponseEntity<?> buscarJogo(@PathVariable int id) {

        return servico.buscarJogo(id)
                .map(jogo -> ResponseEntity.ok(jogo))
                .orElse(ResponseEntity.notFound().build());
    }
}