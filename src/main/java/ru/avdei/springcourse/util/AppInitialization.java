package ru.avdei.springcourse.util;

import ru.avdei.springcourse.controller.TaskController;
import ru.avdei.springcourse.entity.Status;

import java.util.InputMismatchException;
import java.util.Scanner;

public class AppInitialization {

    public static void initialize() {
        TaskController controller = new TaskController();
        boolean initiateExit = false;
        Scanner scanner;

        do {
            System.out.println("");
            System.out.println("Что требуется?");
            System.out.println("1. Получение списка всех задач.\n" +
                    "2. Удаление всех задач.\n" +
                    "3. Получение по идентификатору.\n" +
                    "4. Создать задачу.\n" +
                    "5. Удалить по АйДи.\n" +
                    "6. Получение списка подзадач определенного Эпика по АйДи.\n" +
                    "7. Изменить статус задачи (кроме Эпика).");

            scanner = new Scanner(System.in);
            int choice = 0;
            try {
                choice = scanner.nextInt();
            } catch (Exception ex){
                continue;
            }
            int id = 0;
            Status status;

            switch (choice) {
                case 1:
                    controller.getTasks();
                    break;
                case 2:
                    controller.deleteAllTasks();
                    break;
                case 3:
                    System.out.println("Введите номер задачи: ");
                    try {
                        id = scanner.nextInt();
                    } catch (InputMismatchException ex){
                        continue;
                    }
                    controller.findById(id);
                    break;
                case 4:
                    controller.createTask();
                    break;
                case 5:
                    System.out.println("Введите номер задачи: ");
                    try {
                        id = scanner.nextInt();
                    } catch (InputMismatchException ex){
                        continue;
                    }
                    controller.removeById(id);
                    break;
                case 6:
                    System.out.println("Введите номер задачи: ");
                    try {
                        id = scanner.nextInt();
                    } catch (InputMismatchException ex){
                        continue;
                    }
                    controller.getSubTasksOfEpicById(id);
                    break;
                case 7:
                    System.out.println("Введите номер задачи: ");
                    try {
                        id = scanner.nextInt();
                    } catch (InputMismatchException ex){
                        continue;
                    }
                    System.out.println("Введите статус задачи (NEW, IN_PROGRESS, DONE): ");
                    scanner.nextLine();
                    try {
                        status = Status.valueOf(scanner.nextLine());
                        controller.updateStatus(id, status);
                    } catch (IllegalArgumentException ex){
                        continue;
                    }
                    controller.updateStatus(id, status);
                    break;
                default:
                    initiateExit = true;
                    System.out.println("Выход из программы.");
                    break;
            }

        } while (!initiateExit);
    }
}
