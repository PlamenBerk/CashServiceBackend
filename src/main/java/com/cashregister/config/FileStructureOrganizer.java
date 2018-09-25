package com.cashregister.config;

import java.io.File;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class FileStructureOrganizer {

	public static String CURRENT_FOLDER_LOCATION;

	@Scheduled(cron = "0 59 23 L * ?")
	public void reportCurrentTime() {
		LocalDateTime date = LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()),
				ZoneId.systemDefault());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		File theDir = new File("D:/" + date.format(formatter));

		if (!theDir.exists()) {
			System.out.println("creating directory: " + theDir.getName());
			boolean result = false;

			try {
				theDir.mkdir();
				result = true;
			} catch (SecurityException se) {
				// handle it
			}
			if (result) {
				// System.out.println("DIR created " + theDir.getAbsolutePath());
				CURRENT_FOLDER_LOCATION = theDir.getAbsolutePath();

			}
		}
	}

}
