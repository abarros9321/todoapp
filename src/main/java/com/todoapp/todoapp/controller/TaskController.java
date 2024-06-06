package com.todoapp.todoapp.controller;

import com.todoapp.todoapp.persistence.entity.Task;
import com.todoapp.todoapp.persistence.entity.TaskStatus;
import com.todoapp.todoapp.service.TaskService;
import com.todoapp.todoapp.service.dto.TaskInDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public Task createTask(@RequestBody TaskInDto taskInDto) {
       return this.taskService.createTask(taskInDto);
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return this.taskService.getAllTasks();
    }

    @GetMapping("/status/{status}")
    public List<Task> getTasksByStatus(@PathVariable TaskStatus status) {
        return this.taskService.findAllByTaskStatus(status);
    }

    @PatchMapping("/mark_as_finished/{id}")
    public ResponseEntity<Void> markAsFinished(@PathVariable("id") Long id){
        this.taskService.updateTaskAsFinished(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable("id") Long id){
        this.taskService.DeleteById(id);
        return ResponseEntity.noContent().build();
    }

}
