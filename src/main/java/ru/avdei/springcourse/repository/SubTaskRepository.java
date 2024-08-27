package ru.avdei.springcourse.repository;

import ru.avdei.springcourse.entity.Status;
import ru.avdei.springcourse.entity.SubTask;
import ru.avdei.springcourse.entity.Task;

import java.util.ArrayList;
import java.util.List;

public class SubTaskRepository implements Repository {
    private List<SubTask> subTasks;

    public SubTaskRepository() {
        this.subTasks = new ArrayList<>();
    }

    @Override
    public List<Task> getAllTasks() {
        return null;
    }

    @Override
    public void addTask(Task task) {

    }

    @Override
    public void deleteAllTasks() {

    }

    @Override
    public Task findById(int id) {
        return null;
    }

    @Override
    public void update(Task task) {

    }

    @Override
    public void removeById(int id) {

    }

    @Override
    public void updateTaskStatus(int id, Status status) {

    }
}
