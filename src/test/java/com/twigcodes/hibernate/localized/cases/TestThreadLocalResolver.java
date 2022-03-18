package com.twigcodes.hibernate.localized.cases;

import com.twigcodes.hibernate.localized.exception.UnresolvedLocaleException;
import org.hibernate.Session;
import org.junit.Test;
import com.twigcodes.hibernate.localized.locale_resolver.ThreadLocalLocaleResolver;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Victor Zhivotikov
 * @since 0.1
 */
public class TestThreadLocalResolver {

    private Session session;

    @Test(expected = UnresolvedLocaleException.class)
    public void testUnsetLocale() throws Exception {
        ThreadLocalLocaleResolver resolver = new ThreadLocalLocaleResolver();
        resolver.resolveLocale(session);
    }

    @Test
    public void testChangeLocale() throws Exception {
        ThreadLocalLocaleResolver resolver = new ThreadLocalLocaleResolver();

        resolver.setLocale(Locale.ENGLISH);
        assertThat(resolver.resolveLocale(session)).isEqualTo(Locale.ENGLISH);

        resolver.setLocale(Locale.GERMAN);
        assertThat(resolver.resolveLocale(session)).isEqualTo(Locale.GERMAN);
    }
}
