package ru.avdei.springcourse.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.avdei.springcourse.entity.Epic;
import ru.avdei.springcourse.entity.Task;
import ru.avdei.springcourse.entity.TaskType;

import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class TaskServiceImplTest {

    private static Task task;
    private static TaskService service;
    private static Scanner mockedScanner;

    @BeforeEach
    void init(){
       task = new Epic("epic", "epic", TaskType.EPIC);
       task.setId(1);
        mockedScanner = mock(Scanner.class);
        TaskServiceImpl.setScanner(mockedScanner);
        service = new TaskServiceImpl();

        when(mockedScanner.nextLine())
                .thenReturn("epic")
                .thenReturn("epic")
                .thenReturn("EPIC");

        service.createTask();
    }

    @Test
    void getTasks() {
        Map<Integer, Task> exp = Map.of(task.getId(), task);
        Map<Integer, Task> act = service.getTasks();

        assertEquals(exp, act);

    }

    @Test
    void createTask() {
        Task savedTask = service.findById(1);

        assertNotNull(savedTask);
        assertEquals(1, service.getTasks().size());
        assertEquals(task, savedTask);
    }

    @Test
    void deleteAllTasks() {
        service.deleteAllTasks();

        assertEquals(0, service.getTasks().size());
    }

    @Test
    void findById() {
        Task task = service.findById(999);

        assertNull(task);
    }

    @Test
    void findEpicById() {
        Task savedTask = service.findEpicById(1);

        assertNotNull(savedTask);
        assertEquals(1, service.getTasks().size());
        assertEquals(task, savedTask);
    }

    @Test
    void updateTask() {
        when(mockedScanner.nextLine())
                .thenReturn("task")
                .thenReturn("task")
                .thenReturn("TASK")
                .thenReturn("1");

        service.createTask();

        System.out.println(service.getTasks().size());

    }

    @Test
    void removeById() {

Task notExist = service.findById(999);


    }

    @Test
    void updateStatus() {
    }

    @Test
    void getSubTasksOfEpicById() {
    }


}