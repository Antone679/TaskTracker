package ru.avdei.springcourse.entity;

public class Task {
    private String Name;
    private String Description;
    private int id;
    private Status status;

    public Task(String name, String description) {
        Name = name;
        Description = description;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
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
                "Name='" + Name + '\'' +
                ", Description='" + Description + '\'' +
                ", id=" + id +
                ", status=" + status;
    }
}
