package com.todoapp.todoapp.mapper;

import com.todoapp.todoapp.persistence.entity.Task;
import com.todoapp.todoapp.persistence.entity.TaskStatus;
import com.todoapp.todoapp.service.dto.TaskInDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TaskInDtoToTask implements IMapper<TaskInDto, Task> {
    @Override
    public Task map(TaskInDto in) {
        Task task = new Task();
        task.setTitle(in.getTitle());
        task.setDescription(in.getDescription());
        task.setEta(in.getEta());

        task.setCreatedDate(LocalDateTime.now());
        task.setCompleted(false);
        task.setTaskStatus(TaskStatus.ON_TIME);
        return task;
    }
}
