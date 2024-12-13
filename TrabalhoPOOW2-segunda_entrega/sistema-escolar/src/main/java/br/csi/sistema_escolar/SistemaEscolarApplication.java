package br.csi.sistema_escolar;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		info = @Info(
				title = "Sistema Escolar API",
				version = "1.0",
				description = "Documentação da API do Sistema Escolar",
				contact = @Contact(name = "Suporte", email = "suporte@exemplo.com")
		)
)
@SpringBootApplication
public class SistemaEscolarApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaEscolarApplication.class, args);
	}
}
