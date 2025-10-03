package com.devskiller.friendly_id.sample.contracts;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.web.context.WebApplicationContext;

import com.devskiller.friendly_id.spring.EnableFriendlyId;

@WebMvcTest
@EnableFriendlyId
@Import({FooResourceAssembler.class, BarResourceAssembler.class})
public abstract class ContractVerifierBase {

	@Autowired
	private WebApplicationContext context;

	@BeforeEach
	public void setUp() {
		RestAssuredMockMvc.webAppContextSetup(context);
	}

}
