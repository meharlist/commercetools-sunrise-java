package controllers.wishlist;

import com.commercetools.sunrise.core.controllers.cache.NoCache;
import com.commercetools.sunrise.core.controllers.metrics.LogMetrics;
import com.commercetools.sunrise.core.reverserouters.wishlist.WishlistReverseRouter;
import com.commercetools.sunrise.models.shoppinglists.MyWishlistFetcher;
import com.commercetools.sunrise.wishlist.clear.ClearWishlistControllerAction;
import com.commercetools.sunrise.wishlist.clear.SunriseClearWishlistController;
import com.google.inject.Inject;
import io.sphere.sdk.shoppinglists.ShoppingList;
import play.mvc.Result;

import java.util.concurrent.CompletionStage;

@LogMetrics
@NoCache
public final class ClearWishlistController extends SunriseClearWishlistController {

    private final WishlistReverseRouter reverseRouter;

    @Inject
    public ClearWishlistController(final MyWishlistFetcher wishlistFinder,
                                   final ClearWishlistControllerAction controllerAction,
                                   final WishlistReverseRouter reverseRouter) {
        super(wishlistFinder, controllerAction);
        this.reverseRouter = reverseRouter;
    }

    @Override
    public CompletionStage<Result> handleSuccessfulAction(final ShoppingList wishlist) {
        return redirectToCall(reverseRouter.wishlistPageCall());
    }

    @Override
    public CompletionStage<Result> handleNotFoundWishlist() {
        return redirectToCall(reverseRouter.wishlistPageCall());
    }
}
