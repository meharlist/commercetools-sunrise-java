package com.commercetools.sunrise.core.hooks;

import com.commercetools.sunrise.core.hooks.application.HttpRequestEndedHook;
import com.commercetools.sunrise.core.hooks.application.HttpRequestStartedHook;
import play.inject.Injector;
import play.libs.concurrent.HttpExecution;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

/**
 * Action that runs the {@link HttpRequestStartedHook} and {@link HttpRequestEndedHook} and waits for all hooks to finish for the annotated request.
 */
final class HooksEnabler extends Action<EnableHooks> {

    private final Injector injector;

    @Inject
    HooksEnabler(final Injector injector) {
        this.injector = injector;
    }

    @Override
    public CompletionStage<Result> call(final Http.Context ctx) {
        // On creation of this action there isn't any HTTP context, necessary to initialize the HookRunner
        final HookContext hookRunner = injector.instanceOf(HookContext.class);
        HttpRequestStartedHook.runHook(hookRunner, ctx);
        return delegate.call(ctx)
                .thenCompose(result -> hookRunner.waitForHookedComponentsToFinish()
                        .thenApplyAsync(unused -> HttpRequestEndedHook.runHook(hookRunner, result), HttpExecution.defaultContext()));
    }
}
