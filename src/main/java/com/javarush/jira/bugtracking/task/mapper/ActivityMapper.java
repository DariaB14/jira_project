package com.javarush.jira.bugtracking.task.mapper;

import com.javarush.jira.bugtracking.task.Activity;
import com.javarush.jira.bugtracking.task.to.ActivityTo;
import com.javarush.jira.common.BaseMapper;
import com.javarush.jira.common.error.DataConflictException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
// Аннотация @Mapper указывает MapStruct генерировать реализацию этого интерфейса
// componentModel - маппер будет Spring-бином, можно внедрять через @Autowired
public interface ActivityMapper extends BaseMapper<Activity, ActivityTo> {
    static long checkTaskBelong(long taskId, Activity dbActivity) {
        //Статический метод-валидатор для проверки принадлежности активности к задаче
        if (taskId != dbActivity.getTaskId())
            throw new DataConflictException("Activity " + dbActivity.id() + " doesn't belong to Task " + taskId);
        return taskId;
    }

    @Override
    @Mapping(target = "taskId", expression = "java(ActivityMapper.checkTaskBelong(activityTo.getTaskId(), activity))")
    //Аннотация @Mapping для кастомного маппинга поля taskId
    // expression = "java(...)" - выполняет Java-код во время маппинга
    Activity updateFromTo(ActivityTo activityTo, @MappingTarget Activity activity);
}

/*
MapStruct создает класс ActivityMapperImpl
При вызове updateFromTo():
Автоматически копируются все простые поля
Для taskId вызывается валидатор checkTaskBelong()
Если валидация fails - исключение, если ok - обновление
 */