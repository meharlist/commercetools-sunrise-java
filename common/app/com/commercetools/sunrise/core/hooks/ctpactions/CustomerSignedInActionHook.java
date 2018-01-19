package com.commercetools.sunrise.core.hooks.ctpactions;

import com.commercetools.sunrise.core.hooks.HookRunner;
import io.sphere.sdk.customers.CustomerSignInResult;
import io.sphere.sdk.expansion.ExpansionPathContainer;

import javax.annotation.Nullable;
import java.util.concurrent.CompletionStage;

public interface CustomerSignedInActionHook extends CtpActionHook {

    CompletionStage<CustomerSignInResult> onCustomerSignedInAction(final CustomerSignInResult customerSignInResult, @Nullable final ExpansionPathContainer<CustomerSignInResult> expansionPathContainer);

    static CompletionStage<CustomerSignInResult> runHook(final HookRunner hookRunner, final CustomerSignInResult customerSignInResult, @Nullable final ExpansionPathContainer<CustomerSignInResult> expansionPathContainer) {
        return hookRunner.run(CustomerSignedInActionHook.class, customerSignInResult, (hook, updatedCustomerSignInResult) -> hook.onCustomerSignedInAction(updatedCustomerSignInResult, expansionPathContainer));
    }

}
