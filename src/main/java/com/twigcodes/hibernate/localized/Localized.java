package com.twigcodes.hibernate.localized;

import com.twigcodes.hibernate.localized.locale_resolver.LocaleResolver;

import java.lang.annotation.*;

/**
 * Indicates fields which have locale dependent values based on {@link LocaleResolver#resolveLocale(org.hibernate.Session)}.
 *
 * @see LocalizedIntegrator
 * @see LocaleResolver#resolveLocale(org.hibernate.Session)
 * @author Victor Zhivotikov
 * @since 0.1
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Localized {
}
