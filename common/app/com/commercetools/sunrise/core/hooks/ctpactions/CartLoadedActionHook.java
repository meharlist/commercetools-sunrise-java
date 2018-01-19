package com.commercetools.sunrise.core.hooks.ctpactions;

import com.commercetools.sunrise.core.hooks.HookRunner;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.expansion.ExpansionPathContainer;

import java.util.concurrent.CompletionStage;

public interface CartLoadedActionHook extends CtpActionHook {

    CompletionStage<Cart> onCartLoadedAction(final Cart cart, final ExpansionPathContainer<Cart> expansionPathContainer);

    static CompletionStage<Cart> runHook(final HookRunner hookRunner, final Cart cart, final ExpansionPathContainer<Cart> expansionPathContainer) {
        return hookRunner.run(CartLoadedActionHook.class, cart, (hook, updatedCart) -> hook.onCartLoadedAction(updatedCart, expansionPathContainer));
    }

}
