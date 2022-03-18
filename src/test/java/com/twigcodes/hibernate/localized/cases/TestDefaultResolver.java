package com.twigcodes.hibernate.localized.cases;

import com.twigcodes.hibernate.localized.rule.SessionRule;
import com.twigcodes.hibernate.localized.model.Book;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import com.twigcodes.hibernate.localized.locale_resolver.DefaultLocaleResolver;
import uk.org.lidalia.slf4jtest.LoggingEvent;
import uk.org.lidalia.slf4jtest.TestLogger;
import uk.org.lidalia.slf4jtest.TestLoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Victor Zhivotikov
 * @since 0.1
 */
public class TestDefaultResolver {

    private Session session;
    private final LoggingEvent expectedMessage = LoggingEvent.warn("You didn't configure a LocaleResolver for @Localized. As default the locale resolves now to the VM's locale.");

    private TestLogger logger;

    @Rule
    public final SessionRule sessionRule = new SessionRule(false);

    @Before
    public void logger() {
        logger = TestLoggerFactory.getTestLogger(DefaultLocaleResolver.class);
    }

    @After
    public void clearLogger() {
        logger.clearAll();
    }

    @Before
    public void session() {
        session = sessionRule.getSession();
    }

    @Test
    public void testWarningOnce() throws Exception {
        DefaultLocaleResolver resolver = new DefaultLocaleResolver();
        resolver.setWarnOnce(true);

        resolver.resolveLocale(session);
        assertThat(logger.getLoggingEvents()).contains(expectedMessage);
        logger.clear();

        resolver.resolveLocale(session);
        assertThat(logger.getLoggingEvents()).isEmpty();
    }

    @Test
    public void testDefaultWarning() throws Exception {
        Book book = Book.builder()
                .author("Author")
                .build();
        session.save(book);
        session.flush();

        assertThat(logger.getLoggingEvents()).contains(expectedMessage);
    }
}
