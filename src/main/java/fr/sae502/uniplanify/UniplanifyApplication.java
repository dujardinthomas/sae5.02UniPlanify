package fr.sae502.uniplanify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan // To scan Servlets, ServletFilters and ServletListeners
public class UniplanifyApplication {

	public static void main(String[] args) {
		SpringApplication.run(UniplanifyApplication.class, args);
	}

}
