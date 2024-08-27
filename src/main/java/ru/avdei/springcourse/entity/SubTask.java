package ru.avdei.springcourse.entity;

public class SubTask extends Task {

    private int epicId;

    public SubTask(String name, String description, int epicId) {
        super(name, description);
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return getName()+ ", " + getDescription() + ", " + getId() + ", " + getStatus();
    }
}
