package ru.avdei.springcourse.entity;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {

    private List<SubTask> subtasks;

    public Epic(String name, String description, TaskType taskType) {
        super(name, description, taskType);
        this.subtasks = new ArrayList<>();
    }

    public List<SubTask> getSubtasks() {
        return subtasks;
    }

    @Override
    public String toString() {
        return getName() + ": " + subtasks + ": " +
                getDescription() + ": " + getId() + ": " + getStatus();
    }

    public void setStatus() {
        if (subtasks.isEmpty())
            this.setStatus(Status.NEW);

        if (subtasks.stream().filter(subtask -> subtask.getStatus() == Status.DONE).count() == subtasks.size()) {
            this.setStatus(Status.DONE);
        }
        this.setStatus(Status.IN_PROGRESS);
    }

    @Override
    public Status getStatus() {

        if (subtasks.isEmpty() || subtasks.size() == subtasks.stream().filter(subTask ->
                subTask.getStatus() == Status.NEW).count())
            return Status.NEW;

        if (subtasks.stream().filter(subtask -> subtask.getStatus() == Status.DONE).count() == subtasks.size()) {
            this.setStatus(Status.DONE);
            return Status.DONE;
        }
        return Status.IN_PROGRESS;
    }
}
