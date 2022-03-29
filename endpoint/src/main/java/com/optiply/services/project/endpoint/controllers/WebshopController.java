package com.optiply.services.project.endpoint.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.optiply.infrastructure.data.models.tables.pojos.Webshop;
import com.optiply.infrastructure.data.models.tables.pojos.Webshopemails;
import com.optiply.infrastructure.data.models.tables.pojos.Webshopservicelevels;
import com.optiply.infrastructure.data.models.tables.pojos.Webshopsettings;
import com.optiply.infrastructure.data.repositories.WebshopRepository;
import com.optiply.infrastructure.data.repositories.WebshopemailsRepository;
import com.optiply.infrastructure.data.repositories.WebshopservicelevelsRepository;
import com.optiply.infrastructure.data.repositories.WebshopsettingsRepository;
import com.optiply.services.project.endpoint.parsers.QueryParamParser;
import com.optiply.services.project.endpoint.protobuf.WebshopModel;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.validation.Validated;
import jakarta.inject.Inject;
import org.jooq.Condition;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * The type Webshop controller.
 */
@Validated
@Controller("/v1/webshop")
public class WebshopController {

	/**
	 * The Mapper.
	 */
	private final ObjectMapper mapper;
	/**
	 * The Parser.
	 */
	private final QueryParamParser parser;

	/**
	 * The Webshop repository.
	 */
	private WebshopRepository webshopRepository;
	/**
	 * The Settings repository.
	 */
	private WebshopsettingsRepository settingsRepository;
	/**
	 * The Servicelevels repository.
	 */
	private WebshopservicelevelsRepository servicelevelsRepository;
	/**
	 * The Emails repository.
	 */
	private WebshopemailsRepository emailsRepository;

	/**
	 * Instantiates a new Webshop controller.
	 */
	@Inject
	public WebshopController() {
		this.mapper = new ObjectMapper();
		this.parser = new QueryParamParser();
	}


	/**
	 * Sets webshop repository.
	 *
	 * @param webshopRepository the webshop repository
	 */
	@Inject
	public void setWebshopRepository(WebshopRepository webshopRepository) {
		this.webshopRepository = webshopRepository;
	}

	/**
	 * Sets settings repository.
	 *
	 * @param settingsRepository the settings repository
	 */
	@Inject
	public void setSettingsRepository(WebshopsettingsRepository settingsRepository) {
		this.settingsRepository = settingsRepository;
	}

	/**
	 * Sets servicelevels repository.
	 *
	 * @param servicelevelsRepository the servicelevels repository
	 */
	@Inject
	public void setServicelevelsRepository(WebshopservicelevelsRepository servicelevelsRepository) {
		this.servicelevelsRepository = servicelevelsRepository;
	}

	/**
	 * Sets emails repository.
	 *
	 * @param emailsRepository the emails repository
	 */
	@Inject
	public void setEmailsRepository(WebshopemailsRepository emailsRepository) {
		this.emailsRepository = emailsRepository;
	}

	/**
	 * To model webshop model . some message or builder.
	 *
	 * @param id           the id
	 * @param handler      the handler
	 * @param url          the url
	 * @param interestRate the interest rate
	 * @return the webshop model . some message or builder
	 */
	private WebshopModel.SomeMessageOrBuilder toModel(Long id, String handler, String url, Short interestRate) {

		Mono<Webshopsettings> settings = settingsRepository.read(id);

		String currency = settings.block().getCurrency();
		Boolean multiSupply = Boolean.valueOf(settings.block().getMultisupply());
		Boolean runJubs = Boolean.valueOf(settings.block().getRunjobs());

		Mono<Webshopservicelevels> servicelevels = servicelevelsRepository.read(id);

		Double Slca = servicelevels.block().getSlca();
		Double Slcb = servicelevels.block().getSlcb();
		Double Slcc = servicelevels.block().getSlcc();

		Flux<Webshopemails> emails = emailsRepository.readByWebshopId(id);

		String[] emailList = emails.collectList().flatMap(email -> {
			String[] emailArray = new String[email.size()];
			for (int i = 0; i < email.size(); i++) {
				emailArray[i] = email.get(i).getAddress();
			}
			return Mono.just(emailArray);
		}).block();

		WebshopModel.SomeMessageOrBuilder webshopModel = WebshopModel.SomeMessage.newBuilder()
				.setHandle(handler).setUrl(url).setInterestRate(interestRate)
				.setEmails(WebshopModel.SomeMessage.Emails.newBuilder().addAllEmails(List.of(emailList)))
				.setServiceLevels(WebshopModel.SomeMessage.Servicelevels.newBuilder()
						.setSlcA(Slca).setSlcB(Slcb).setSlcC(Slcc))
				.setSettings(WebshopModel.SomeMessage.Settings.newBuilder()
						.setCurrency(currency).setMultiSupplier(multiSupply).setRunJobs(runJubs)).build();

		return webshopModel;
	}


	/**
	 * Gets webshop.
	 *
	 * @param handle the handle
	 * @return the webshop
	 */
	@Get("/exact/{handle}")
	public Mono<HttpResponse<String>> getWebshop(String handle) {

		Mono<Webshop> webshop = webshopRepository.readByHandle(handle);
		Long id = webshop.block().getWebshopid();
		String handler = webshop.block().getHandle();
		String url = webshop.block().getUrl();
		Short interestRate = webshop.block().getInterestrate();

		WebshopModel.SomeMessageOrBuilder webshopModel = toModel(id, handler, url, interestRate);

		try {
			return Mono.just(HttpResponse.ok(mapper.writeValueAsString(webshopModel)));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return Mono.just(HttpResponse.serverError());
	}

	/**
	 * Gets webshops.
	 *
	 * @param query the query
	 * @return the webshops
	 */
	@Get("{query}")
	public Flux<HttpResponse<String>> getWebshops(String query) {

		if (query == null || query.equals("")) {
			return Flux.just(HttpResponse.badRequest(null));
		}

		List<Condition> conditions = this.parser.parseParams(this.parser.parseQuery(query));

		Webshop[] webshops = this.webshopRepository.multiConditionalRead(
				conditions.toArray(new Condition[0])
		).collect(Collectors.toList()).block().toArray(new Webshop[0]);

		List<WebshopModel.SomeMessageOrBuilder> webshopModels = new ArrayList<>();

		for (Webshop webshop : webshops) {

			Long id = webshop.getWebshopid();
			String handler = webshop.getHandle();
			String url = webshop.getUrl();
			Short interestRate = webshop.getInterestrate();

			webshopModels.add(toModel(id, handler, url, interestRate));

		}

		return Flux.fromArray(webshopModels.toArray(new WebshopModel.SomeMessageOrBuilder[0])).map(webshopModel -> {
			try {
				return HttpResponse.ok(mapper.writeValueAsString(webshopModel));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return HttpResponse.serverError();
		});
	}


}
