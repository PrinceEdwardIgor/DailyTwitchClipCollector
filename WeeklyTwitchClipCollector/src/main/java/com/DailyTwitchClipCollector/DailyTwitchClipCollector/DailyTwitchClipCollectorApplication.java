package com.DailyTwitchClipCollector.DailyTwitchClipCollector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = {"com.DailyTwitchClipCollector.DailyTwitchClipCollector.*"})
public class DailyTwitchClipCollectorApplication {
	public static void main(String[] args) {
		SpringApplication.run(DailyTwitchClipCollectorApplication.class, args);
	}
}
