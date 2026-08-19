package br.com.quakeparser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "br.com.quakeparser")
public class QuakeParserApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuakeParserApplication.class, args);
    }
}