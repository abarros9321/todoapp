package com.todoapp.todoapp.service;

import com.todoapp.todoapp.exceptions.ToDoExceptions;
import com.todoapp.todoapp.mapper.TaskInDtoToTask;
import com.todoapp.todoapp.persistence.entity.Task;
import com.todoapp.todoapp.persistence.entity.TaskStatus;
import com.todoapp.todoapp.persistence.repository.TaskRepository;
import com.todoapp.todoapp.service.dto.TaskInDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Qualifier("TaskServiceImpl")
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskInDtoToTask taskInDtoToTask;

    public TaskServiceImpl(TaskRepository taskRepository, TaskInDtoToTask taskInDtoToTask) {
        this.taskRepository = taskRepository;
        this.taskInDtoToTask = taskInDtoToTask;
    }


    public Task createTask(TaskInDto taskInDto) {
        Task task = taskInDtoToTask.map(taskInDto);
        return this.taskRepository.save(task);
    }

    public List<Task> getAllTasks() {
        return this.taskRepository.findAll();
    }

    public List<Task> findAllByTaskStatus(TaskStatus taskStatus) {
        return this.taskRepository.findAllByTaskStatus(taskStatus);
    }

    @Transactional
    public void updateTaskAsFinished(Long id) {
        if(this.taskRepository.findById(id).isEmpty()) {
            throw new ToDoExceptions("Task not found", HttpStatus.NOT_FOUND);
        }
        this.taskRepository.markTaskAsCompleted(id);
    }


    @Transactional
    public void deleteById(Long id) {
        if(this.taskRepository.findById(id).isEmpty()) {
            throw new ToDoExceptions("Task not found", HttpStatus.NOT_FOUND);
        }
        this.taskRepository.deleteById(id);
    }
}
