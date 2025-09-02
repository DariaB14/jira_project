package com.javarush.jira.bugtracking.task.mapper;

import com.javarush.jira.bugtracking.project.ProjectMapper;
import com.javarush.jira.bugtracking.sprint.SprintMapper;
import com.javarush.jira.bugtracking.task.Task;
import com.javarush.jira.bugtracking.task.to.TaskToExt;
import com.javarush.jira.common.BaseMapper;
import com.javarush.jira.common.TimestampMapper;
import com.javarush.jira.common.error.DataConflictException;
import com.javarush.jira.login.AuthUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = TimestampMapper.class, uses = {SprintMapper.class, ProjectMapper.class})
// 1. Наследуем конфигурацию временных меток из TimestampMapper
// Используем другие мапперы для сложных полей
public interface TaskExtMapper extends BaseMapper<Task, TaskToExt> {

    static long checkProjectBelong(long projectId, Task dbTask) {
        if (projectId != dbTask.getProjectId())
            throw new DataConflictException("Task " + dbTask.id() + " doesn't belong to Project " + projectId);
        return projectId;
    }

    static long checkUserAuthorities(long sprintId, Task dbTask) {
        if (sprintId != dbTask.getSprintId() && !(AuthUser.get().isAdmin() || AuthUser.get().isManager()))
            throw new DataConflictException("Do not have authorities to change task's sprint");
        return sprintId;
    }

    @Override
    TaskToExt toTo(Task task);

    @Override
    @Mapping(target = "projectId", expression = "java(TaskExtMapper.checkProjectBelong(taskToExt.getProjectId(), task))")
    @Mapping(target = "sprintId", expression = "java(TaskExtMapper.checkUserAuthorities(taskToExt.getSprintId(), task))")
        // 7. Двойная валидация при обновлении:
        // - Принадлежность проекту
        // - Права на изменение спринта
    Task updateFromTo(TaskToExt taskToExt, @MappingTarget Task task);
}

/*
Автоматический маппинг сложных полей через uses = {SprintMapper.class, ProjectMapper.class}
Двойная защита при обновлении задачи
Только админы/менеджеры могут менять спринт задачи
 */