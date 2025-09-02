package com.javarush.jira.ref.internal;

import com.javarush.jira.common.model.TitleEntity;
import com.javarush.jira.common.util.validation.Code;
import com.javarush.jira.ref.RefType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.lang.Nullable;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "reference",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"ref_type", "code"}, name = "uk_reference_ref_type_code")})
/* Ограничение uk_reference_ref_type_code гарантирует, что комбинация полей
(ref_type, code) будет уникальной. Это означает, что в рамках одного типа
справочника (RefType) все коды должны быть уникальными. */
@AllArgsConstructor
@EqualsAndHashCode(of = {"refType", "code"}, callSuper = false)
/* Lombok генерирует методы equals() и hashCode(), основанные только на полях
refType и code.  callSuper = false: Не использовать реализацию из суперкласса
(TitleEntity). */
public class Reference extends TitleEntity {

    @Column(name = "ref_type", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    @NotNull
    private RefType refType;

    @Column(name = "code", nullable = false)
    @Code
    private String code;

    @Column(name = "aux", columnDefinition = "varchar")
    @Nullable
    private String aux;

    public Reference(Long id, RefType refType, String code, String title) {
        super(id, title);
        this.refType = refType;
        this.code = code;
    }

    @Override
    public String toString() {
        return refType + ":" + code + '(' + title + ')';
    }
}