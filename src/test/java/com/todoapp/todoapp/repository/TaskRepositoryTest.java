package com.todoapp.todoapp.repository;

import com.todoapp.todoapp.persistence.entity.Task;
import com.todoapp.todoapp.persistence.entity.TaskStatus;
import com.todoapp.todoapp.persistence.repository.TaskRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository repository;

    private Task task;


    @BeforeEach
    void setUp() {
        task = new Task();
        task.setTitle("tarea1");
        task.setDescription("desc1");
        task.setEta(LocalDateTime.now());
        task.setCreatedDate(LocalDateTime.now());
        task.setCompleted(false);
        task.setTaskStatus(TaskStatus.ON_TIME);
    }

    @DisplayName("Test para guardar una task")
    @Test
    void testGuardarTask() {
        //given - configuracion


        //when - comportamiento
        Task saved = repository.save(task);
        //then - verificacion
        Assertions.assertThat(saved).isNotNull();
        Assertions.assertThat(saved.getId()).isGreaterThan(0);
        Assertions.assertThat(saved.getTitle()).isEqualTo(task.getTitle());
        Assertions.assertThat(saved.getDescription()).isEqualTo(task.getDescription());
    }

    @DisplayName("Test para listar tareas")
    @Test
    void testListarTasks() {
        //given
        Task task1 = Task.builder()
                .title("tarea2")
                .description("desc2")
                .eta(LocalDateTime.now())
                .createdDate(LocalDateTime.now())
                .completed(false)
                .taskStatus(TaskStatus.ON_TIME)
                .build();

        repository.save(task1);
        repository.save(task);
        //when
        List<Task> tasks = repository.findAll();
        //then
        Assertions.assertThat(tasks).isNotNull();
        Assertions.assertThat(tasks.size()).isEqualTo(2);
    }

    @DisplayName("Test para obtener tarea por id")
    @Test
    void testListarTaskPorId() {
        //given
        repository.save(task);
        //when
        Task taskSave = repository.findById(task.getId()).orElse(null);
        //then
        Assertions.assertThat(taskSave).isNotNull();
        Assertions.assertThat(taskSave.getTitle()).isEqualTo(task.getTitle());
    }

    @DisplayName("Test para modificar una tarea")
    @Test
    void testUpddateTask() {
        //given
        repository.save(task);
        //when
        Task taskActualizar = repository.findById(task.getId()).orElse(null);
        assert taskActualizar != null;
        taskActualizar.setTitle("TITLE2");
        taskActualizar.setDescription("desc2");

        Task taskActualizado = repository.save(taskActualizar);
        //then
        Assertions.assertThat(taskActualizado).isNotNull();
        Assertions.assertThat(taskActualizado.getTitle()).isEqualTo("TITLE2");
        Assertions.assertThat(taskActualizado.getDescription()).isEqualTo("desc2");
    }

    @DisplayName("Test para eliminar una task")
    @Test
    void testDeleteTask() {
        //given
        repository.save(task);
        //when
        repository.delete(task);
        Optional<Task> taskBorrada = repository.findById(task.getId());
        //then
        Assertions.assertThat(taskBorrada).isEmpty();

    }
}
