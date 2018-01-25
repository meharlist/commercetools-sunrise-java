package com.commercetools.sunrise.models.carts;

import com.commercetools.sunrise.core.hooks.HookRunner;
import com.commercetools.sunrise.models.customers.MyCustomerInSession;
import io.sphere.sdk.carts.CartState;
import io.sphere.sdk.carts.queries.CartQuery;
import io.sphere.sdk.client.SphereClient;

import javax.inject.Inject;
import java.util.Optional;

public final class DefaultMyCartFetcher extends AbstractMyCartFetcher {

    private final MyCartInSession myCartInSession;
    private final MyCustomerInSession myCustomerInSession;

    @Inject
    DefaultMyCartFetcher(final HookRunner hookRunner, final SphereClient sphereClient,
                         final MyCartInSession myCartInSession, final MyCustomerInSession myCustomerInSession) {
        super(hookRunner, sphereClient);
        this.myCartInSession = myCartInSession;
        this.myCustomerInSession = myCustomerInSession;
    }

    @Override
    protected Optional<CartQuery> buildRequest() {
        return tryBuildQueryByCustomerId()
                .map(Optional::of)
                .orElseGet(this::tryBuildQueryByCartId)
                .map(this::decorateQueryWithAdditionalInfo);
    }

    private Optional<CartQuery> tryBuildQueryByCustomerId() {
        return myCustomerInSession.findId()
                .map(customerId -> CartQuery.of().plusPredicates(cart -> cart.customerId().is(customerId)));
    }

    private Optional<CartQuery> tryBuildQueryByCartId() {
        return myCartInSession.findId()
                .map(cartId -> CartQuery.of().plusPredicates(cart -> cart.id().is(cartId)));
    }

    private CartQuery decorateQueryWithAdditionalInfo(final CartQuery query) {
        return query
                .plusPredicates(cart -> cart.cartState().is(CartState.ACTIVE))
                .withSort(cart -> cart.lastModifiedAt().sort().desc())
                .withLimit(1);
    }
}
