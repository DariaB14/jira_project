package com.javarush.jira.common;

import com.javarush.jira.common.to.BaseTo;
import org.mapstruct.MappingTarget;

import java.util.Collection;
import java.util.List;

public interface BaseMapper<E, T extends BaseTo> {
    // 1. Преобразование DTO → Entity
    E toEntity(T to);

    // 2. Преобразование коллекции DTO → коллекция Entity
    List<E> toEntityList(Collection<T> tos);

    // 3. Обновление существующей Entity из DTO
    E updateFromTo(T to, @MappingTarget E entity);

    // 4. Преобразование Entity → DTO
    T toTo(E entity);

    // 5. Преобразование коллекции Entity → коллекция DTO
    List<T> toToList(Collection<E> entities);
}
