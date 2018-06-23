package org.tyrant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.core.JsonProcessingException;

@EnableTransactionManagement
@EnableScheduling
@EnableAsync 
@SpringBootApplication
public class Application implements WebMvcConfigurer {
	
	public static void main(String... args) throws JsonProcessingException {
		SpringApplication.run(Application.class, args);
	}

}
