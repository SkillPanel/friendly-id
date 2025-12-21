package com.devskiller.friendly_id.sample.contracts;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.context.WebApplicationContext;

import com.devskiller.friendly_id.spring.EnableFriendlyId;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@WebMvcTest
@EnableFriendlyId
@WithMockUser(username = "admin", roles = "ADMIN")
@Import(SecurityConfig.class)
public abstract class AuthenticatedContractBase {

	@Autowired
	private WebApplicationContext context;

	@BeforeEach
	public void setUp() {
		RestAssuredMockMvc.webAppContextSetup(context, springSecurity());
	}

}
