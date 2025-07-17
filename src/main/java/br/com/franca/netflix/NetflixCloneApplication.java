package br.com.franca.netflix;

import br.com.franca.netflix.config.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
@EnableJpaRepositories
@EnableTransactionManagement
public class NetflixCloneApplication {

	public static void main(String[] args) {
		SpringApplication.run(NetflixCloneApplication.class, args);
	}

}
