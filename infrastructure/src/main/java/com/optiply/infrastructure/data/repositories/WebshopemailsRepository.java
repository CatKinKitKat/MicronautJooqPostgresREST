package com.optiply.infrastructure.data.repositories;


import com.optiply.infrastructure.data.models.Tables;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.jooq.DSLContext;

import java.util.List;

/**
 * The type Webshopemails repository.
 */
@Singleton
public class WebshopemailsRepository {

	/**
	 * The Dsl context.
	 */
	private final DSLContext dslContext;


	/**
	 * Instantiates a new Webshopemails repository.
	 *
	 * @param dslContext the dsl context
	 */
	@Inject
	public WebshopemailsRepository(@Named("dsl") DSLContext dslContext) {

		this.dslContext = dslContext;
	}

	/**
	 * Create boolean.
	 *
	 * @param webshopId the webshop id
	 * @param email     the email
	 * @return the boolean
	 */
	public Boolean create(Long webshopId, String email) {
		return dslContext.insertInto(Tables.WEBSHOPEMAILS)
				.columns(Tables.WEBSHOPEMAILS.WEBSHOPID, Tables.WEBSHOPEMAILS.ADDRESS)
				.values(webshopId, email)
				.execute() == 0;
	}

	/**
	 * Create boolean.
	 *
	 * @param handle the handle
	 * @param email  the email
	 * @return the boolean
	 */
	public Boolean create(String handle, String email) {
		Long webshopId = dslContext.select(Tables.WEBSHOP.WEBSHOPID)
				.from(Tables.WEBSHOP)
				.where(Tables.WEBSHOP.HANDLE.equalIgnoreCase(handle))
				.fetchOne(Tables.WEBSHOP.WEBSHOPID);

		return dslContext.insertInto(Tables.WEBSHOPEMAILS)
				.columns(Tables.WEBSHOPEMAILS.WEBSHOPID, Tables.WEBSHOPEMAILS.ADDRESS)
				.values(webshopId, email)
				.execute() == 0;
	}

	/**
	 * Gets emails.
	 *
	 * @param webshopId the webshop id
	 * @return the emails
	 */
	public List<String> getEmails(Long webshopId) {
		return dslContext.select(Tables.WEBSHOPEMAILS.ADDRESS)
				.from(Tables.WEBSHOPEMAILS)
				.where(Tables.WEBSHOPEMAILS.WEBSHOPID.eq(webshopId))
				.fetch(Tables.WEBSHOPEMAILS.ADDRESS);
	}

	/**
	 * Gets emails.
	 *
	 * @param handle the handle
	 * @return the emails
	 */
	public List<String> getEmails(String handle) {
		return dslContext.select(Tables.WEBSHOPEMAILS.ADDRESS)
				.from(Tables.WEBSHOPEMAILS)
				.where(Tables.WEBSHOP.HANDLE.equalIgnoreCase(handle))
				.fetch(Tables.WEBSHOPEMAILS.ADDRESS);
	}

	/**
	 * Delete all boolean.
	 *
	 * @param webshopId the webshop id
	 * @return the boolean
	 */
	public Boolean deleteAll(Long webshopId) {
		return dslContext.delete(Tables.WEBSHOPEMAILS)
				.where(Tables.WEBSHOPEMAILS.WEBSHOPID.eq(webshopId))
				.execute() == 0;
	}

	/**
	 * Delete one boolean.
	 *
	 * @param webshopId the webshop id
	 * @param email     the email
	 * @return the boolean
	 */
	public Boolean deleteOne(Long webshopId, String email) {
		return dslContext.delete(Tables.WEBSHOPEMAILS)
				.where(Tables.WEBSHOPEMAILS.WEBSHOPID.eq(webshopId))
				.and(Tables.WEBSHOPEMAILS.ADDRESS.eq(email))
				.execute() == 0;
	}

	/**
	 * Delete one boolean.
	 *
	 * @param handle the handle
	 * @param email  the email
	 * @return the boolean
	 */
	public Boolean deleteOne(String handle, String email) {
		Long webshopId = dslContext.select(Tables.WEBSHOP.WEBSHOPID)
				.from(Tables.WEBSHOP)
				.where(Tables.WEBSHOP.HANDLE.equalIgnoreCase(handle))
				.fetchOne(Tables.WEBSHOP.WEBSHOPID);

		return dslContext.delete(Tables.WEBSHOPEMAILS)
				.where(Tables.WEBSHOPEMAILS.WEBSHOPID.eq(webshopId))
				.and(Tables.WEBSHOPEMAILS.ADDRESS.eq(email))
				.execute() == 0;
	}

}