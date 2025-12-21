package com.devskiller.friendly_id.sample.simple;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.devskiller.friendly_id.FriendlyId;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void shouldAcceptFriendlyIdAsPathVariable() throws Exception {
		// given
		UUID uuid = UUID.randomUUID();
		String friendlyId = FriendlyId.toFriendlyId(uuid);
		String rawUuid = uuid.toString();

		// when/then
		mockMvc.perform(get("/items/{id}", friendlyId)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id", is(friendlyId)))
				.andExpect(jsonPath("$.rawId", is(rawUuid)))
				.andExpect(jsonPath("$.friendlyUuid", is(friendlyId)))
				.andExpect(jsonPath("$.friendlyId", is(friendlyId)));
	}

	@Test
	void shouldAcceptUuidAsPathVariable() throws Exception {
		// given
		UUID uuid = UUID.randomUUID();
		String friendlyId = FriendlyId.toFriendlyId(uuid);
		String rawUuid = uuid.toString();

		// when/then - using raw UUID as path variable
		mockMvc.perform(get("/items/{id}", rawUuid)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id", is(friendlyId)))
				.andExpect(jsonPath("$.rawId", is(rawUuid)))
				.andExpect(jsonPath("$.friendlyUuid", is(friendlyId)))
				.andExpect(jsonPath("$.friendlyId", is(friendlyId)));
	}

	@Test
	void shouldDeserializeAndSerialize() throws Exception {
		// given
		UUID uuid = UUID.randomUUID();
		String friendlyId = FriendlyId.toFriendlyId(uuid);
		String json = """
				{"id": "%s"}
				""".formatted(friendlyId);

		// when/then
		mockMvc.perform(post("/items")
						.contentType(MediaType.APPLICATION_JSON)
						.content(json)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id", is(friendlyId)));
	}

	@Test
	void shouldGenerateAllIdsWhenNotProvided() throws Exception {
		// given
		String json = "{}";

		// when/then
		mockMvc.perform(post("/items")
						.contentType(MediaType.APPLICATION_JSON)
						.content(json)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id", notNullValue()))
				.andExpect(jsonPath("$.rawId", notNullValue()))
				.andExpect(jsonPath("$.friendlyUuid", notNullValue()))
				.andExpect(jsonPath("$.friendlyId", notNullValue()));
	}

	@Test
	void shouldAcceptFriendlyIdAsRequestParam() throws Exception {
		// given
		UUID uuid = UUID.randomUUID();
		String friendlyId = FriendlyId.toFriendlyId(uuid);
		String rawUuid = uuid.toString();

		// when/then - using FriendlyId as ?id=xxx
		mockMvc.perform(get("/items")
						.param("id", friendlyId)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id", is(friendlyId)))
				.andExpect(jsonPath("$.rawId", is(rawUuid)));
	}

	@Test
	void shouldAcceptUuidAsRequestParam() throws Exception {
		// given
		UUID uuid = UUID.randomUUID();
		String friendlyId = FriendlyId.toFriendlyId(uuid);
		String rawUuid = uuid.toString();

		// when/then - using raw UUID as ?id=xxx
		mockMvc.perform(get("/items")
						.param("id", rawUuid)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id", is(friendlyId)))
				.andExpect(jsonPath("$.rawId", is(rawUuid)));
	}

	@Test
	void shouldAcceptFriendlyIdTypeAsRequestParam() throws Exception {
		// given
		UUID uuid = UUID.randomUUID();
		String friendlyId = FriendlyId.toFriendlyId(uuid);
		String rawUuid = uuid.toString();

		// when/then - using FriendlyId string with @RequestParam FriendlyId type
		mockMvc.perform(get("/items/by-friendly-id")
						.param("id", friendlyId)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id", is(friendlyId)))
				.andExpect(jsonPath("$.rawId", is(rawUuid)));
	}
}
