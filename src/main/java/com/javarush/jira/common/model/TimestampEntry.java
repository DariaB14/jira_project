package com.javarush.jira.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * For entries with start/end lifecycle (used also for enable/disable)
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
public abstract class TimestampEntry extends BaseEntity {
    @CreationTimestamp
    /* Hibernate должен автоматически устанавливать в это поле текущую дату и время
    в момент первого сохранения сущности (операция INSERT).*/
    @Column(name = "startpoint", nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    //клиент не сможет передать свое значение
    protected LocalDateTime startpoint;


    @Column(name = "endpoint")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected LocalDateTime endpoint = null;

    protected TimestampEntry(Long id) {
        super(id);
    }

    protected TimestampEntry(Long id, LocalDateTime startpoint, LocalDateTime endpoint) {
        super(id);
        this.startpoint = startpoint;
        this.endpoint = endpoint;
    }

    public boolean isEnabled() {
        return endpoint == null || endpoint.isAfter(LocalDateTime.now());
    }

    public void setEnabled(boolean enable) {
        endpoint = enable ? null : LocalDateTime.now();
    }
}