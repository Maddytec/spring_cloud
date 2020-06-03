package br.com.maddytec.efetivacao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@SpringBootApplication
@EnableCircuitBreaker
public class EfetivacaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(EfetivacaoApplication.class, args);
	}

}
