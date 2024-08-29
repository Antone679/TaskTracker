package ru.avdei.springcourse.entity;

public class SubTask extends Task {

    private int epicId;

    public SubTask(String name, String description, TaskType taskType, int epicId) {
        super(name, description, taskType);
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return getName()+ ", " + getDescription() + ", " + getId() + ", " + getStatus();
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

}
