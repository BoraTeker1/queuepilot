package com.queuepilot.queuepilot;

import org.springframework.boot.SpringApplication;

public class TestQueuepilotApplication {

	public static void main(String[] args) {
		SpringApplication.from(QueuepilotApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
