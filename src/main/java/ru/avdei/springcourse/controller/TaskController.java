package ru.avdei.springcourse.controller;

import ru.avdei.springcourse.entity.Status;
import ru.avdei.springcourse.entity.Task;
import ru.avdei.springcourse.service.TaskServiceImpl;

import java.util.Map;

public class TaskController {
    private TaskServiceImpl taskService;

    public TaskController() {
        this.taskService = new TaskServiceImpl();
    }
    public TaskController(TaskServiceImpl taskService) {
        this.taskService = taskService;
    }

    public TaskServiceImpl getTaskService() {
        return taskService;
    }

    public void setTaskService(TaskServiceImpl taskService) {
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

    public void getHistory(){
        taskService.getLastSeenTasks();
    }
}
