package com.todoapp.todoapp.persistence.repository;

import com.todoapp.todoapp.persistence.entity.Task;
import com.todoapp.todoapp.persistence.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    public List<Task> findAllByTaskStatus(TaskStatus status);


    @Modifying
    @Query(value = "UPDATE TASK SET COMPLETED = true WHERE ID=:id", nativeQuery = true)
    public void markTaskAsCompleted(@Param("id") Long id);
}
