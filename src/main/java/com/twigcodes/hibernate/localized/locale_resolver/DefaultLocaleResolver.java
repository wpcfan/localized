package com.twigcodes.hibernate.localized.locale_resolver;

import com.twigcodes.hibernate.localized.exception.UnresolvedLocaleException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;

import java.util.Locale;

/**
 * Returns the VM's default locale.
 *
 * This is the default locale resolver.
 *
 * @see Locale#getDefault()
 * @author Victor Zhivotikov
 * @since 0.1
 */
@Slf4j
public class DefaultLocaleResolver implements LocaleResolver {

    @Setter
    private boolean warnOnce;

    @Override
    public Locale resolveLocale(Session session) throws UnresolvedLocaleException {
        if (warnOnce) {
            warnOnce = false;
            log.warn("You didn't configure a LocaleResolver for @Localized. As default the locale resolves now to the VM's locale.");
        }
        return Locale.getDefault();
    }
}
