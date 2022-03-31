package com.optiply.infrastructure.data.repositories;

import com.optiply.infrastructure.data.models.Tables;
import com.optiply.infrastructure.data.models.tables.pojos.Webshop;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.jooq.Condition;
import org.jooq.DSLContext;

import java.util.List;

/**
 * The type Webshop repository.
 */
@Singleton
public class WebshopRepository {

	/**
	 * The Dsl context.
	 */
	private final DSLContext dslContext;


	/**
	 * Instantiates a new Webshop repository.
	 *
	 * @param dslContext the dsl context
	 */
	@Inject
	public WebshopRepository(@Named("dsl") DSLContext dslContext) {

		this.dslContext = dslContext;
	}

	/**
	 * Create webshop boolean.
	 *
	 * @param handle       the handle
	 * @param url          the url
	 * @param interestRate the interest rate
	 * @return the boolean
	 */
	public Boolean createWebshop(String handle, String url, Short interestRate) {
		return dslContext.insertInto(Tables.WEBSHOP)
				.columns(Tables.WEBSHOP.HANDLE, Tables.WEBSHOP.URL, Tables.WEBSHOP.INTERESTRATE)
				.values(handle, url, interestRate)
				.execute() == 0;
	}

	/**
	 * Create webshop boolean.
	 *
	 * @param handle       the handle
	 * @param url          the url
	 * @param interestRate the interest rate
	 * @param A            the a
	 * @param B            the b
	 * @param C            the c
	 * @param Currency     the currency
	 * @param runJobs      the run jobs
	 * @param multiSupply  the multi supply
	 * @return the boolean
	 */
	public Boolean createWebshop(String handle, String url, Short interestRate, Double A, Double B, Double C, String Currency, Boolean runJobs, Boolean multiSupply) {
		return dslContext.insertInto(Tables.WEBSHOP)
				.columns(Tables.WEBSHOP.HANDLE, Tables.WEBSHOP.URL, Tables.WEBSHOP.INTERESTRATE, Tables.WEBSHOP.A, Tables.WEBSHOP.B, Tables.WEBSHOP.C, Tables.WEBSHOP.CURRENCY, Tables.WEBSHOP.RUNJOBS, Tables.WEBSHOP.MULTISUPPLY)
				.values(handle, url, interestRate, A, B, C, Currency, runJobs, multiSupply)
				.execute() == 0;
	}

	/**
	 * Gets webshop id.
	 *
	 * @param handle the handle
	 * @return the webshop id
	 */
	public Long getWebshopId(String handle) {
		return dslContext.selectFrom(Tables.WEBSHOP)
				.where(Tables.WEBSHOP.HANDLE.equalIgnoreCase(handle))
				.fetchOne(Tables.WEBSHOP.WEBSHOPID);
	}

	/**
	 * Gets webshop.
	 *
	 * @param handle the handle
	 * @return the webshop
	 */
	public Webshop getWebshop(String handle) {
		return dslContext.selectFrom(Tables.WEBSHOP)
				.where(Tables.WEBSHOP.HANDLE.equalIgnoreCase(handle))
				.fetchOneInto(Webshop.class);
	}

	/**
	 * Gets webshop.
	 *
	 * @param id the id
	 * @return the webshop
	 */
	public Webshop getWebshop(Long id) {
		return dslContext.selectFrom(Tables.WEBSHOP)
				.where(Tables.WEBSHOP.WEBSHOPID.eq(id))
				.fetchOneInto(Webshop.class);
	}

	/**
	 * Gets webshops.
	 *
	 * @param conditions the conditions
	 * @return the webshops
	 */
	public List<Webshop> getWebshops(List<Condition> conditions) {
		return dslContext.selectFrom(Tables.WEBSHOP)
				.where(conditions)
				.fetchInto(Webshop.class);
	}

	/**
	 * Update webshop boolean.
	 *
	 * @param handle       the handle
	 * @param url          the url
	 * @param interestRate the interest rate
	 * @param A            the a
	 * @param B            the b
	 * @param C            the c
	 * @param Currency     the currency
	 * @param runJobs      the run jobs
	 * @param multiSupply  the multi supply
	 * @return the boolean
	 */
	public Boolean updateWebshop(String handle, String url, Short interestRate, Double A, Double B, Double C, String Currency, Boolean runJobs, Boolean multiSupply) {
		return dslContext.update(Tables.WEBSHOP)
				.set(Tables.WEBSHOP.HANDLE, handle)
				.set(Tables.WEBSHOP.URL, url)
				.set(Tables.WEBSHOP.INTERESTRATE, interestRate)
				.set(Tables.WEBSHOP.A, A)
				.set(Tables.WEBSHOP.B, B)
				.set(Tables.WEBSHOP.C, C)
				.set(Tables.WEBSHOP.CURRENCY, Currency)
				.set(Tables.WEBSHOP.RUNJOBS, runJobs)
				.set(Tables.WEBSHOP.MULTISUPPLY, multiSupply)
				.where(Tables.WEBSHOP.HANDLE.eq(handle))
				.execute() == 0;
	}

	/**
	 * Delete webshop boolean.
	 *
	 * @param handle the handle
	 * @return the boolean
	 */
	public Boolean deleteWebshop(String handle) {
		return dslContext.delete(Tables.WEBSHOP)
				.where(Tables.WEBSHOP.HANDLE.eq(handle))
				.execute() == 0;
	}

}
