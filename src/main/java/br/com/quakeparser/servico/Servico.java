package br.com.quakeparser.servico;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.quakeparser.dominio.Jogo;
import br.com.quakeparser.interpretador.LeitorLog;
import br.com.quakeparser.interpretador.ParserQuake;

@Service
public class Servico {

    private final List<Jogo> jogos;

    public Servico() throws IOException {
        LeitorLog leitor = new LeitorLog();
        ParserQuake parser = new ParserQuake();

        List<String> linhas = leitor.ler(Path.of("games.log"));
        jogos = parser.processar(linhas);
    }

    public Optional<Jogo> buscarJogo(int numero) {
        return ProcuraJogo.buscarPorNumero(jogos, numero);
    }
}