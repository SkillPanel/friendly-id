package com.devskiller.friendly_id.sample.hateos;

import com.devskiller.friendly_id.spring.EnableFriendlyId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@EnableFriendlyId // Required for UUID <-> FriendlyID conversion in path variables
@Import({FooResourceAssembler.class, BarResourceAssembler.class}) // Import assemblers for WebMvcTest
class FooControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Test
	void shouldGet() throws Exception {
		mockMvc.perform(get("/foos/{id}", "cafe"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/hal+json"))
				.andExpect(jsonPath("$.uuid", is("cafe")))
				.andExpect(jsonPath("$._links.self.href", is("http://localhost/foos/cafe")));
	}

	@Test
	void shouldCreate() throws Exception {
		mockMvc.perform(post("/foos")
				.content("{\"uuid\":\"newFoo\",\"name\":\"Very New Foo\"}")
				.contentType("application/hal+json"))
				.andDo(print())
				.andExpect(header().string("Location", "http://localhost/foos/newFoo"))
				.andExpect(status().isCreated());
	}

	@Test
	void update() throws Exception {
		mockMvc.perform(put("/foos/{id}", "foo")
				.content("{\"uuid\":\"foo\",\"name\":\"Sample Foo\"}")
				.contentType("application/hal+json"))
				.andDo(print())
				.andExpect(status().isOk());
	}

}
