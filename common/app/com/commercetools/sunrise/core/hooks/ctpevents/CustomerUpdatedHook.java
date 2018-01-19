package com.commercetools.sunrise.core.hooks.ctpevents;

import com.commercetools.sunrise.core.hooks.ConsumerHook;
import com.commercetools.sunrise.core.hooks.HookRunner;
import io.sphere.sdk.customers.Customer;

@FunctionalInterface
public interface CustomerUpdatedHook extends ConsumerHook {

    void onUpdated(Customer customer);

    static void run(final HookRunner hookRunner, final Customer resource) {
        hookRunner.run(CustomerUpdatedHook.class, h -> h.onUpdated(resource));
    }
}
