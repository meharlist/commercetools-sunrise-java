package com.commercetools.sunrise.models.shoppinglists;

import com.commercetools.sunrise.core.components.ControllerComponent;
import com.commercetools.sunrise.core.hooks.ctprequests.ShoppingListQueryHook;
import com.commercetools.sunrise.core.hooks.ctprequests.ShoppingListUpdateCommandHook;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.shoppinglists.ShoppingList;
import io.sphere.sdk.shoppinglists.commands.ShoppingListUpdateCommand;
import io.sphere.sdk.shoppinglists.expansion.ShoppingListExpansionModel;
import io.sphere.sdk.shoppinglists.queries.ShoppingListQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.completedFuture;

public final class ShoppingListLineItemExpansionComponent implements ControllerComponent, ShoppingListQueryHook, ShoppingListUpdateCommandHook {

    @Override
    public CompletionStage<ShoppingListQuery> onShoppingListQuery(final ShoppingListQuery query) {
        return completedFuture(query.plusExpansionPaths(expansionPaths()));
    }

    @Override
    public CompletionStage<ShoppingListUpdateCommand> onCartUpdateCommand(final ShoppingListUpdateCommand command) {
        return completedFuture(command.plusExpansionPaths(expansionPaths()));
    }

    private List<ExpansionPath<ShoppingList>> expansionPaths() {
        final List<ExpansionPath<ShoppingList>> expansionPaths = new ArrayList<>();
        expansionPaths.addAll(ShoppingListExpansionModel.of().lineItems().productSlug().expansionPaths());
        expansionPaths.addAll(ShoppingListExpansionModel.of().lineItems().variant().expansionPaths());
        return expansionPaths;
    }
}
