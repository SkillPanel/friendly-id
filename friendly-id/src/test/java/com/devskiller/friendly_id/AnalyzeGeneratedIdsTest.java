package com.devskiller.friendly_id;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class AnalyzeGeneratedIdsTest {

	private List<String> ids = new ArrayList<>();

	@Test
	void analyzeGeneratedValueStatistics() {
		for (int i = 0; i < 100_000; i++) {
			this.ids.add(Base62.encode(UuidConverter.toBigInteger(UUID.randomUUID())));
		}
		IntSummaryStatistics stats = ids.stream().map(String::length).mapToInt(Integer::intValue).summaryStatistics();

		System.out.println("\nResults:");
		System.out.println("Min: " + stats.getMin());
		System.out.println("Max: " + stats.getMax());
		System.out.println("Avg: " + stats.getAverage());
		System.out.println("Count: " + stats.getCount());
		System.out.println("Sample: \n" + ids.stream().limit(100).collect(Collectors.joining("\n")));

		assertThat(stats.getMax()).isEqualTo(22);
		assertThat(stats.getMin()).isGreaterThanOrEqualTo(17);
		assertThat(stats.getAverage()).isLessThanOrEqualTo(22);
	}

}