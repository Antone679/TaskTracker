package ru.avdei.springcourse.entity;

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
        if (name.equals("ZERO"))
            return "Задач нет.";

        return
                "Name='" + name + '\'' +
                ", Description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status;
    }
}
