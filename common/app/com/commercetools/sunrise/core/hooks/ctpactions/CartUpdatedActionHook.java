package com.commercetools.sunrise.core.hooks.ctpactions;

import com.commercetools.sunrise.core.hooks.HookRunner;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.expansion.ExpansionPathContainer;

import java.util.concurrent.CompletionStage;

public interface CartUpdatedActionHook extends CtpActionHook {

    CompletionStage<Cart> onCartUpdatedAction(final Cart cart, final ExpansionPathContainer<Cart> expansionPathContainer);

    static CompletionStage<Cart> runHook(final HookRunner hookRunner, final Cart cart, final ExpansionPathContainer<Cart> expansionPathContainer) {
        return hookRunner.run(CartUpdatedActionHook.class, cart, (hook, updatedCart) -> hook.onCartUpdatedAction(updatedCart, expansionPathContainer));
    }

}
