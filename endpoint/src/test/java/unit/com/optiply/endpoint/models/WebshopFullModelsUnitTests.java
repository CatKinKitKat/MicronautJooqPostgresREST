package com.optiply.endpoint.models;

import com.optiply.endpoint.environment.TestEnvironment;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

/**
 * The type Webshop models test.
 */
@MicronautTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WebshopFullModelsUnitTests extends TestEnvironment {

	/**
	 * Test webshop simple model.
	 */
	@Test
	void testWebshopModel() {

		WebshopModel test0 =
				new WebshopModel("test", "test.com", (short) 20,
						33.0, 33.0, 34.0, List.of("test@test.com"));

		WebshopModel test1 =
				new WebshopModel("test1", "http://www.test1.com", (short) 20,
						10.0, 10.0, 10.0, List.of("test1@test1.com"));

		WebshopModel test2 =
				new WebshopModel("test2", "http://www.test2.com", (short) 20,
						33.0, 33.0, 34.0, List.of("test2@test2"));

		WebshopModel test3 =
				new WebshopModel("test3", "http://www.test3.com", (short) 20,
						25.0, 30.0, 45.0, List.of("test3@test3.com"));

		Assertions.assertFalse(test0.isValid()); //URL is invalid
		Assertions.assertFalse(test1.isValid()); //Service Level Sum is invalid
		Assertions.assertFalse(test2.isValid()); //Email is invalid
		Assertions.assertTrue(test3.isValid());

		WebshopModel test4 = new WebshopModel();
		test4.setHandle("test3");
		test4.setUrl("http://www.test3.com");
		test4.setServiceLevelA(25.0);
		test4.setServiceLevelB(30.0);
		test4.setServiceLevelC(45.0);
		test4.setEmails(List.of("test3@test3.com"));

		Assertions.assertTrue(test4.isValid());
		Assertions.assertEquals(test4, test3);
	}


	/**
	 * Test webshop settings model.
	 */
	@Test
	void testWebshopSettingsModel() {

		WebshopSettingsModel test1 = new WebshopSettingsModel(
				"test1", "EURO", false, false);

		WebshopSettingsModel test2 = new WebshopSettingsModel(
				"test2", "EUR", false, false);

		Assertions.assertFalse(test1.isValid());
		Assertions.assertTrue(test2.isValid());

		WebshopSettingsModel test3 = new WebshopSettingsModel();
		test3.setHandle("test2");
		test3.setCurrency("EUR");
		test3.setRunJobs(false);
		test3.setMultiSupplier(false);

		Assertions.assertTrue(test3.isValid());
		Assertions.assertEquals(test3, test2);
	}

	@Test
	void testWebshopFullModel() {

		WebshopFullModel test1 = new WebshopFullModel(
				"test1", "test1.com", (short) 20,
				33.0, 33.0, 34.0, "EUR",
				true, true, List.of("test1@test1.com"));

		WebshopFullModel test2 = new WebshopFullModel(
				"test2", "http://www.test2.com", (short) 20,
				10.0, 10.0, 10.0, "EUR",
				true, true, List.of("test2@test2.com"));

		WebshopFullModel test3 = new WebshopFullModel(
				"test3", "http://www.test3.com", (short) 20,
				25.0, 30.0, 45.0, "EUR",
				true, true, List.of("test3@test3"));

		WebshopFullModel test4 = new WebshopFullModel(
				"test4", "http://www.test4.com", (short) 20,
				25.0, 30.0, 45.0, "EUR",
				true, true, List.of("test4@test4.com"));

		Assertions.assertFalse(test1.isValid()); //URL is invalid
		Assertions.assertFalse(test2.isValid()); //Service Level Sum is invalid
		Assertions.assertFalse(test3.isValid()); //Email is invalid
		Assertions.assertTrue(test4.isValid());

		WebshopFullModel test5 = new WebshopFullModel();
		test5.setHandle("test4");
		test5.setUrl("http://www.test4.com");
		test5.setServiceLevelA(25.0);
		test5.setServiceLevelB(30.0);
		test5.setServiceLevelC(45.0);
		test5.setCurrency("EUR");
		test5.setRunJobs(true);
		test5.setMultiSupplier(true);
		test5.setEmails(List.of("test4@test4.com"));

		Assertions.assertTrue(test5.isValid());
		Assertions.assertEquals(test5, test4);

	}

	@Test
	void testSmallModels() {

		EmailListModel test1 = new EmailListModel(List.of("test1@test", "test2@test.com"));
		EmailListModel test2 = new EmailListModel(List.of("test1@test.pt", "test2@test.com"));

		Assertions.assertFalse(test1.isValid());
		Assertions.assertTrue(test2.isValid());

		EmailModel test3 = new EmailModel("test1@test");
		EmailModel test4 = new EmailModel("test1@test.com");

		Assertions.assertFalse(test3.isValid());
		Assertions.assertTrue(test4.isValid());

		HandleModel test5 = new HandleModel("");
		HandleModel test6 = new HandleModel("test");

		Assertions.assertFalse(test5.isValid());
		Assertions.assertTrue(test6.isValid());

		InterestRateModel test7 = new InterestRateModel((short) -1);
		InterestRateModel test8 = new InterestRateModel((short) 1);

		Assertions.assertFalse(test7.isValid());
		Assertions.assertTrue(test8.isValid());

		ServiceLevelsModel test9 = new ServiceLevelsModel(10.0, 10.0, 10.0);
		ServiceLevelsModel test10 = new ServiceLevelsModel(25.0, 30.0, 45.0);

		Assertions.assertFalse(test9.isValid());
		Assertions.assertTrue(test10.isValid());

		SettingsModel test11 = new SettingsModel("EURO", true, true);
		SettingsModel test12 = new SettingsModel("EUR", true, true);

		Assertions.assertFalse(test11.isValid());
		Assertions.assertTrue(test12.isValid());

		UrlModel test13 = new UrlModel("test.com");
		UrlModel test14 = new UrlModel("http://test.com");
		UrlModel test15 = new UrlModel("http://www.test.com/");

		Assertions.assertFalse(test13.isValid());
		Assertions.assertTrue(test14.isValid());
		Assertions.assertTrue(test15.isValid());

	}

}