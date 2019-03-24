package com.cashregister.config;

import java.io.File;
import java.time.LocalDate;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.cashregister.model.DateEntity;
import com.cashregister.service.BaseService;

@Service
public class FileStructureOrganizer extends BaseService {

	// public static String CURRENT_FOLDER_LOCATION = "D:\\clientDocuments\\";
	public static String CURRENT_FOLDER_LOCATION = "/home/plamendanielpics/cashregister/clientDocuments/";

	@Autowired
	@Qualifier("transactionManager")
	protected PlatformTransactionManager txManager;

	@PostConstruct
	public void folderOrganizer() {
		System.err.println("initializing folder organizer");
		final DateEntity dateEntity = getEm().find(DateEntity.class, 1);
		final LocalDate cuurentDate = LocalDate.now();

		if (cuurentDate.getMonthValue() > dateEntity.getMonth()) {

			// ***
			// In the @PostConstruct (as with the afterPropertiesSet from the
			// InitializingBean interface)
			// there is no way to ensure that all the post processing is already done, so
			// (indeed) there can
			// be no Transactions. The only way to ensure that that is working is by using a
			// TransactionTemplate.

			TransactionTemplate tmpl = new TransactionTemplate(txManager);
			tmpl.execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					dateEntity.setMonth(cuurentDate.getMonthValue());
					getEm().merge(dateEntity);
				}
			});

			File theDir = new File(
					CURRENT_FOLDER_LOCATION + cuurentDate.getMonth().name() + "-" + cuurentDate.getYear());

			if (!theDir.exists()) {
				System.out.println("creating directory: " + theDir.getName());
				boolean result = false;

				try {
					theDir.mkdir();
					result = true;
				} catch (SecurityException se) {
					System.err.println(se.getMessage());
				}
				if (result) {
					CURRENT_FOLDER_LOCATION = theDir.getAbsolutePath();
				}
			}
		} else {
			CURRENT_FOLDER_LOCATION = CURRENT_FOLDER_LOCATION + cuurentDate.getMonth().name() + "-"
					+ cuurentDate.getYear();
			System.err.println("Used directory: " + CURRENT_FOLDER_LOCATION);
		}

	}

}
