package ru.avdei.springcourse.entity;

import java.util.List;

public class Epic extends Task {

    private List<SubTask> subtasks;

    public Epic(String name, String description, List<SubTask> subtasks) {
        super(name, description);
        this.subtasks = subtasks;
    }

    public List<SubTask> getSubtasks() {
        return subtasks;
    }

    @Override
    public String toString() {
        return  getName() + ": " +subtasks + ": " +
                getDescription() + ": "  + getId() + ": " + getStatus();
    }

    @Override
    public void setStatus(Status status) {
        throw new UnsupportedOperationException("Установка поля запрещена в дочернем классе");
    }
}
