package com.twigcodes.hibernate.localized.cases;

import com.twigcodes.hibernate.localized.locale_resolver.ThreadLocalLocaleResolver;
import com.twigcodes.hibernate.localized.model.Book;
import com.twigcodes.hibernate.localized.rule.SessionRule;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Victor Zhivotikov
 * @since 0.1
 */
public class TestInsert {

    private Session session;
    private ThreadLocalLocaleResolver localeResolver;

    @Rule
    public final SessionRule sessionRule = new SessionRule();

    @Before
    public void session() {
        session = sessionRule.getSession();
    }

    @Before
    public void localizedConfiguration() {
        localeResolver = sessionRule.getLocaleResolver();
        localeResolver.setLocale(Locale.US);
    }

    @Test
    public void testInsertNull() throws Exception {
        Book book = Book.builder()
                .author("Author")
                .build();
        session.save(book);
        session.flush();

        session.refresh(book);
        assertThat(book.getTitle()).isNull();
    }

    @Test
    public void testInsert() throws Exception {
        Book book = Book.builder()
                .author("Author")
                .title("Title")
                .build();
        session.save(book);
        session.flush();

        session.refresh(book);
        assertThat(book.getTitle()).isEqualTo("Title");
    }
}
