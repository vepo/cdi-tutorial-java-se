package io.vepo.cdi.runner;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vepo.cdi.runner.dummy.DummyDatabase;
import io.vepo.cdi.runner.dummy.DummyEvent;

@Singleton
public class CdiRunner implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(CdiRunner.class);

	public static void main(String[] args) {
		SeContainerInitializer initializer = SeContainerInitializer.newInstance();
		try (SeContainer container = initializer.initialize()) {
			CdiRunner runner = container.select(CdiRunner.class).get();
			runner.run();
		}
	}

	@Inject
	private DummyDatabase database;

	@Override
	public void run() {
		logger.info("[BEGIN] Initializing Processing....");
		while (database.hasNext()) {
			DummyEvent event = database.next();
			logger.info("Processing... {}", event);
		}
		logger.info("[END] Events processed!");
	}
}
