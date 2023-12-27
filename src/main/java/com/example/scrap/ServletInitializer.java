package com.example.scrap;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.util.Properties;

public class ServletInitializer extends SpringBootServletInitializer {


	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application
				.sources(ServletInitializer.class)
				.properties(getProperties());
	}

	static Properties getProperties() {

		Properties props = new Properties();

		props.put("spring.config.location", "file:////home/core/scrap/");

		return props;

	}

}
