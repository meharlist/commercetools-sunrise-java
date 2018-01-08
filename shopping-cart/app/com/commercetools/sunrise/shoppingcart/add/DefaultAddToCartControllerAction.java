package com.commercetools.sunrise.shoppingcart.add;

import com.commercetools.sunrise.models.carts.CartCreator;
import com.commercetools.sunrise.models.carts.MyCartUpdater;
import io.sphere.sdk.carts.*;
import io.sphere.sdk.carts.commands.updateactions.AddLineItem;
import play.libs.concurrent.HttpExecution;

import javax.inject.Inject;
import javax.money.CurrencyUnit;
import java.util.concurrent.CompletionStage;

import static java.util.Collections.singletonList;
import static java.util.concurrent.CompletableFuture.completedFuture;

final class DefaultAddToCartControllerAction implements AddToCartControllerAction {

    private final MyCartUpdater myCartUpdater;
    private final CartCreator cartCreator;
    private final CurrencyUnit currency;

    @Inject
    DefaultAddToCartControllerAction(final MyCartUpdater myCartUpdater, final CartCreator cartCreator, final CurrencyUnit currency) {
        this.myCartUpdater = myCartUpdater;
        this.cartCreator = cartCreator;
        this.currency = currency;
    }

    @Override
    public CompletionStage<Cart> apply(final AddToCartFormData formData) {
        return myCartUpdater.apply(formData.addLineItem())
                .thenComposeAsync(cartOpt -> cartOpt
                        .map(cart -> (CompletionStage<Cart>) completedFuture(cart))
                        .orElseGet(() -> cartCreator.get(buildCartDraft(formData))), HttpExecution.defaultContext());
    }

    private CartDraft buildCartDraft(final AddToCartFormData formData) {
        return CartDraftBuilder.of(currency)
                .lineItems(singletonList(buildLineItemDraft(formData)))
                .build();
    }

    private LineItemDraft buildLineItemDraft(final AddToCartFormData formData) {
        final AddLineItem addLineItem = formData.addLineItem();
        LineItemDraftDsl draft = LineItemDraft.of(addLineItem.getProductId(), addLineItem.getVariantId(), addLineItem.getQuantity())
                .withExternalTaxRate(addLineItem.getExternalTaxRate())
                .withCustom(addLineItem.getCustom());
        if (addLineItem.getDistributionChannel() != null) {
            draft = draft.withDistributionChannel(addLineItem.getDistributionChannel());
        }
        if (addLineItem.getSupplyChannel() != null) {
            draft = draft.withSupplyChannel(addLineItem.getSupplyChannel());
        }
        return draft;
    }
}
