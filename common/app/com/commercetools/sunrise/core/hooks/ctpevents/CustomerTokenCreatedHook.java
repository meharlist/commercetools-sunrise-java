package com.commercetools.sunrise.core.hooks.ctpevents;

import com.commercetools.sunrise.core.hooks.HookRunner;
import io.sphere.sdk.customers.CustomerToken;

import java.util.concurrent.CompletionStage;

/**
 * This hook is called after a customer token was created.
 */
public interface CustomerTokenCreatedHook extends CtpEventHook {

    void onCustomerTokenCreated(CustomerToken customerToken);

    static void runHook(final HookRunner hookRunner, final CustomerToken customerToken) {
        hookRunner.runEventHook(CustomerTokenCreatedHook.class, hook -> hook.onCustomerTokenCreated(customerToken));
    }
}
