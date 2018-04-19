package intelligient.transportation.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"hibernate.config","intelligient.transportation.controllers","intelligient.transportation.dao"})
@EntityScan(basePackages = "intelligient.transportation.models")

public class mainC {

	public static void main(String[] args) {
		SpringApplication.run(mainC.class, args);

	}

}
