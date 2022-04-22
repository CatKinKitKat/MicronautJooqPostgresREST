package com.optiply.endpoint.models;

import com.optiply.endpoint.environment.TestEnvironment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * The type Webshop models test.
 */
class WebshopModelsUnitTests extends TestEnvironment {

	/**
	 * Test webshop simple model.
	 */
	@Test
	void testWebshopSimpleModel() {

		WebshopSimpleModel test1 =
				new WebshopSimpleModel("test1", "http://www.test1.com",
						10.0, 10.0, 10.0);

		WebshopSimpleModel test2 =
				new WebshopSimpleModel("test2", "www.test3.com",
						25.0, 30.0, 45.0);

		WebshopSimpleModel test3 =
				new WebshopSimpleModel("test3", "http://www.test3.com",
						25.0, 30.0, 45.0);

		Assertions.assertFalse(test1.isValid());
		Assertions.assertFalse(test2.isValid());
		Assertions.assertTrue(test3.isValid());

		WebshopSimpleModel test4 = new WebshopSimpleModel();
		test4.setHandle("test3");
		test4.setUrl("http://www.test3.com");
		test4.setA(25.0);
		test4.setB(30.0);
		test4.setC(45.0);

		Assertions.assertTrue(test4.isValid());
		Assertions.assertEquals(test4, test3);
	}

	/**
	 * Test webshop body model.
	 */
	@Test
	void testWebshopBodyModel() {

		WebshopBodyModel test1 =
				new WebshopBodyModel("test1", "http://www.test1.com", (short) 15,
						10.0, 10.0, 10.0, "EUR", false, false);

		WebshopBodyModel test2 =
				new WebshopBodyModel("test2", "http://www.test2.com", (short) 15,
						25.0, 30.0, 45.0, "EURO", false, false);

		WebshopBodyModel test3 =
				new WebshopBodyModel("test3", "www.test3.com", (short) 15,
						25.0, 30.0, 45.0, "EUR", false, false);

		WebshopBodyModel test4 =
				new WebshopBodyModel("test4", "http://www.test4.com", (short) 15,
						25.0, 30.0, 45.0, "EUR", false, false);


		Assertions.assertFalse(test1.isValid());
		Assertions.assertFalse(test2.isValid());
		Assertions.assertFalse(test3.isValid());
		Assertions.assertTrue(test4.isValid());

		WebshopBodyModel test5 = new WebshopBodyModel();
		test5.setHandle("test4");
		test5.setUrl("http://www.test4.com");
		test5.setInterestRate((short) 15);
		test5.setA(25.0);
		test5.setB(30.0);
		test5.setC(45.0);
		test5.setCurrency("EUR");
		test5.setRunJobs(false);
		test5.setMultiSupplier(false);

		Assertions.assertTrue(test5.isValid());
		Assertions.assertEquals(test5, test4);
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

	/**
	 * Test webshop emails model.
	 */
	@Test
	void testWebshopEmailsModel() {

		WebshopEmailsModel test1 = new WebshopEmailsModel(
				"test1", List.of("test@test-pt", "test@test.pt"));

		WebshopEmailsModel test2 = new WebshopEmailsModel(
				"test2", List.of("test@test.com", "test@test"));

		WebshopEmailsModel test3 = new WebshopEmailsModel(
				"test3", List.of("test@test.pt", "test@test.com"));

		Assertions.assertFalse(test1.isValid());
		Assertions.assertFalse(test2.isValid());
		Assertions.assertTrue(test3.isValid());

		WebshopEmailsModel test4 = new WebshopEmailsModel();
		test4.setHandle("test3");
		test4.setEmails(List.of("test@test.pt", "test@test.com"));

		Assertions.assertTrue(test4.isValid());
		Assertions.assertEquals(test4, test3);
	}

}
