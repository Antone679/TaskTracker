package ru.avdei.springcourse.entity;

import java.util.Objects;

public class Task {
    private String name;
    private String description;
    private int id;
    private Status status;
    private TaskType taskType;

    public Task(String name, String description, TaskType taskType) {
        this.name = name;
        this.description = description;
        this.taskType = taskType;
        this.status = Status.NEW;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

     public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {

        return
                "Задача: " + name + "\n" +
                "описание: " + description + "\n" +
                "ID - " + id + "\n" +
                "статус: " + status + "\n" +
                        ".".repeat(50);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Task task = (Task) object;
        return id == task.id && Objects.equals(name, task.name) && Objects.equals(description, task.description) && status == task.status && taskType == task.taskType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, id, status, taskType);
    }
}

