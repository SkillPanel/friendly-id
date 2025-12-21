package com.devskiller.friendly_id.sample.customized;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.devskiller.friendly_id.FriendlyId;
import com.devskiller.friendly_id.spring.EnableFriendlyId;

import static com.devskiller.friendly_id.FriendlyId.toFriendlyId;
import static com.devskiller.friendly_id.FriendlyId.toUuid;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ItemController.class)
@EnableFriendlyId
class ApplicationTest {

	@Autowired
	MockMvc mockMvc;

	@MockitoBean
	ItemService itemService;

	@Test
	void shouldSerializeAllIdFormats() throws Exception {
		// given
		UUID uuid = UUID.randomUUID();
		String friendlyId = toFriendlyId(uuid);
		var item = new Item(uuid, uuid, uuid, com.devskiller.friendly_id.type.FriendlyId.of(uuid));
		given(itemService.find(uuid)).willReturn(item);

		// expect
		mockMvc.perform(get("/items/{id}", friendlyId)
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id", is(friendlyId)))
				.andExpect(jsonPath("$.rawId", is(uuid.toString())))
				.andExpect(jsonPath("$.friendlyUuid", is(friendlyId)))
				.andExpect(jsonPath("$.friendlyId", is(friendlyId)));
	}

	@Test
	void shouldDeserializeAndCreate() throws Exception {
		// given
		UUID uuid = UUID.randomUUID();
		String friendlyId = toFriendlyId(uuid);
		String json = """
				{"id": "%s", "rawId": "%s", "friendlyUuid": "%s", "friendlyId": "%s"}
				""".formatted(friendlyId, uuid, friendlyId, friendlyId);

		var item = new Item(uuid, uuid, uuid, com.devskiller.friendly_id.type.FriendlyId.of(uuid));
		given(itemService.create(any(Item.class))).willReturn(item);

		// when
		mockMvc.perform(post("/items")
						.content(json)
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk());

		// then
		then(itemService).should().create(any(Item.class));
	}

	@Test
	void shouldDeserializeAndUpdate() throws Exception {
		// given
		UUID uuid = UUID.randomUUID();
		String friendlyId = toFriendlyId(uuid);
		String json = """
				{"id": "%s"}
				""".formatted(friendlyId);

		// when
		mockMvc.perform(put("/items/{id}", friendlyId)
						.content(json)
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk());

		// then
		then(itemService).should().update(eq(uuid), any(Item.class));
	}

	@Test
	void shouldWorkWithPseudoUuid() throws Exception {
		// given
		UUID itemId = toUuid("itemId");
		String friendlyId = toFriendlyId(itemId);
		var item = new Item(itemId, itemId, itemId, com.devskiller.friendly_id.type.FriendlyId.of(itemId));
		given(itemService.find(itemId)).willReturn(item);

		// expect
		mockMvc.perform(get("/items/{id}", "itemId")
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id", is(friendlyId)))
				.andExpect(jsonPath("$.rawId", is(itemId.toString())));
	}
}
