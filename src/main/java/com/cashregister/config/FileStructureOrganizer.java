package com.cashregister.config;

import java.io.File;
import java.time.LocalDate;
import java.util.Calendar;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class FileStructureOrganizer {

	// public static String CURRENT_FOLDER_LOCATION = "D:\\OCTOBER-2018";
	public static String CURRENT_FOLDER_LOCATION_DEBIAN = "/home/plamendanielpics/cashregister/OCTOBER-2018";

	// cron = "0 59 23 * * ?"
	@Scheduled(cron = "0 59 23 * * ?")
	public void reportCurrentTime() {

		Calendar c = Calendar.getInstance();
		if (c.get(Calendar.DATE) == c.getActualMaximum(Calendar.DATE)) {

			LocalDate futureDate = LocalDate.now().plusMonths(1);

			File theDir = new File(
					CURRENT_FOLDER_LOCATION_DEBIAN + futureDate.getMonth().name() + "-" + futureDate.getYear());

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
					CURRENT_FOLDER_LOCATION_DEBIAN = theDir.getAbsolutePath();

				}
			}

		}

	}

}
