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
    }

    @Override
    public void getHistory() {
        lastSeenTasks.clear();
        linked.getTasks();

        if (lastSeenTasks == null || lastSeenTasks.isEmpty()){
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
        linked.removeNode(nodeMap.get(task.getId()));
        nodeMap.remove(task.getId());

    }

    @Override
    public void clear() {
        linked.clear();
    }

    private class MyLinkedList {
        private Node head;
        private Node last;
        private int size;

        public MyLinkedList() {
            this.head = null;
            this.last = null;
            this.size = 0;
        }

        public void linkLast(Node newNode) {

            if (head == null) {
                head = newNode;
                last = newNode;
                size++;
                return;
            }

            last.setNext(newNode);
            last = newNode;
            size++;

        }
        public void getTasks() {
            Node current = head;
            while (current != null) {
                lastSeenTasks.add(current.getTask());
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
            } else {
                last = node.getPrev();
            }
            size--;
        }

        public void clear() {
            head = null;
            last = null;
            size = 0;
        }
    }
}
