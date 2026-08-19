package br.com.quakeparser.cliente;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ClienteApi {

    private final HttpClient cliente;

    public ClienteApi() {
        this.cliente = HttpClient.newHttpClient();
    }

    public String buscarJogo(int id) throws IOException, InterruptedException {
        HttpRequest requisicao = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/jogos/" + id))
                .GET()
                .build();

        HttpResponse<String> resposta = cliente.send(
                requisicao,
                HttpResponse.BodyHandlers.ofString()
        );

        if (resposta.statusCode() == 200) {
            return resposta.body();
        }

        if (resposta.statusCode() == 404) {
            return null;
        }

        throw new IOException(
                "Erro ao consultar a API. Código: " + resposta.statusCode()
        );
    }
}