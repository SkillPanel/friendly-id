package com.devskiller.friendly_id.sample.spring3;

import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@GetMapping("/bars/{id}")
	Bar getBar(@PathVariable UUID id) {
		Bar bar = new Bar();
		bar.setId(id);
		return bar;
	}

}
