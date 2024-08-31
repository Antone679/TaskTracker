package ru.avdei.springcourse.service;
import ru.avdei.springcourse.entity.*;
import java.util.*;

public interface TaskService {
    public Map<Integer, Task> getTasks();
    public void createTask();
    public void deleteAllTasks();
    public Task findById(int id);
    public Epic findEpicById(int id);
    public void updateTask(Task task);
    public void removeById(int id);
    public void updateStatus(int id, Status status);
    public void getSubTasksOfEpicById(int id);
}
