package com.cashregister.config;

import java.io.File;
import java.time.LocalDate;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class FileStructureOrganizer {

	public static String CURRENT_FOLDER_LOCATION;

	// cron = "0 59 23 * * ?"
	@Scheduled(fixedDelay = 90000)
	public void reportCurrentTime() {

		// Calendar c = Calendar.getInstance();
		// if (c.get(Calendar.DATE) == c.getActualMaximum(Calendar.DATE)) {

		LocalDate futureDate = LocalDate.now().plusMonths(1);

		File theDir = new File("D:/" + futureDate.getMonth().name() + "-" + futureDate.getYear());

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

		// }

	}

}
