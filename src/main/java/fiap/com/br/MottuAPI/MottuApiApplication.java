package fiap.com.br.MottuAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class MottuApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MottuApiApplication.class, args);
	}

}
