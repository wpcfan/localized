package com.twigcodes.hibernate.localized.cases;

import com.twigcodes.hibernate.localized.LocalizedProperty;
import com.twigcodes.hibernate.localized.locale_resolver.ThreadLocalLocaleResolver;
import com.twigcodes.hibernate.localized.model.Book;
import com.twigcodes.hibernate.localized.repository.LocalizedSessionRepository;
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
public class TestDelete {

    private Book book;
    private Session session;
    private LocalizedSessionRepository repository;
    private ThreadLocalLocaleResolver localeResolver;

    @Rule
    public final SessionRule sessionRule = new SessionRule();

    @Before
    public void before() {
        session = sessionRule.getSession();
        repository = sessionRule.getRepository();
        localeResolver = sessionRule.getLocaleResolver();
        localeResolver.setLocale(Locale.US);

        book = Book.builder()
                .author("Author")
                .title("Title US")
                .build();
        session.save(book);
        session.flush();

        localeResolver.setLocale(Locale.ENGLISH);
        book.setTitle("Title EN");
        session.save(book);
        session.flush();

        localeResolver.setLocale(Locale.US);
    }

    @Test
    public void testDelete() throws Exception {
        System.out.println("Test begin");
        session.delete(book);
        session.flush();

        // Fall back to EN, so not null
        LocalizedProperty property = repository.find(SessionRule.TABLE_NAME, "title",
                localeResolver.resolveLocale(session), book.getId());
        assertThat(property).isNotNull();
        assertThat(property.getValue()).isEqualTo("Title EN");

        localeResolver.setLocale(Locale.ENGLISH);
        property = repository.find(SessionRule.TABLE_NAME, "title", localeResolver.resolveLocale(session), book.getId());
        assertThat(property).isNotNull();
    }
}


