package com.optiply.endpoint.controllers;

import com.optiply.endpoint.models.WebshopBodyModel;
import com.optiply.endpoint.models.WebshopEmailsModel;
import com.optiply.endpoint.models.WebshopSettingsModel;
import com.optiply.endpoint.models.WebshopSimpleModel;
import com.optiply.infrastructure.data.models.Tables;
import com.optiply.infrastructure.data.models.tables.pojos.Webshop;
import com.optiply.infrastructure.data.repositories.WebshopRepository;
import com.optiply.infrastructure.data.repositories.WebshopemailsRepository;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import static org.mockito.Mockito.*;

@MicronautTest
public class EndpointControllerTest {

	@InjectMocks
	private EndpointController endpointController;

	@Mock
	private WebshopRepository webshopRepository;

	@Mock
	private WebshopemailsRepository webshopemailsRepository;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindVariousSingle() {

		WebshopBodyModel test = new WebshopBodyModel();
		test.setHandle("test");
		test.setUrl("http://www.test.com");
		test.setInterestRate((short) 20);
		test.setA(33.0);
		test.setB(33.0);
		test.setC(34.0);
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
				Objects.requireNonNull(endpointController
						.getWebshops(new String[]{"handle:test"}, "handle", "asc")
						.block()).getBody();

		Assertions.assertTrue(resultFoundSingle.isPresent());
		Assertions.assertEquals(test, resultFoundSingle.get()[0]);
	}

	@Test
	void testFindVariousMultiple() {

		WebshopBodyModel test = new WebshopBodyModel();
		test.setHandle("test");
		test.setUrl("http://www.test.com");
		test.setInterestRate((short) 20);
		test.setA(33.0);
		test.setB(33.0);
		test.setC(34.0);
		test.setCurrency("EUR");
		test.setRunJobs(true);
		test.setMultiSupplier(false);

		WebshopBodyModel optiply = new WebshopBodyModel();
		optiply.setHandle("optiply");
		optiply.setUrl("http://www.optiply.nl");
		optiply.setInterestRate((short) 25);
		optiply.setA(50.0);
		optiply.setB(25.0);
		optiply.setC(25.0);
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
				Objects.requireNonNull(endpointController
						.getWebshops(new String[]{"interestRate>10"}, "handle", "desc")
						.block()).getBody();

		Assertions.assertTrue(resultFoundMultiple.isPresent());
		Assertions.assertEquals(test, resultFoundMultiple.get()[0]);
		Assertions.assertEquals(optiply, resultFoundMultiple.get()[1]);
	}



}
