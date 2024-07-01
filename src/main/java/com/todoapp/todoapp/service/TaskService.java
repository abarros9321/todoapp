package com.todoapp.todoapp.service;

import com.todoapp.todoapp.persistence.entity.Task;
import com.todoapp.todoapp.persistence.entity.TaskStatus;
import com.todoapp.todoapp.service.dto.TaskInDto;

import java.util.List;

public interface TaskService {
    Task createTask(TaskInDto taskInDto);
    List<Task> getAllTasks();
    void updateTaskAsFinished(Long id);
    void deleteById(Long id);
    List<Task> findAllByTaskStatus(TaskStatus taskStatus);
}
