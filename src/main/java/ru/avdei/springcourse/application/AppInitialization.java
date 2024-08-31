package ru.avdei.springcourse.application;
import ru.avdei.springcourse.controller.TaskController;
import ru.avdei.springcourse.entity.Status;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AppInitialization {

    public static final String INSERT_NUMBER = "Введите номер задачи: ";

    public static void initialize() {
        TaskController controller = new TaskController();
        boolean initiateExit = false;
        Scanner scanner;

        do {
            showMenu();

            scanner = new Scanner(System.in);
            int choice = 0;
            try {
                choice = scanner.nextInt();
            } catch (Exception ex){
                continue;
            }

            switch (choice) {
                case 1:
                    controller.getTasks();
                    break;
                case 2:
                    controller.deleteAllTasks();
                    break;
                case 3:
                    case3(scanner, controller);
                    break;
                case 4:
                    controller.createTask();
                    break;
                case 5:
                    case5(scanner, controller);
                    break;
                case 6:
                    case6(scanner, controller);
                    break;
                case 7:
                    case7(scanner, controller);
                    break;
                case 8: controller.getHistory();
                break;
                default:
                    initiateExit = true;
                    System.out.println("Выход из программы.");
                    break;
            }

        } while (!initiateExit);
    }

    private static void case5(Scanner scanner, TaskController controller) {
        int id;
        System.out.println(INSERT_NUMBER);
        try {
            id = scanner.nextInt();
        } catch (InputMismatchException ex){
            return;
        }
        controller.removeById(id);
    }

    private static void case6(Scanner scanner, TaskController controller) {
        int id;
        System.out.println(INSERT_NUMBER);
        try {
            id = scanner.nextInt();
        } catch (InputMismatchException ex){
            return;
        }
        controller.getSubTasksOfEpicById(id);
    }

    private static void case3(Scanner scanner, TaskController controller) {
        int id;
        System.out.println(INSERT_NUMBER);
        try {
            id = scanner.nextInt();
        } catch (InputMismatchException ex){
            return;
        }
        controller.findById(id);
    }

    private static void case7(Scanner scanner, TaskController controller) {
        Status status;
        int id;
        System.out.println(INSERT_NUMBER);
        try {
            id = scanner.nextInt();
        } catch (InputMismatchException ex){
            return;
        }
        System.out.println("Введите статус задачи (NEW, IN_PROGRESS, DONE): ");
        scanner.nextLine();
        try {
            status = Status.valueOf(scanner.nextLine());
            controller.updateStatus(id, status);
        } catch (IllegalArgumentException ex){
            return;
        }
        controller.updateStatus(id, status);
    }

    private static void showMenu() {
        System.out.println("");
        System.out.println("Что требуется?");
        System.out.println("1. Получение списка всех задач.\n" +
                "2. Удаление всех задач.\n" +
                "3. Получение по идентификатору.\n" +
                "4. Создать задачу.\n" +
                "5. Удалить по АйДи.\n" +
                "6. Получение списка подзадач определенного Эпика по АйДи.\n" +
                "7. Изменить статус задачи (кроме Эпика).\n" +
                "8. Получить последние десять просмотренных задач.");
    }
}
