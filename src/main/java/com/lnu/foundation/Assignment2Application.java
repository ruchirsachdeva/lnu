package com.lnu.foundation;

import com.lnu.foundation.config.RestConfig;
import com.lnu.foundation.config.WebConfig;
import com.lnu.foundation.config.WebSecurityConfiguration;
import com.lnu.foundation.model.Car;
import com.lnu.foundation.repository.CarRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;

@SpringBootApplication
public class Assignment2Application {

	public static void main(String[] args) {
		 	new SpringApplicationBuilder(Assignment2Application.class, RestConfig.class, WebSecurityConfiguration.class, WebConfig.class).run(args);
		//SpringApplication.run(Assignment2Application.class, args);
	}

	@Bean
	ApplicationRunner init(CarRepository repository) {
		return args -> {
			Stream.of("Ferrari", "Jaguar", "Porsche", "Lamborghini", "Bugatti",
					"AMC Gremlin", "Triumph Stag", "Ford Pinto", "Yugo GV").forEach(name -> {
				Car car = new Car();
				car.setName(name);
				repository.save(car);
			});
			repository.findAll().forEach(System.out::println);
		};
	}
}
