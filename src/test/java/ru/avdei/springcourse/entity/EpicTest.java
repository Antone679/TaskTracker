package ru.avdei.springcourse.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    private Epic epic;
    private SubTask subTask1;
    private SubTask subTask2;


    @BeforeEach
    void initSubTasks(){
        epic = new Epic("EPIC", "EpDesc", TaskType.EPIC);
    }

    @Test
    void statusCorrectWorkIfOnlyNewSubTasks() {
        subTask1 = new SubTask("First", "1stDesc", TaskType.SUBTASK, 1);
        subTask2 = new SubTask("Second", "2stDesc", TaskType.SUBTASK, 1);
        assertEquals(Status.NEW, epic.getStatus());
    }
    @Test
    void statusCorrectWorkIfEpicHasNotSubTasks() {
        assertEquals(Status.NEW, epic.getStatus());
    }

    @Test
    void statusCorrectWorkIfOnlyDoneSubTasks() {
        subTask1 = new SubTask("First", "1stDesc", TaskType.SUBTASK, 1);
        subTask2 = new SubTask("Second", "2stDesc", TaskType.SUBTASK, 1);

        epic.getSubtasks().add(subTask1);
        epic.getSubtasks().add(subTask2);
        subTask1.setStatus(Status.DONE);
        subTask2.setStatus(Status.DONE);

        assertEquals(Status.DONE, epic.getStatus());
    }

    @Test
    void statusCorrectWorkIfNewAndDoneSubTasks() {
        subTask1 = new SubTask("First", "1stDesc", TaskType.SUBTASK, 1);
        subTask2 = new SubTask("Second", "2stDesc", TaskType.SUBTASK, 1);

        epic.getSubtasks().add(subTask1);
        epic.getSubtasks().add(subTask2);
        subTask2.setStatus(Status.DONE);

        assertEquals(Status.IN_PROGRESS, epic.getStatus());
    }

    @Test
    void statusCorrectWorkIfOnlyInProgressSubTasks() {
        subTask1 = new SubTask("First", "1stDesc", TaskType.SUBTASK, 1);
        subTask2 = new SubTask("Second", "2stDesc", TaskType.SUBTASK, 1);

        epic.getSubtasks().add(subTask1);
        epic.getSubtasks().add(subTask2);
        subTask1.setStatus(Status.IN_PROGRESS);
        subTask2.setStatus(Status.IN_PROGRESS);

        assertEquals(Status.IN_PROGRESS, epic.getStatus());
    }

}