package ru.avdei.springcourse.repository;

import ru.avdei.springcourse.entity.Epic;
import ru.avdei.springcourse.entity.Status;
import ru.avdei.springcourse.entity.SubTask;
import ru.avdei.springcourse.entity.Task;

import java.util.*;

public class TaskRepository {
    private List<Task> allTasks;
    private List<Epic> allEpics;
    private List<SubTask> allSubtasks;


    public TaskRepository() {
        this.allTasks = new ArrayList<>();
        this.allEpics = new ArrayList<>();
        this.allSubtasks = new ArrayList<>();
    }

    public List<Task> getAllTasks() {
        return allTasks;
    }

    public List<Epic> getAllEpics() {
        return allEpics;
    }

    public List<SubTask> getAllSubtasks() {
        return allSubtasks;
    }

    @Override
    public String toString() {
        return "TaskRepository{" +
                "allTasks=" + allTasks +
                ", allEpics=" + allEpics +
                ", allSubtasks=" + allSubtasks +
                '}';
    }

    public void addTask(Task task) {
        allTasks.add(task);
    }

    public void deleteAllTasks() {
        allTasks.clear();
    }

    public Task findById(int id) {
        return allTasks.stream().filter(task -> task.getId() == id).findFirst().orElse(null);
    }

    public void update(Task task) {
        removeById(task.getId());
        allTasks.add(task);

    }

    public void removeById(int id) {
        allTasks.remove(findById(id));
    }

    public void updateTaskStatus(int id, Status status) {
        allTasks.stream().filter(task -> task.getId() == id).forEach(task -> task.setStatus(status));
    }
}
