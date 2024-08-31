package ru.avdei.springcourse.service;
import ru.avdei.springcourse.entity.*;
import ru.avdei.springcourse.repository.TaskRepository;
import ru.avdei.springcourse.util.IdGenerator;
import ru.avdei.springcourse.util.ServiceManager;
import java.util.*;

public class TaskServiceImpl implements TaskService {
    private TaskRepository taskRepository;
    private HistoryService historyService;

    public TaskServiceImpl() {

        this.taskRepository = new TaskRepository();
        this.historyService = ServiceManager.getDefaultHistory();
    }

    public TaskServiceImpl(TaskRepository taskRepository) {

        this.taskRepository = taskRepository;
        this.historyService = ServiceManager.getDefaultHistory();
    }

    public TaskRepository getTaskRepository() {
        return taskRepository;
    }

    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public HistoryService getHistoryService() {
        return historyService;
    }

    public void getLastSeenTasks() {
        historyService.getHistory();
    }

    public Map<Integer, Task> getTasks() {
        Map<Integer, Task> mapToReturn = new HashMap<>();
        Task zeroTask = new Task("ZERO", "ZERO", TaskType.ZERO);
        zeroTask.setStatus(Status.ZERO);
        if (taskRepository.getAllTasks().isEmpty()) {
            mapToReturn.put(0, zeroTask);
            return mapToReturn;
        }
        for(Map.Entry<Integer, Task> tmp : taskRepository.getAllTasks().entrySet()) {
            if (tmp.getValue() instanceof SubTask) {
                continue;
            }
            mapToReturn.put(tmp.getKey(), tmp.getValue());
        }
        return mapToReturn;
    }

    public void createTask() {
        System.out.println("Введите наименование задачи:");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();

        System.out.println("Теперь введите описание задачи:");
        String description = scanner.nextLine();

        System.out.println("Теперь введите тип задачи (TASK, EPIC, SUBTASK):");
        String type = scanner.nextLine();
        if (type.equals("SUBTASK")) {
            System.out.println("Теперь введите ID EPIC задачи, к которой принадлежит создаваемая задача:");
            int epicId = 0;
            try{
                epicId = scanner.nextInt();
            } catch (InputMismatchException ex){
                return;
            }

            SubTask subTask = new SubTask(name, description, TaskType.SUBTASK, epicId);
            subTask.setEpicId(epicId);
            subTask.setId(IdGenerator.generateId());
            taskRepository.addTask(subTask);
            Epic epic = findEpicById(epicId);
            epic.getSubtasks().add(subTask);
            taskRepository.updateTaskStatus(epic.getId(), Status.NEW);
            return;
        }

        if (type.equals("EPIC")) {
            Epic epic;
            try {
                epic = new Epic(name, description, TaskType.valueOf(type));
            } catch (IllegalArgumentException ex) {
                System.out.println("Некорректно ввел статус задачи. Она не сохранена.");
                return;
            }
            epic.setId(IdGenerator.generateId());


            taskRepository.addTask(epic);
            return;
        }

        Task task;
        try {
           task = new Task(name, description, TaskType.valueOf(type));
        } catch (IllegalArgumentException ex) {
            System.out.println("Некорректно ввел статус задачи. Она не сохранена.");
            return;
        }
        task.setId(IdGenerator.generateId());


        taskRepository.addTask(task);
    }

    public void deleteAllTasks() {
        taskRepository.deleteAllTasks();
        System.out.println("Все задачи удалены.");
    }

    public Task findById(int id) {
        if (taskRepository.findById(id) == null) {
            return new Task("ZERO", "ZERO", TaskType.ZERO);
        }
        Task task = taskRepository.findById(id);
        historyService.add(task);

        return task;
    }

    public Epic findEpicById(int id) {
        if (taskRepository.findById(id) == null) {
            return new Epic("ZERO", "ZERO", TaskType.ZERO);
        }

        Epic task = taskRepository.findEpicById(id);
        historyService.add(task);

        return task;
    }

    public void updateTask(Task task) {
        taskRepository.update(task);
    }

    public void removeById(int id) {
        if (taskRepository.findById(id) == null) {
            System.out.println("Такой задачи нет.");
            return;
        }

        taskRepository.removeById(id);
    }

    public void updateStatus(int id, Status status) {
        taskRepository.updateTaskStatus(id, status);
    }

    public void getSubTasksOfEpicById(int id) {
        List<SubTask> list = taskRepository.getSubTasksOfEpicById(id);

        if (list.isEmpty())
            System.out.println("Ничего нет. Возможно, список подзадач пуст, либо такого Эпика не существует.");

        for (SubTask subTask : list) {
           historyService.add(subTask);
            System.out.println(subTask);
        }
    }
}
