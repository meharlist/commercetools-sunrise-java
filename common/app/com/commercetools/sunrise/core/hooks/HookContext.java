package com.commercetools.sunrise.core.hooks;

import com.google.inject.ImplementedBy;

import java.util.concurrent.CompletionStage;
import java.util.function.Function;

@ImplementedBy(HookContextImpl.class)
public interface HookContext extends HookRunner, ComponentRegistry {

    /**
     * Waits for all the asynchronous executions from implemented hooks to finish.
     * @return an unused value
     * @see HookRunner#runEventHook(Class, Function)
     */
    CompletionStage<?> waitForHookedComponentsToFinish();
}
