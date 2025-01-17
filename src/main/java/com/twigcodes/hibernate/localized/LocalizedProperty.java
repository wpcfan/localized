package com.twigcodes.hibernate.localized;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Locale;
import java.util.Objects;

/**
 * Entity for storing @{@link Localized} fields of an arbitrary entity.
 *
 * @author Victor Zhivotikov
 * @since 0.1
 */
@Entity
@Table(
        name = "localized_property",
        uniqueConstraints = @UniqueConstraint(columnNames = {
                "table_name", "instance", "locale", "field"
        }))
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class LocalizedProperty implements Serializable {
    private static final long serialVersionUID = -7994792168226645324L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "table_name")
    private String tableName;
    private String instance;
    private Locale locale;
    private String field;
    @Lob
    @Column(name = "field_value")
    private String value;

    @Override
    public String toString() {
        return String.format("locale=%s, id=%s, %s.%s='%s'", locale, instance, tableName, field, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        LocalizedProperty that = (LocalizedProperty) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
