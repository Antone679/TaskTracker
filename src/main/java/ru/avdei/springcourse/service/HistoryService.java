package ru.avdei.springcourse.service;

import ru.avdei.springcourse.entity.Task;

import java.util.List;

public interface HistoryService {
    void add(Task task);
    void getHistory();
    void remove(Task task);

    void clear();
    List<Task> getLastSeenTasks();
}
