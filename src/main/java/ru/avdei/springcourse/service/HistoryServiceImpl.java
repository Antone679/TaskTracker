package ru.avdei.springcourse.service;

import ru.avdei.springcourse.entity.Node;
import ru.avdei.springcourse.entity.Task;

import java.util.*;

public class HistoryServiceImpl implements HistoryService {
    private List<Task> lastSeenTasks;
    private Map<Integer, Node> nodeMap;
    private MyLinkedList linked;

    public HistoryServiceImpl() {
        this.lastSeenTasks = new ArrayList<>();
        this.nodeMap = new HashMap<>();
        this.linked = new MyLinkedList();
    }

    public List<Task> getLastSeenTasks() {
        lastSeenTasks.clear();
        linked.getTasks(lastSeenTasks); // Обновляем lastSeenTasks из linked
        return lastSeenTasks;
    }

    @Override
    public void add(Task task) {
        Node toAdd = new Node(task);

        if (nodeMap.containsKey(task.getId())) {
            Node existingNode = nodeMap.get(task.getId());
            linked.removeNode(existingNode);
            nodeMap.remove(task.getId());
        }

        nodeMap.put(task.getId(), toAdd);
        linked.linkLast(toAdd);

        // Убираем очистку lastSeenTasks здесь
        // Обновляем lastSeenTasks, если вам нужно (это можно сделать в getLastSeenTasks)
    }

    @Override
    public void getHistory() {
        lastSeenTasks.clear();
        linked.getTasks(lastSeenTasks); // Обновляем lastSeenTasks

        if (lastSeenTasks.isEmpty()) {
            System.out.println("Недавно просмотренных задач нет.");
            return;
        }
        System.out.println("Просмотренные недавно задачи:\n");

        for (Task task : lastSeenTasks) {
            System.out.println(task.toString());
            System.out.println(".".repeat(50));
        }
    }

    @Override
    public void remove(Task task) {
        if (nodeMap.containsKey(task.getId())) { // Проверяем, существует ли задача
            linked.removeNode(nodeMap.get(task.getId()));
            nodeMap.remove(task.getId());
        }

        // Убираем очистку lastSeenTasks здесь
        // Обновляем lastSeenTasks, если вам нужно (это можно сделать в getLastSeenTasks)
    }

    @Override
    public void clear() {
        linked.clear();
        lastSeenTasks.clear(); // Очищаем историю
    }

    private class MyLinkedList {
        private Node head;
        private Node last;

        public MyLinkedList() {
            this.head = null;
            this.last = null;
        }

        public void linkLast(Node newNode) {
            if (head == null) {
                head = newNode;
                last = newNode;
                return;
            }

            last.setNext(newNode);
            newNode.setPrev(last);
            last = newNode;
        }

        public void getTasks(List<Task> tasks) { // Передаем список для заполнения
            Node current = head;
            while (current != null) {
                tasks.add(current.getTask());
                current = current.getNext();
            }
        }

        public void removeNode(Node node) {
            if (node == null) return;

            if (node.getPrev() != null) {
                node.getPrev().setNext(node.getNext());
            } else {
                head = node.getNext();
            }
            if (node.getNext() != null) {
                node.getNext().setPrev(node.getPrev());
            } else {last = node.getPrev();
            }
        }

        public void clear() {
            head = null;
            last = null;
        }
    }
}
