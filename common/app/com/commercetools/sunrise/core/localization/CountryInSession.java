package com.commercetools.sunrise.core.localization;

import com.commercetools.sunrise.core.sessions.StoringOperations;
import com.google.inject.ImplementedBy;
import com.neovisionaries.i18n.CountryCode;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Keeps the current country in session.
 */
@ImplementedBy(CountryInSessionImpl.class)
public interface CountryInSession extends StoringOperations<CountryCode> {

    Optional<CountryCode> findCountry();

    @Override
    void store(@Nullable final CountryCode countryCode);

    @Override
    void remove();
}
