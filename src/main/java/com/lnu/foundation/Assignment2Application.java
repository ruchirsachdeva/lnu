package com.lnu.foundation;

import com.lnu.foundation.config.RestConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Assignment2Application {

	public static void main(String[] args) {
		 	new SpringApplicationBuilder(Assignment2Application.class, RestConfig.class).run(args);
	}

}
