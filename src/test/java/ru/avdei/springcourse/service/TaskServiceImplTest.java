package ru.avdei.springcourse.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.avdei.springcourse.entity.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class TaskServiceImplTest {

    private static Task task;
    private static TaskService service;
    private static Scanner mockedScanner;

    @BeforeEach
    void init() {
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
        Task notExist = service.findById(999);
        Task loaded = service.findById(1);

        assertEquals(task, loaded);
        assertNull(notExist);
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
        when(mockedScanner.nextLine())
                .thenReturn("task")
                .thenReturn("task")
                .thenReturn("SUBTASK");

        when(mockedScanner.nextInt())
                .thenReturn(1);

        service.createTask();

        service.removeById(1);
        Task loaded = service.findById(1);
        Task loadedSub = service.findById(2);

        assertNull(loaded);
        assertNull(loadedSub);
    }

    @Test
    void updateStatus() {

        //Epic id1 status NEW

        when(mockedScanner.nextLine())
                .thenReturn("task")
                .thenReturn("task")
                .thenReturn("TASK"); //Task id2 status NEW
        service.createTask();

        when(mockedScanner.nextLine())
                .thenReturn("task")
                .thenReturn("task")
                .thenReturn("SUBTASK");

        when(mockedScanner.nextInt())
                .thenReturn(1);
        service.createTask();

        Task epic = service.findEpicById(1);
        Task standardTask = service.findById(2);
        Task subTask = service.findById(3);

        //task check
        assertEquals(Status.NEW, epic.getStatus());
        assertEquals(Status.NEW, standardTask.getStatus());
        assertEquals(Status.NEW, subTask.getStatus());

        standardTask.setStatus(Status.DONE);
        subTask.setStatus(Status.IN_PROGRESS);

        assertEquals(Status.IN_PROGRESS, epic.getStatus());
        assertEquals(Status.DONE, standardTask.getStatus());
        assertEquals(Status.IN_PROGRESS, subTask.getStatus());
    }

    @Test
    void getSubTasksOfEpicById() {
        Epic loaded = service.findEpicById(1);

        assertEquals(Collections.emptyList(),loaded.getSubtasks());

        when(mockedScanner.nextLine())
                .thenReturn("task")
                .thenReturn("task")
                .thenReturn("SUBTASK");

        when(mockedScanner.nextInt())
                .thenReturn(1);

        service.createTask();

        List<Task> exp = List.of(service.findById(2));
        assertEquals(exp, loaded.getSubtasks());
    }


}