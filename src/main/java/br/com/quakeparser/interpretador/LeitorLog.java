package br.com.quakeparser.interpretador;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class LeitorLog {

    public List<String> ler(Path caminho) throws IOException {
        return Files.readAllLines(caminho);
    }
}
