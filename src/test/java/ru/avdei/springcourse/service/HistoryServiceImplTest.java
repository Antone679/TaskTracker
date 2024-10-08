package ru.avdei.springcourse.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.avdei.springcourse.entity.Task;
import ru.avdei.springcourse.entity.TaskType;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class HistoryServiceImplTest {

    private HistoryServiceImpl historyService;
    private Task task;

    @BeforeEach
    void init(){
        historyService = new HistoryServiceImpl();

        task = new Task("one", "desc", TaskType.TASK);
        historyService.add(task);
    }

    @Test
    void getLastSeenTasks() {
        assertNotNull(historyService.getLastSeenTasks());
    }

    @Test
    void add() {
        assertEquals(task, historyService.getLastSeenTasks().get(0));
    }

    @Test
    void getHistory() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        historyService.getHistory();
        assertTrue(outContent.toString().contains("Просмотренные недавно задачи"));
    }

    @Test
    void remove() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        historyService.remove(task);
        historyService.getHistory();
        assertTrue(outContent.toString().contains("Недавно просмотренных задач нет"));
    }

    @Test
    void clear() {
        historyService.clear();

        assertTrue(historyService.getLastSeenTasks().isEmpty());
    }
}