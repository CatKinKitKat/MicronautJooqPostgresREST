package com.optiply.infrastructure.data.repositories;

import com.optiply.infrastructure.data.models.Tables;
import com.optiply.infrastructure.data.models.tables.pojos.Webshop;
import com.optiply.infrastructure.data.models.tables.records.WebshopRecord;
import com.optiply.infrastructure.data.repositories.interfaces.IWebshopRepository;
import io.micronaut.data.r2dbc.operations.R2dbcOperations;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.jooq.DSLContext;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

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
    private List<Webshop> getWebshops(String handle, Boolean exact) {

        if (exact) {
            return dslContext.select()
                    .from(Tables.WEBSHOP)
                    .where(Tables.WEBSHOP.HANDLE.eq(handle))
                    .fetchInto(Webshop.class);

        }

        return dslContext.select()
                .from(Tables.WEBSHOP)
                .where(Tables.WEBSHOP.HANDLE.like("%" + handle + "%"))
                .fetchInto(Webshop.class);
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
        WebshopRecord webshop = dslContext.newRecord(Tables.WEBSHOP);
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

        List<Webshop> webshops = getWebshops(handle, true);

        if (webshops.size() > 0) {
            return Mono.just(webshops.get(0));
        }

        return Mono.empty();
    }

    /**
     * Read by handles flux.
     *
     * @param handles the handles
     * @return the flux
     */
    @Override
    public Flux<Webshop> readByHandles(String... handles) {


        for (String handle : handles) {
            List<Webshop> webshops = getWebshops(handle, true);

            if (webshops.size() > 0) {
                return Flux.fromIterable(webshops);
            }
        }

        return Flux.empty();
    }

    /**
     * Read by handle like flux.
     *
     * @param handle the handle
     * @return the flux
     */
    @Override
    public Flux<Webshop> readByHandleLike(String handle) {

        List<Webshop> webshops = getWebshops(handle, false);

        if (webshops.size() > 0) {
            return Flux.fromIterable(webshops);
        }

        return Flux.empty();
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
        int result = dslContext
                .update(Tables.WEBSHOP)
                .set(Tables.WEBSHOP.HANDLE, handle)
                .set(Tables.WEBSHOP.URL, url)
                .set(Tables.WEBSHOP.INTERESTRATE, interestRate)
                .where(Tables.WEBSHOP.WEBSHOPID.eq(id))
                .execute();

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
        int result = dslContext
                .delete(Tables.WEBSHOP)
                .where(Tables.WEBSHOP.WEBSHOPID.eq(id))
                .execute();


        if (result != 0) {

            return Mono.empty();
        }

        return Mono.empty();
    }
}
