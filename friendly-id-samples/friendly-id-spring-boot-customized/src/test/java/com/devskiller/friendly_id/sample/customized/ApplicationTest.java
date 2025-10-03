package com.devskiller.friendly_id.sample.customized;

import com.devskiller.friendly_id.FriendlyId;
import com.devskiller.friendly_id.spring.EnableFriendlyId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static com.devskiller.friendly_id.FriendlyId.toUuid;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BarController.class)
@EnableFriendlyId
class ApplicationTest {

	@Autowired
	MockMvc mockMvc;

	@MockitoBean
	FooService fooService;

	@Test
	void shouldSerialize() throws Exception {
		// given
		UUID uuid = UUID.randomUUID();
		given(fooService.find(uuid)).willReturn(new Bar(uuid, uuid));

		// expect
		mockMvc.perform(get("/bars/{id}", FriendlyId.toFriendlyId(uuid))
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.friendlyId", is(FriendlyId.toFriendlyId(uuid))))
				.andExpect(jsonPath("$.uuid", is(uuid.toString())));
	}

	@Test
	void shouldDeserialize() throws Exception {
		// given
		UUID uuid = UUID.randomUUID();
		String json = "{\"friendlyId\":\"" + FriendlyId.toFriendlyId(uuid) + "\",\"uuid\":\"" + uuid + "\"}";

		// when
		mockMvc.perform(put("/bars/{id}", FriendlyId.toFriendlyId(uuid))
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk());

		// then
		then(fooService)
				.should().update(eq(uuid), any(Bar.class));
	}

	@Test
	void sampleTestUsingPseudoUuid() throws Exception {
		// given
		UUID barId = toUuid("barId");
		given(fooService.find(barId)).willReturn(new Bar(barId, barId));

		// expect
		mockMvc.perform(get("/bars/{id}", "barId")
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.friendlyId", is("barId")))
				.andExpect(jsonPath("$.uuid", is(barId.toString())));

		System.out.println(barId);
	}
}
