package ru.avdei.springcourse.service;

import ru.avdei.springcourse.entity.Status;
import ru.avdei.springcourse.entity.Task;
import ru.avdei.springcourse.repository.TaskRepository;
import ru.avdei.springcourse.util.IdGenerator;

import java.util.List;
import java.util.Scanner;

public class TaskService {
    private TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public TaskRepository getTaskRepository() {
        return taskRepository;
    }

    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getTasks() {
        return taskRepository.getAllTasks();
    }

    public void createTask() {
        System.out.println("Введите наименование задачи:");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        System.out.println("Теперь введите описание задачи:");
        String description = scanner.nextLine();
        Task task = new Task(name, description);
        task.setId(IdGenerator.generateId());
        task.setStatus(Status.NEW);
        taskRepository.addTask(task);
    }

    public void deleteAllTasks() {
        taskRepository.deleteAllTasks();
    }

    public Task findById(int id) {
        return taskRepository.findById(id);
    }

    public void updateTask(Task task) {
    taskRepository.update(task);
    }
    public void removeById(int id){
        taskRepository.removeById(id);
    }
    public void updateStatus(int id, Status status){
        taskRepository.updateTaskStatus(id, status);
    }
}
