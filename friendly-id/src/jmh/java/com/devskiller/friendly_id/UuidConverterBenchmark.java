package com.devskiller.friendly_id;

import java.math.BigInteger;
import java.util.UUID;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.OperationsPerInvocation;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@State(Scope.Benchmark)
@Warmup(iterations = 3)
@Measurement(iterations = 10)
@Fork(2)
public class UuidConverterBenchmark {

	static final int SIZE = 1_000_000;

	@Param({"STANDARD", "LEGACY"})
	FriendlyIdEncoding encoding;

	UUID[] uuids;
	BigInteger[] ids;

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder()
				.include(UuidConverterBenchmark.class.getSimpleName())
				.build();
		new Runner(opt).run();
	}

	@Setup
	public void setup() {
		uuids = new UUID[SIZE];
		ids = new BigInteger[SIZE];
		for (int i = 0; i < SIZE; i++) {
			uuids[i] = UUID.randomUUID();
			// each encoding maps UUIDs onto a different value range, so decode real encoder output
			ids[i] = UuidConverter.toBigInteger(uuids[i], encoding);
		}
	}

	@Benchmark
	@OperationsPerInvocation(SIZE)
	public void convertToBigInteger(Blackhole blackhole) {
		for (int i = 0; i < SIZE; i++) {
			blackhole.consume(UuidConverter.toBigInteger(uuids[i], encoding));
		}
	}

	@Benchmark
	@OperationsPerInvocation(SIZE)
	public void convertFromBigInteger(Blackhole blackhole) {
		for (int i = 0; i < SIZE; i++) {
			blackhole.consume(UuidConverter.toUuid(ids[i], encoding));
		}
	}
}
