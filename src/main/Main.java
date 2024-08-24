package main;

import enumlists.TaskStatus;
import managers.FileBackedTaskManager;
import managers.HistoryManager;
import managers.Managers;
import managers.TaskManager;
import taskstype.Epic;
import taskstype.Subtask;
import taskstype.Task;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args)  {
        Managers manager = new Managers();

        TaskManager taskManager = manager.getDefault();


        Task buyingCoffee = new Task("Купить кофе", "Зерновой", TaskStatus.NEW);
        Task buyingJam = new Task("Купить варенье", "Малиновое", TaskStatus.NEW);
        taskManager.add(buyingCoffee);
        taskManager.add(buyingJam);

        Epic catFood = new Epic("Покормить кота", "Корм для толстых котов и миска", TaskStatus.NEW);
        taskManager.add(catFood);
        Subtask storeSelection = new Subtask("Выбор магазина", "Магазин около дома", TaskStatus.NEW, catFood.getTaskId());
        Subtask foodSelection = new Subtask("Выбор корма", "Корм для толстых котов", TaskStatus.DONE, catFood.getTaskId());
        Subtask bowlSelection = new Subtask("Выбор миски", "Миска стеклянная", TaskStatus.DONE, catFood.getTaskId());
        taskManager.add(storeSelection);
        taskManager.add(foodSelection);
        taskManager.add(bowlSelection);


        taskManager.searchTaskById(buyingCoffee.getTaskId());
        taskManager.searchEpicById(catFood.getTaskId());
        taskManager.searchSubtaskById(foodSelection.getTaskId());
        taskManager.printHistory();


        //Проверка задач с временем
        File file = new File("fileBacked.csv");

        FileBackedTaskManager taskManagerWithFile = manager.managerWithFile(file);
        Task laptop = new Task("Купить ноутбук", "Фирма Sony", TaskStatus.NEW, Duration.ofMinutes(20),
                LocalDateTime.of(2024, 9, 15, 9, 00));
        taskManagerWithFile.add(laptop);
        Task pc = new Task("Купить компьютер", "Фирма Philips", TaskStatus.NEW, Duration.ofMinutes(20),
                LocalDateTime.of(2024, 10, 15, 9, 50));
        taskManagerWithFile.add(pc);
        Epic food = new Epic("Купить продукты", "По списку продуктов", TaskStatus.NEW);
        taskManagerWithFile.add(food);

        Subtask store = new Subtask("Выбор магазина", "Магазин около дома", TaskStatus.NEW, food.getTaskId(),
                Duration.ofMinutes(20), LocalDateTime.of(2024, 9, 15, 9, 15 ));
        taskManagerWithFile.add(store);

        Subtask list = new Subtask("Список продуктов", "Молоко хлеб", TaskStatus.NEW, food.getTaskId(),
                Duration.ofMinutes(10), LocalDateTime.of(2024, 8, 22, 13, 00 ));
        taskManagerWithFile.add(list);

        //Работа с существующим файлом

        FileBackedTaskManager taskManager2 = FileBackedTaskManager.loadFromFile(file);
        System.out.println("Проверка добавления задач");
        taskManager2.printTask();
        System.out.println("Проверка добавления эпика");
        taskManager2.printEpic();
        System.out.println("Проверка добавления подзадач");
        taskManager2.printSubtask();

    }
}
