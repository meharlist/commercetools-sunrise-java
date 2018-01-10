package com.commercetools.sunrise.wishlist.remove;

import com.commercetools.sunrise.core.controllers.ControllerAction;
import com.google.inject.ImplementedBy;
import io.sphere.sdk.shoppinglists.ShoppingList;

import java.util.concurrent.CompletionStage;

@FunctionalInterface
@ImplementedBy(DefaultRemoveFromWishlistControllerAction.class)
public interface RemoveFromWishlistControllerAction extends ControllerAction {

    CompletionStage<ShoppingList> apply(RemoveFromWishlistFormData removeWishlistFormData);
}
