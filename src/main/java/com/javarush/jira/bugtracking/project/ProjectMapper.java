package com.javarush.jira.bugtracking.project;

import com.javarush.jira.bugtracking.project.to.ProjectTo;
import com.javarush.jira.common.BaseMapper;
import com.javarush.jira.common.TimestampMapper;
import org.mapstruct.Mapper;

@Mapper(config = TimestampMapper.class)
public interface ProjectMapper extends BaseMapper<Project, ProjectTo> {
}

/*
Преобразует: Project (из базы) ↔ ProjectTo (для браузера)
Для чего используется?

Списки проектов - только основные данные
Краткая информация - когда не нужны детали
 */