package com.quickfix.simulator;

import com.quickfix.simulator.core.FIXimulator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		FIXimulator simulator = new FIXimulator();
		simulator.start();
	}

}
