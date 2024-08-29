package ru.avdei.springcourse.controller;

import ru.avdei.springcourse.entity.Status;
import ru.avdei.springcourse.entity.Task;
import ru.avdei.springcourse.service.TaskService;

import java.util.List;
import java.util.Map;

public class TaskController {
    private TaskService taskService;

    public TaskController() {
        this.taskService = new TaskService();
    }
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
        Map<Integer, Task> tasks = taskService.getTasks();
        for (Map.Entry<Integer, Task> task : tasks.entrySet()) {
            System.out.println(task.getValue());
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
    public void getSubTasksOfEpicById(int id){
        taskService.getSubTasksOfEpicById(id);
    }
}
