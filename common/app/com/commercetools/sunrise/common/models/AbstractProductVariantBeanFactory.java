package com.commercetools.sunrise.common.models;

import io.sphere.sdk.products.Image;
import io.sphere.sdk.products.ProductVariant;

import java.util.Optional;

abstract class AbstractProductVariantBeanFactory<D> extends ViewModelFactory<ProductVariantBean, D> {

    protected AbstractProductVariantBeanFactory() {
    }

    @Override
    protected ProductVariantBean getViewModelInstance() {
        return new ProductVariantBean();
    }

    @Override
    protected void initialize(final ProductVariantBean model, final D data) {
        fillSku(model, data);
        fillName(model, data);
        fillUrl(model, data);
        fillImage(model, data);
        fillPrice(model, data);
        fillPriceOld(model, data);
    }

    protected abstract void fillSku(final ProductVariantBean model, final D data);

    protected abstract void fillName(final ProductVariantBean model, final D data);

    protected abstract void fillUrl(final ProductVariantBean model, final D data);

    protected abstract void fillImage(final ProductVariantBean model, final D data);

    protected abstract void fillPrice(final ProductVariantBean model, final D data);

    protected abstract void fillPriceOld(final ProductVariantBean model, final D data);

    protected String createSku(final ProductVariant variant) {
        return variant.getSku();
    }

    protected Optional<String> createImageUrl(final ProductVariant variant) {
        return variant.getImages().stream()
                .findFirst()
                .map(Image::getUrl);
    }
}