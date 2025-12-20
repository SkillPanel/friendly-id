package com.devskiller.friendly_id.sample.simple;

import com.devskiller.friendly_id.FriendlyId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void shouldSerialize() throws Exception {
		// given
		UUID uuid = UUID.randomUUID();
		String friendlyId = FriendlyId.toFriendlyId(uuid);

		// when/then
		mockMvc.perform(get("/bars/{id}", friendlyId)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id", is(friendlyId)));
	}
}
