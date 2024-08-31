package ru.avdei.springcourse.service;

import ru.avdei.springcourse.entity.Task;

import java.util.ArrayList;
import java.util.List;

public class HistoryServiceImpl implements HistoryService {
    private List<Task> lastSeenTasks;

    public HistoryServiceImpl() {
        this.lastSeenTasks = new ArrayList<>();
    }

    @Override
    public void add(Task task) {
        if (!lastSeenTasks.contains(task)) {
            lastSeenTasks.add(task);
        }
        if (lastSeenTasks.size() == 11) {
            lastSeenTasks.remove(0);
        }
    }

    @Override
    public void getHistory() {
        if (lastSeenTasks == null || lastSeenTasks.isEmpty())
            System.out.println("Недавно просмотренных задач нет.");

        System.out.println("Просмотренные недавно задачи:\n");
        for (Task task : lastSeenTasks) {
            System.out.println(task.toString());
            System.out.println(".".repeat(50));
        }
    }
}
