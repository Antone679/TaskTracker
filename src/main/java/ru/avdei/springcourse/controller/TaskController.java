package ru.avdei.springcourse.controller;

import ru.avdei.springcourse.entity.Status;
import ru.avdei.springcourse.entity.Task;
import ru.avdei.springcourse.service.TaskService;

import java.util.List;

public class TaskController {
    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    public TaskService getTaskService() {
        return taskService;
    }

    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    public void getTasks() {
        List<Task> tasks = taskService.getTasks();
        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    public void createTask() {
        taskService.createTask();
    }

    public void deleteAllTasks() {
        taskService.deleteAllTasks();
    }

    public void findById(int id) {
        System.out.println(taskService.findById(id));
    }

    public void updateTask(Task task) {
        taskService.updateTask(task);
    }

    public void removeById(int id) {
        taskService.removeById(id);
    }

    public void updateStatus(int id, Status status) {
        taskService.updateStatus(id, status);
    }
}
