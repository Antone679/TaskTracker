package ru.avdei.springcourse.repository;

import ru.avdei.springcourse.entity.Epic;
import ru.avdei.springcourse.entity.Status;
import ru.avdei.springcourse.entity.SubTask;
import ru.avdei.springcourse.entity.Task;

import java.util.List;

public interface Repository {
    public List<Task> getAllTasks();

    public void addTask(Task task);

    public void deleteAllTasks();

    public Task findById(int id);

    public void update(Task task);

    public void removeById(int id);

    public void updateTaskStatus(int id, Status status);
}
