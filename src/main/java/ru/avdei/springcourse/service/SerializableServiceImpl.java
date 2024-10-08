package ru.avdei.springcourse.service;

import ru.avdei.springcourse.entity.*;
import ru.avdei.springcourse.util.IdGenerator;
import ru.avdei.springcourse.util.ManagerSaveException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SerializableServiceImpl extends TaskServiceImpl {

    private static Path csvFile = Paths.get("src/main/resources/information.csv");

    public SerializableServiceImpl() {
        initializeTasks();
        organizeSubAndEpics();
    }
    protected void setCsvFile(Path path){
        csvFile = path;
    }

    @Override
    public void createTask() {
        super.createTask();
        Task task = taskRepository.findById(IdGenerator.getCurrentId());
        saveToCSV(saveToString(task));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        writeHistory();

    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        try {
            Files.delete(csvFile);
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка удаления всех задач!");
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        writeHistory();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        deleteFromCSV(task);
        saveToCSV(saveToString(task));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        writeHistory();
    }

    @Override
    public void removeById(int id) {
        Task task = taskRepository.findById(id);

        if (task instanceof Epic epic) {
            List<SubTask> list = epic.getSubtasks();
            List<Integer> subTaskIdsToRemove = new ArrayList<>();

            for (SubTask subTask : list) {
                subTaskIdsToRemove.add(subTask.getId());
            }

            // Удаляем подзадачи
            for (Integer subTaskId : subTaskIdsToRemove) {
                removeById(subTaskId);
            }
        }

        super.removeById(id);
        deleteFromCSV(task);
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        writeHistory();
    }

    @Override
    public void updateStatus(int id, Status status) {
        super.updateStatus(id, status);
        deleteFromCSV(taskRepository.findById(id));
        saveToCSV(saveToString(taskRepository.findById(id)));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        writeHistory();
    }

    protected String saveToString(Task task) {
        StringBuilder sb = new StringBuilder();
        String delimiter = ",";
        sb.append(task.getId()).append(delimiter).append(task.getTaskType()).append(delimiter).append(task.getName())
                .append(delimiter).append(task.getStatus()).append(delimiter).append(task.getDescription());

        if (task instanceof SubTask) {
            sb.append(delimiter).append(((SubTask) task).getEpicId());
        }

        return sb.toString();
    }

    protected void saveToCSV(String line) {
        Path tempFile = Paths.get("src/main/resources/temp.csv");

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile.toFile()));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile.toFile()))) {
            String currentLine;

            if (csvFile.toFile().length() != 0) {

                while ((currentLine = reader.readLine()) != null) {
                    if (currentLine.isEmpty()) {
                        writer.write(line);
                        writer.newLine();
                        writer.newLine();
                        continue;
                    }
                    writer.write(currentLine);
                    writer.newLine();
                }
            }
            else {
                writer.write(line);
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Не удалось записать задачу в файл!");
        }
        try {
            Files.delete(csvFile);
            Files.move(tempFile, csvFile);
        } catch (IOException ex) {
            throw new ManagerSaveException("Не удалось завершить операцию удаления задачи из файла при копировании временного файла!");
        }
    }

    protected void deleteFromCSV(Task task) {

        if (task == null){
            return;
        }

        Path tempFile = Paths.get("src/main/resources/temp.csv");

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile.toFile()));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile.toFile()))) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                if (!currentLine.startsWith(String.valueOf(task.getId()))) {
                    writer.write(currentLine);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Не удалось удалить такую задачу!");
        }
        try {
            Files.delete(csvFile);
            Files.move(tempFile, csvFile);
        } catch (IOException ex) {
            throw new ManagerSaveException("Не удалось завершить операцию удаления задачи из файла при копировании временного файла!");
        }
    }

    protected Task getFromString(String line) {
        Task task = null;
        String[] arrayOfData = line.split(",");
        int id = Integer.parseInt(arrayOfData[0]);
        TaskType taskType = TaskType.valueOf(arrayOfData[1]);
        String name = arrayOfData[2];
        Status status = Status.valueOf(arrayOfData[3]);
        String description = arrayOfData[4];

        switch (arrayOfData[1]) {
            case "TASK":
                task = new Task(name, description, taskType);
                task.setId(id);
                task.setStatus(status);
                break;
            case "EPIC":
                task = new Epic(name, description, taskType);
                task.setId(id);
                task.setStatus(status);
                break;
            case "SUBTASK":
                int epicId = Integer.parseInt(arrayOfData[5]);
                task = new SubTask(name, description, taskType, epicId);
                task.setId(id);
                task.setStatus(status);
                break;
        }
        return task;

    }

    protected void initializeTasks() {
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile.toFile()))) {
            reader.readLine();
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.isEmpty()) {
                    break;
                }
                Task task = getFromString(currentLine);
                taskRepository.addTask(task);
                IdGenerator.setGeneratorId(task.getId());
            }
            historyInit(reader);

        } catch (IOException e) {
            throw new ManagerSaveException("Не удалось инициализировать задачи из файла!");
        }
    }

    protected void historyInit(BufferedReader reader) throws IOException {
        String currentLine;
        while ((currentLine = reader.readLine()) != null) {
            if (!currentLine.isEmpty()) {
                String[] historyData = currentLine.split(",");

                for (String elem : historyData) {
                    historyService.add(taskRepository.findById(Integer.parseInt(elem)));
                }
            }
        }
    }

    protected void organizeSubAndEpics() {
        for (Map.Entry<Integer, Task> entry : taskRepository.getAllTasks().entrySet()) {
            Task task = entry.getValue();

            if (task instanceof SubTask) {
                SubTask subTask = (SubTask) task;
                Epic epic = taskRepository.findEpicById(subTask.getEpicId());
                epic.getSubtasks().add(subTask);
            }
        }
    }

    protected void writeHistory() {
        Path tempFile = Paths.get("src/main/resources/historyTmp.csv");

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile.toFile()));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile.toFile()))) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                if (!currentLine.isEmpty()) {
                    writer.write(currentLine);
                    writer.newLine();
                } else
                    break;
            }
            writer.newLine();

            StringBuilder historyString = new StringBuilder();
            for (Task task : historyService.getLastSeenTasks()) {
                historyString.append(task.getId()).append(",");
            }

            if (historyString.length() > 0) {
                historyString.setLength(historyString.length() - 1);
                writer.write(historyString.toString());
            }

        } catch (IOException e) {
            throw new ManagerSaveException("Не удалось удалить такую задачу!");
        }
        try {
            Files.delete(csvFile);
            Files.move(tempFile, csvFile);
        } catch (IOException ex) {
            throw new ManagerSaveException("Не удалось завершить операцию удаления задачи из файла при копировании временного файла!");
        }
    }

    @Override
    public Task findById(int id) {
        Task task = super.findById(id);
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        writeHistory();
        return task;
    }

    @Override
    public Epic findEpicById(int id) {
        Epic task = super.findEpicById(id);
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        writeHistory();
        return task;
    }

    @Override
    public void getSubTasksOfEpicById(int id) {
        super.getSubTasksOfEpicById(id);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        writeHistory();
    }
}
