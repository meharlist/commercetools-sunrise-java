package controllers.shoppingcart;

import com.commercetools.sunrise.core.components.RegisteredComponents;
import com.commercetools.sunrise.core.controllers.cache.NoCache;
import com.commercetools.sunrise.core.controllers.metrics.LogMetrics;
import com.commercetools.sunrise.core.renderers.ContentRenderer;
import com.commercetools.sunrise.core.reverserouters.shoppingcart.cart.CartReverseRouter;
import com.commercetools.sunrise.core.reverserouters.shoppingcart.checkout.CheckoutReverseRouter;
import com.commercetools.sunrise.models.carts.CartFinder;
import com.commercetools.sunrise.models.carts.CartShippingInfoExpansionControllerComponent;
import com.commercetools.sunrise.shoppingcart.checkout.CheckoutStepControllerComponent;
import com.commercetools.sunrise.shoppingcart.checkout.shipping.CheckoutShippingControllerAction;
import com.commercetools.sunrise.shoppingcart.checkout.shipping.CheckoutShippingFormData;
import com.commercetools.sunrise.shoppingcart.checkout.shipping.ShippingSettings;
import com.commercetools.sunrise.shoppingcart.checkout.shipping.SunriseCheckoutShippingController;
import com.commercetools.sunrise.shoppingcart.checkout.shipping.viewmodels.CheckoutShippingPageContentFactory;
import io.sphere.sdk.carts.Cart;
import play.data.FormFactory;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

@LogMetrics
@NoCache
@RegisteredComponents({
        CheckoutStepControllerComponent.class,
        CartShippingInfoExpansionControllerComponent.class
})
public final class CheckoutShippingController extends SunriseCheckoutShippingController {

    private final CartReverseRouter cartReverseRouter;
    private final CheckoutReverseRouter checkoutReverseRouter;

    @Inject
    public CheckoutShippingController(final ContentRenderer contentRenderer,
                                      final FormFactory formFactory,
                                      final CheckoutShippingFormData formData,
                                      final CartFinder cartFinder,
                                      final CheckoutShippingControllerAction controllerAction,
                                      final CheckoutShippingPageContentFactory pageContentFactory,
                                      final ShippingSettings shippingSettings,
                                      final CartReverseRouter cartReverseRouter,
                                      final CheckoutReverseRouter checkoutReverseRouter) {
        super(contentRenderer, formFactory, formData, cartFinder, controllerAction, pageContentFactory, shippingSettings);
        this.cartReverseRouter = cartReverseRouter;
        this.checkoutReverseRouter = checkoutReverseRouter;
    }

    @Override
    public String getTemplateName() {
        return "checkout-shipping";
    }

    @Override
    public String getCmsPageKey() {
        return "default";
    }

    @Override
    public CompletionStage<Result> handleNotFoundCart() {
        return redirectToCall(cartReverseRouter.cartDetailPageCall());
    }

    @Override
    public CompletionStage<Result> handleSuccessfulAction(final Cart updatedCart, final CheckoutShippingFormData formData) {
        return redirectToCall(checkoutReverseRouter.checkoutPaymentPageCall());
    }
}
