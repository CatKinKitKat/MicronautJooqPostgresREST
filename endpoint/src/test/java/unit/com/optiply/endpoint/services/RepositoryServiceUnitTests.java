package com.optiply.endpoint.services;

import com.optiply.endpoint.environment.TestEnvironment;
import com.optiply.endpoint.models.WebshopModel;
import com.optiply.infrastructure.data.models.Tables;
import com.optiply.infrastructure.data.models.tables.pojos.Webshop;
import com.optiply.infrastructure.data.models.tables.pojos.Webshopemails;
import com.optiply.infrastructure.data.repositories.WebshopRepository;
import com.optiply.infrastructure.data.repositories.WebshopemailsRepository;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RepositoryServiceUnitTests extends TestEnvironment {

	@InjectMocks
	private RepositoryService repositoryService;

	@Mock
	private WebshopRepository webshopRepository;

	@Mock
	private WebshopemailsRepository webshopemailsRepository;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}


	/**
	 * Test find various single.
	 */
	@Test
	void testGetWebshopsHandleEqual() {

		Webshop test = new Webshop();
		test.setWebshopId(1L);
		test.setHandle("test");
		test.setUrl("http://www.test.com");
		test.setInterestRate((short) 20);
		test.setA(33.0);
		test.setB(33.0);
		test.setC(34.0);
		test.setCurrency("EUR");
		test.setRunJobs(true);
		test.setMultiSupply(true);

		Webshopemails testMail = new Webshopemails();
		testMail.setWebshopId(1L);
		testMail.setAddressId(1L);
		testMail.setAddress("test@test.com");

		when(webshopRepository.findVarious(
				Tables.WEBSHOP.HANDLE.equalIgnoreCase("test"),
				Tables.WEBSHOP.HANDLE.asc()
		)).thenReturn(Flux.just(test.getHandle()));

		when(webshopRepository.find(test.getHandle()))
				.thenReturn(Mono.just(test));


		when(webshopemailsRepository.findEmails(test.getHandle()))
				.thenReturn(Mono.just(List.of(testMail.getAddress())));

		WebshopModel result = new WebshopModel();
		result.setHandle(test.getHandle());
		result.setUrl(test.getUrl());
		result.setInterestRate(test.getInterestRate());
		result.setServiceLevelA(test.getA());
		result.setServiceLevelB(test.getB());
		result.setServiceLevelC(test.getC());
		result.setEmails(List.of(testMail.getAddress()));

		Optional<List<WebshopModel>> compare = Objects.requireNonNull(repositoryService.getWebshops(
				Tables.WEBSHOP.HANDLE.equalIgnoreCase("test"),
				Tables.WEBSHOP.HANDLE.asc()).block()).getBody();

		Assertions.assertTrue(compare.isPresent());
		Assertions.assertEquals(1, compare.get().size());
		Assertions.assertEquals(result, compare.get().get(0));
	}

}