package io.vepo.cdi.runner.dummy;

import static java.util.stream.Collectors.toList;

import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class DummyDatabase implements Iterator<DummyEvent> {

	private static final Logger logger = LoggerFactory.getLogger(DummyDatabase.class);
	private List<DummyEvent> contents;

	@PostConstruct
	public void setup() {
		contents = IntStream.range(0, 1_000).mapToObj(DummyDatabase::randomEvent).collect(toList());
	}

	public DummyEvent next() {
		DummyEvent next = contents.stream().findFirst().orElseThrow(() -> new IllegalStateException());
		contents = contents.stream().filter(event -> !event.equals(next)).collect(toList());
		return next;
	}

	private static DummyEvent randomEvent(int id) {
		logger.info("Creating event... id={}", id);
		DummyEvent event = new DummyEvent();
		event.setId(id);
		event.setContent("Content: " + id);
		return event;
	}

	@Override
	public boolean hasNext() {
		return contents.size() > 0;
	}

}
