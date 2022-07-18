package br.com.projetos.apiplanetas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport
public class ApiplanetasApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiplanetasApplication.class, args);
	}

}
