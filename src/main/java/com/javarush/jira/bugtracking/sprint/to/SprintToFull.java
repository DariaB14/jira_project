package com.javarush.jira.bugtracking.sprint.to;

import com.javarush.jira.common.to.CodeTo;
import lombok.Value;

@Value
public class SprintToFull extends SprintTo {
    //ключая объект проекта (а не just его ID). Это пример обогащенного DTO
    // для более удобной работы клиента.
    CodeTo project;

    public SprintToFull(Long id, String code, String statusCode, CodeTo project) {
        super(id, code, statusCode, project.getId());
        this.project = project;
    }
}
