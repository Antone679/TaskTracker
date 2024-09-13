package ru.avdei.springcourse.repository;
import ru.avdei.springcourse.entity.*;

import java.util.*;

public class TaskRepository {
    private Map<Integer, Task> allTasks;


    public TaskRepository() {
        this.allTasks = new HashMap<>();
    }

    protected void setAllTasks(Map<Integer, Task> allTasks) {
        this.allTasks = allTasks;
    }

    public Map<Integer, Task> getAllTasks() {
       return allTasks;
    }

    @Override
    public String toString() {
        return "TaskRepository{" +
                "allTasks=" + allTasks +
                '}';
    }

    public void addTask(Task task) {
        allTasks.put(task.getId(), task);
    }

    public void deleteAllTasks() {
        allTasks.clear();
    }

    public Task findById(int id) {

        if (!allTasks.containsKey(id)){
            return null;
        }
        return allTasks.get(id);
    }

    public Epic findEpicById(int id){
        if (allTasks.get(id) instanceof Epic) {
            return (Epic) allTasks.get(id);
        }
        return null;
    }

    public void update(Task task) {
        allTasks.put(task.getId(), task);

    }

    public void removeById(int id) {
        allTasks.remove(id);
    }

    public void updateTaskStatus(int id, Status status) {
        if (allTasks.get(id) instanceof Epic) {
            ((Epic) allTasks.get(id)).setStatus();
            return;

        }
        Task task = allTasks.get(id);
        task.setStatus(status);
        allTasks.put(id, task);
    }
    public List<SubTask> getSubTasksOfEpicById(int id){
        List<SubTask> list = new ArrayList<>();

        for (Map.Entry<Integer, Task> entry : allTasks.entrySet()){
            if (entry.getValue() instanceof SubTask){
                SubTask subTask = (SubTask) entry.getValue();
                if (subTask.getEpicId() == id){
                    list.add(subTask);
                }
            }
        }
        return list;
    }
}
