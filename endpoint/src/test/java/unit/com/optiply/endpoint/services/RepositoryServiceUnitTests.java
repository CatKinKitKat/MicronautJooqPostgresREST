package com.optiply.endpoint.services;

import com.optiply.endpoint.environment.TestEnvironment;
import com.optiply.endpoint.models.WebshopBodyModel;
import com.optiply.endpoint.models.WebshopEmailsModel;
import com.optiply.endpoint.models.WebshopSettingsModel;
import com.optiply.endpoint.models.WebshopSimpleModel;
import com.optiply.infrastructure.data.models.Tables;
import com.optiply.infrastructure.data.models.tables.pojos.Webshop;
import com.optiply.infrastructure.data.repositories.WebshopRepository;
import com.optiply.infrastructure.data.repositories.WebshopemailsRepository;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.mockito.Mockito.when;

/**
 * The type Endpoint controller unit tests.
 */
@MicronautTest
public class RepositoryServiceUnitTests extends TestEnvironment {

	@InjectMocks
	private RepositoryService repositoryService;

	@Mock
	private WebshopRepository webshopRepository;

	@Mock
	private WebshopemailsRepository webshopemailsRepository;

	/**
	 * Sets .
	 */
	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	/**
	 * Test find various single.
	 */
	@Test
	void testFindVariousSingle() {

		WebshopBodyModel test = new WebshopBodyModel();
		test.setHandle("test");
		test.setUrl("http://www.test.com");
		test.setInterestRate((short) 20);
		test.setServiceLevelA(33.0);
		test.setServiceLevelB(33.0);
		test.setServiceLevelC(34.0);
		test.setCurrency("EUR");
		test.setRunJobs(true);
		test.setMultiSupplier(false);

		when(
				webshopRepository.findVarious(
						Tables.WEBSHOP.HANDLE.equalIgnoreCase("test"),
						Tables.WEBSHOP.HANDLE.asc()
				)
		).thenReturn(
				Flux.just(
						new Webshop(1L, "test", "http://www.test.com",
								(short) 20, 33.0, 33.0, 34.0,
								"EUR", true, false)
				)
		);

		Optional<WebshopBodyModel[]> resultFoundSingle =
				Objects.requireNonNull(repositoryService
						.getWebshops(Tables.WEBSHOP.HANDLE.equalIgnoreCase("test"), Tables.WEBSHOP.HANDLE.asc())
						.block()).getBody();

		Assertions.assertTrue(resultFoundSingle.isPresent());
		Assertions.assertEquals(test, resultFoundSingle.get()[0]);
	}

	/**
	 * Test find various multiple.
	 */
	@Test
	void testFindVariousMultiple() {

		WebshopBodyModel test = new WebshopBodyModel();
		test.setHandle("test");
		test.setUrl("http://www.test.com");
		test.setInterestRate((short) 20);
		test.setServiceLevelA(33.0);
		test.setServiceLevelB(33.0);
		test.setServiceLevelC(34.0);
		test.setCurrency("EUR");
		test.setRunJobs(true);
		test.setMultiSupplier(false);

		WebshopBodyModel optiply = new WebshopBodyModel();
		optiply.setHandle("optiply");
		optiply.setUrl("http://www.optiply.nl");
		optiply.setInterestRate((short) 25);
		optiply.setServiceLevelA(50.0);
		optiply.setServiceLevelB(25.0);
		optiply.setServiceLevelC(25.0);
		optiply.setCurrency("USD");
		optiply.setRunJobs(false);
		optiply.setMultiSupplier(true);

		when(
				webshopRepository.findVarious(
						Tables.WEBSHOP.INTEREST_RATE.greaterThan((short) 10),
						Tables.WEBSHOP.HANDLE.desc()
				)
		).thenReturn(
				Flux.just(
						new Webshop(1L, "test", "http://www.test.com",
								(short) 20, 33.0, 33.0, 34.0,
								"EUR", true, false),
						new Webshop(2L, "optiply", "http://www.optiply.nl",
								(short) 25, 50.0, 25.0, 25.0,
								"USD", false, true))
		);

		Optional<WebshopBodyModel[]> resultFoundMultiple =
				Objects.requireNonNull(repositoryService
						.getWebshops(Tables.WEBSHOP.INTEREST_RATE.greaterThan((short) 10), Tables.WEBSHOP.HANDLE.desc())
						.block()).getBody();

		Assertions.assertTrue(resultFoundMultiple.isPresent());
		Assertions.assertEquals(test, resultFoundMultiple.get()[0]);
		Assertions.assertEquals(optiply, resultFoundMultiple.get()[1]);
	}

	/**
	 * Test get webshop.
	 */
	@Test
	void testGetWebshop() {

		WebshopSimpleModel test = new WebshopSimpleModel();
		test.setHandle("test");
		test.setUrl("http://www.test.com");
		test.setInterestRate((short) 20);
		test.setServiceLevelA(33.0);
		test.setServiceLevelB(33.0);
		test.setServiceLevelC(34.0);

		when(
				webshopRepository.find("test")
		).thenReturn(
				Mono.just(
						new Webshop(1L, "test", "http://www.test.com",
								(short) 20, 33.0, 33.0, 34.0,
								"EUR", true, false)
				)
		);

		Optional<WebshopSimpleModel> resultFoundSingle =
				Objects.requireNonNull(repositoryService
						.getWebshop("test")
						.block()).getBody();

		Assertions.assertTrue(resultFoundSingle.isPresent());
		Assertions.assertEquals(test, resultFoundSingle.get());
	}

	/**
	 * Test get webshop setiings.
	 */
	@Test
	void testGetWebshopSetiings() {

		WebshopSettingsModel test = new WebshopSettingsModel();
		test.setHandle("test");
		test.setCurrency("EUR");
		test.setRunJobs(true);
		test.setMultiSupplier(false);

		when(
				webshopRepository.find("test")
		).thenReturn(
				Mono.just(
						new Webshop(1L, "test", "http://www.test.com",
								(short) 20, 33.0, 33.0, 34.0,
								"EUR", true, false)
				)
		);

		Optional<WebshopSettingsModel> resultFoundSingle =
				Objects.requireNonNull(repositoryService
						.getWebshopSettings("test")
						.block()).getBody();

		Assertions.assertTrue(resultFoundSingle.isPresent());
		Assertions.assertEquals(test, resultFoundSingle.get());

	}

	/**
	 * Test get webshop emails.
	 */
	@Test
	void testGetWebshopEmails() {

		WebshopEmailsModel test = new WebshopEmailsModel();
		test.setHandle("test");
		test.setEmails(List.of(new String[]{"test@test.pt", "tester@test.pt"}));

		when(
				webshopemailsRepository.findEmails("test")
		).thenReturn(
				Flux.just("test@test.pt", "tester@test.pt")
		);

		Optional<WebshopEmailsModel> result =
				Objects.requireNonNull(repositoryService
						.getWebshopEmails("test")
						.block()).getBody();

		Assertions.assertTrue(result.isPresent());
		Assertions.assertEquals(test, result.get());

	}

	/**
	 * Test create.
	 */
	@Test
	void testCreate() {

		final String OK_STR = "Webshop created.";
		WebshopBodyModel sumthin = new WebshopBodyModel();
		sumthin.setHandle("sumthin");
		sumthin.setUrl("http://www.sumthin.com");
		sumthin.setServiceLevelA(20.0);
		sumthin.setServiceLevelB(30.0);
		sumthin.setServiceLevelC(50.0);
		sumthin.setInterestRate((short) 15);
		sumthin.setCurrency("USD");
		sumthin.setRunJobs(false);
		sumthin.setMultiSupplier(false);

		when(
				webshopRepository.create(
						"sumthin", "http://www.sumthin.com",
						20.0, 30.0, 50.0, (short) 15,
						"USD", false, false
				)
		).thenReturn(
				Mono.just(true)
		);

		Optional<String> response =
				Objects.requireNonNull(repositoryService
						.createWebshop(sumthin).block()
				).getBody();

		Assertions.assertTrue(response.isPresent());
		Assertions.assertEquals(OK_STR, response.get());

	}

	/**
	 * Test add emails.
	 */
	@Test
	void testAddEmails() {

		final String OK_STR = "Email added.";

		when(
				webshopemailsRepository.create(
						"sumthin", "redneck@sumthin.com"
				)
		).thenReturn(
				Mono.just(true)
		);

		Optional<String> response =
				Objects.requireNonNull(repositoryService
						.addEmail("sumthin", "redneck@sumthin.com").block()
				).getBody();

		Assertions.assertTrue(response.isPresent());
		Assertions.assertEquals(OK_STR, response.get());

	}

	/**
	 * Test update webshop.
	 */
	@Test
	void testUpdateWebshop() {

		final String OK_STR = "Webshop updated.";
		WebshopBodyModel sumthin = new WebshopBodyModel();
		sumthin.setHandle("sumthin");
		sumthin.setUrl("http://www.sumthin.com");
		sumthin.setServiceLevelA(20.0);
		sumthin.setServiceLevelB(30.0);
		sumthin.setServiceLevelC(50.0);
		sumthin.setInterestRate((short) 15);
		sumthin.setCurrency("USD");
		sumthin.setRunJobs(false);
		sumthin.setMultiSupplier(false);

		when(
				webshopRepository.updateWebshop(
						"sumthin", "http://www.sumthin.com",
						20.0, 30.0, 50.0, (short) 15,
						"USD", false, false
				)
		).thenReturn(
				Mono.just(true)
		);

		Optional<String> response =
				Objects.requireNonNull(repositoryService
						.updateWebshop(sumthin).block()
				).getBody();

		Assertions.assertTrue(response.isPresent());
		Assertions.assertEquals(OK_STR, response.get());

	}

	/**
	 * Test remove email.
	 */
	@Test
	void testRemoveEmail() {

		final String OK_STR = "Email removed.";

		when(
				webshopemailsRepository.delete("test", "test@test.pt")
		).thenReturn(
				Mono.just(true)
		);

		Optional<String> response =
				Objects.requireNonNull(repositoryService
						.removeEmail("test", "test@test.pt").block()
				).getBody();

		Assertions.assertTrue(response.isPresent());
		Assertions.assertEquals(OK_STR, response.get());

	}

	/**
	 * Test delete webshop.
	 */
	@Test
	void testDeleteWebshop() {

		when(
				webshopRepository.deleteWebshop("test")
		).thenReturn(
				Mono.just(true)
		);

		Optional<Object> response =
				Objects.requireNonNull(repositoryService
						.deleteWebshop("test").block()
				).getBody();

		Assertions.assertFalse(response.isPresent());

	}

}
