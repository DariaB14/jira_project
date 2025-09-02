package com.javarush.jira.bugtracking.attachment;

import com.javarush.jira.bugtracking.attachment.to.AttachmentTo;
import com.javarush.jira.common.BaseMapper;
import com.javarush.jira.common.TimestampMapper;
import org.mapstruct.Mapper;

@Mapper(config = TimestampMapper.class)
//Создай реальный класс на основе этого интерфейса"
//Используй настройки для дат и времени"
public interface AttachmentMapper extends BaseMapper<Attachment, AttachmentTo> {
}

//Это "преобразователь" (маппер), который превращает:
//Вложение из базы данных (Attachment) → Вложение для браузера (AttachmentTo)
//Вложение из браузера (AttachmentTo) → Вложение для базы данных (Attachment)