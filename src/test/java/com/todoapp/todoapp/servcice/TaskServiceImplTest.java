package com.todoapp.todoapp.servcice;

import com.todoapp.todoapp.exceptions.ToDoExceptions;
import com.todoapp.todoapp.mapper.TaskInDtoToTask;
import com.todoapp.todoapp.persistence.entity.Task;
import com.todoapp.todoapp.persistence.entity.TaskStatus;
import com.todoapp.todoapp.persistence.repository.TaskRepository;
import com.todoapp.todoapp.service.TaskServiceImpl;
import com.todoapp.todoapp.service.dto.TaskInDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepositoryMock;
    @Mock
    private TaskInDtoToTask taskInDtoToTaskMock;

    @InjectMocks
    private TaskServiceImpl taskService;

    private Task task;
    private TaskInDto taskInDto;

    @BeforeEach
    void setUp() {
        task = Task.builder()
                .id(1L)
                .title("title1")
                .description("desc1")
                .eta(LocalDateTime.now())
                .completed(false)
                .createdDate(LocalDateTime.now())
                .taskStatus(TaskStatus.ON_TIME)
                .build();
        taskInDto = TaskInDto.builder()
                .title("title1")
                .description("desc1")
                .eta(LocalDateTime.now())
                .build();
    }

    @DisplayName("Test para guardar una task")
    @Test
    public void guardarTask() {
        //given
        BDDMockito.given(taskRepositoryMock.save(task)).willReturn(task);
        BDDMockito.given(taskInDtoToTaskMock.map(taskInDto)).willReturn(task);
        //when
        Task taskGuardado = taskService.createTask(taskInDto);
        //then
        Assertions.assertThat(taskGuardado).isNotNull();
        Assertions.assertThat(taskGuardado.getTitle()).isEqualTo("title1");
    }

    @DisplayName("Test para listar task")
    @Test
    public void testListarTask() {
        //given
        Task task1 = Task.builder()
                .title("title2")
                .description("desc2")
                .eta(LocalDateTime.now())
                .build();
        BDDMockito.given(taskRepositoryMock.findAll()).willReturn(List.of(task, task1));
        //when
        List<Task> tasks = taskService.getAllTasks();
        //then
        Assertions.assertThat(tasks).isNotNull();
        Assertions.assertThat(tasks).hasSize(2);
    }

    @Test
    void testListarTaskListaVacia() {
        //given
        BDDMockito.given(taskRepositoryMock.findAll()).willReturn(Collections.EMPTY_LIST);
        //when
        List<Task> tasks = taskService.getAllTasks();
        //then
        Assertions.assertThat(tasks).isNotNull();
        Assertions.assertThat(tasks).isEmpty();
    }

    @Test
    public void testObtenerTaskPorStatus() {
        //given
        Task task1 = Task.builder()
                .title("title2")
                .description("desc2")
                .eta(LocalDateTime.now())
                .taskStatus(TaskStatus.ON_TIME)
                .build();
        BDDMockito.given(taskRepositoryMock.findAllByTaskStatus(task.getTaskStatus())).willReturn(List.of(task, task1));
        //when
        List<Task> tasks = taskService.findAllByTaskStatus(task.getTaskStatus());
        //then
        Assertions.assertThat(tasks).isNotNull();
        Assertions.assertThat(tasks).hasSize(2);
    }

    @Test
    public void actualizarTaskAsFinished() {
        //given
        BDDMockito.given(taskRepositoryMock.findById(task.getId())).willReturn(Optional.ofNullable(task));
        task.setCompleted(true);

        //when
        taskService.updateTaskAsFinished(task.getId());
        //then
        Assertions.assertThat(task).isNotNull();
        Assertions.assertThat(task.isCompleted()).isEqualTo(true);
    }

    @Test
    public void deleteTask() {
        //given
        BDDMockito.given(taskRepositoryMock.findById(task.getId())).willReturn(Optional.ofNullable(task));
        willDoNothing().given(taskRepositoryMock).deleteById(task.getId());
        //when
        taskService.deleteById(1L);
        //then
        verify(taskRepositoryMock, Mockito.times(1)).deleteById(task.getId());
    }
}
