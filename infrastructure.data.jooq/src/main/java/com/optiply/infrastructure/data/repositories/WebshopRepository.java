package com.optiply.infrastructure.data.repositories;

import com.optiply.infrastructure.data.models.tables.pojos.Webshop;
import com.optiply.infrastructure.data.models.tables.records.WebshopRecord;
import com.optiply.infrastructure.data.repositories.interfaces.IWebshopRepository;
import io.micronaut.data.r2dbc.operations.R2dbcOperations;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

/**
 * The type Webshop repository.
 */
@Singleton
public class WebshopRepository implements IWebshopRepository {

    /**
     * The Dsl context.
     */
    private final DSLContext dslContext;
    /**
     * The Operations.
     */
    private final R2dbcOperations operations;


    /**
     * Instantiates a new Webshop repository.
     *
     * @param dslContext the dsl context
     * @param operations the operations
     */
    @Inject
    public WebshopRepository(@Named("dsl") DSLContext dslContext, @Named("r2dbc") R2dbcOperations operations) {
        this.dslContext = dslContext;
        this.operations = operations;
    }

    /**
     * Gets webshops.
     *
     * @param handle the handle
     * @param exact  the exact
     * @return the webshops
     */
    private Result<Record> getWebshops(String handle, Boolean exact) {

        if (exact) {
            return dslContext.select().from(com.optiply.infrastructure.data.models.tables.Webshop.WEBSHOP).where(com.optiply.infrastructure.data.models.tables.Webshop.WEBSHOP.HANDLE.eq(handle)).fetch();

        }

        return dslContext.select().from(com.optiply.infrastructure.data.models.tables.Webshop.WEBSHOP).where(com.optiply.infrastructure.data.models.tables.Webshop.WEBSHOP.HANDLE.like("%" + handle + "%")).fetch();
    }

    /**
     * Create mono.
     *
     * @param handle       the handle
     * @param url          the url
     * @param interestRate the interest rate
     * @return the mono
     */
    public Mono<Void> create(String handle, String url, Short interestRate) {
        WebshopRecord webshop = dslContext.newRecord(com.optiply.infrastructure.data.models.tables.Webshop.WEBSHOP);
        webshop.setHandle(handle);
        webshop.setUrl(url);
        webshop.setInterestrate(interestRate);

        webshop.store();

        return Mono.empty();
    }

    /**
     * Read by handle mono.
     *
     * @param handle the handle
     * @return the mono
     */
    @Override
    public Mono<Webshop> readByHandle(String handle) {

        ArrayList<Webshop> webshop = new ArrayList<Webshop>();
        Result<Record> webshops = getWebshops(handle, true);

        if (webshops.size() != 0) {
            webshops.forEach(consumer -> {
                webshop.add(new Webshop(consumer.getValue(com.optiply.infrastructure.data.models.tables.Webshop.WEBSHOP.WEBSHOPID), consumer.getValue(com.optiply.infrastructure.data.models.tables.Webshop.WEBSHOP.HANDLE), consumer.getValue(com.optiply.infrastructure.data.models.tables.Webshop.WEBSHOP.URL), consumer.getValue(com.optiply.infrastructure.data.models.tables.Webshop.WEBSHOP.INTERESTRATE)));
            });
        }

        return Mono.just(webshop.get(0));
    }

    /**
     * Read by handles flux.
     *
     * @param handles the handles
     * @return the flux
     */
    @Override
    public Flux<Webshop> readByHandles(String... handles) {

        ArrayList<Webshop> webshop = new ArrayList<Webshop>();

        for (String handle : handles) {
            Result<Record> webshops = getWebshops(handle, true);

            if (webshops.size() != 0) {
                webshops.forEach(consumer -> {
                    webshop.add(new Webshop(consumer.getValue(com.optiply.infrastructure.data.models.tables.Webshop.WEBSHOP.WEBSHOPID), consumer.getValue(com.optiply.infrastructure.data.models.tables.Webshop.WEBSHOP.HANDLE), consumer.getValue(com.optiply.infrastructure.data.models.tables.Webshop.WEBSHOP.URL), consumer.getValue(com.optiply.infrastructure.data.models.tables.Webshop.WEBSHOP.INTERESTRATE)));
                });
            }
        }

        return Flux.fromArray(webshop.toArray(new Webshop[0]));
    }

    /**
     * Read by handle like flux.
     *
     * @param handle the handle
     * @return the flux
     */
    @Override
    public Flux<Webshop> readByHandleLike(String handle) {

        ArrayList<Webshop> webshop = new ArrayList<Webshop>();
        Result<Record> webshops = getWebshops(handle, false);

        if (webshops.size() != 0) {
            webshops.forEach(consumer -> {
                webshop.add(new Webshop(consumer.getValue(com.optiply.infrastructure.data.models.tables.Webshop.WEBSHOP.WEBSHOPID), consumer.getValue(com.optiply.infrastructure.data.models.tables.Webshop.WEBSHOP.HANDLE), consumer.getValue(com.optiply.infrastructure.data.models.tables.Webshop.WEBSHOP.URL), consumer.getValue(com.optiply.infrastructure.data.models.tables.Webshop.WEBSHOP.INTERESTRATE)));
            });
        }

        return Flux.fromArray(webshop.toArray(new Webshop[0]));
    }

    /**
     * Update mono.
     *
     * @param id           the id
     * @param handle       the handle
     * @param url          the url
     * @param interestRate the interest rate
     * @return the mono
     */
    @Override
    public Mono<Void> update(Long id, String handle, String url, Short interestRate) {
        int result = dslContext.update(com.optiply.infrastructure.data.models.tables.Webshop.WEBSHOP).set(com.optiply.infrastructure.data.models.tables.Webshop.WEBSHOP.HANDLE, handle).set(com.optiply.infrastructure.data.models.tables.Webshop.WEBSHOP.URL, url).set(com.optiply.infrastructure.data.models.tables.Webshop.WEBSHOP.INTERESTRATE, interestRate).where(com.optiply.infrastructure.data.models.tables.Webshop.WEBSHOP.WEBSHOPID.eq(id)).execute();

        if (result != 0) {

            return Mono.empty();
        }

        return Mono.empty();
    }

    /**
     * Delete mono.
     *
     * @param id the id
     * @return the mono
     */
    @Override
    public Mono<Void> delete(Long id) {
        int result = dslContext.delete(com.optiply.infrastructure.data.models.tables.Webshop.WEBSHOP).where(com.optiply.infrastructure.data.models.tables.Webshop.WEBSHOP.WEBSHOPID.eq(id)).execute();


        if (result != 0) {

            return Mono.empty();
        }

        return Mono.empty();
    }
}
