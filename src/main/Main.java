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
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args)  {
        Managers manager = new Managers();
        TaskManager taskManager = manager.getDefault();
        HistoryManager historyManager = manager.getDefaultHistory();

/*
        Task buyingCoffee = new Task("Купить кофе", "Зерновой", TaskStatus.NEW);
        Task buyingJam = new Task("Купить варенье", "Малиновое", TaskStatus.NEW);
        taskManager.add(buyingCoffee);
        taskManager.add(buyingJam);
        System.out.println("Список задач");
        taskManager.printTask();

        Task buyingJamNew = new Task("Купить варенье", "Малиновое", 2, TaskStatus.IN_PROGRESS);
        taskManager.update(buyingJamNew);
        System.out.println("Обновленный список задач");
        taskManager.printTask();
        taskManager.removeTaskById(1);
        System.out.println("Обновленный список задач");
        taskManager.printTask();
        Epic catFood = new Epic("Покормить кота", "Корм для толстых котов и миска", TaskStatus.NEW);
        taskManager.add(catFood);
        Subtask storeSelection = new Subtask("Выбор магазина", "Магазин около дома", TaskStatus.NEW, 3);
        Subtask foodSelection = new Subtask("Выбор корма", "Корм для толстых котов", TaskStatus.DONE, 3);
        Subtask bowlSelection = new Subtask("Выбор миски", "Миска стеклянная", TaskStatus.DONE, 3);
        taskManager.add(storeSelection);
        taskManager.add(foodSelection);
        taskManager.add(bowlSelection);
        Epic playingTennis = new Epic("Игра в теннис", "Начало в 19.00", TaskStatus.NEW);
        taskManager.add(playingTennis);
        Subtask racketSelection = new Subtask("Выбор ракетки", "Бренд Wilson", TaskStatus.IN_PROGRESS, 7);
        taskManager.add(racketSelection);
        System.out.println("Список эпиков");
        taskManager.printEpic();
        System.out.println("Обновление подзадачи");
        Subtask racketSelectionUpdated = new Subtask("Выбор ракетки", "Бренд HEAD", TaskStatus.NEW, 8, 7);
        taskManager.update(racketSelectionUpdated);
        System.out.println("Список эпиков с обновленными подзадачами");
        taskManager.printEpic();
        System.out.println("Обновление эпика");
        Epic playingTennisUpdated = new Epic("Игра в теннис", "Начало в 11.00", 7, TaskStatus.NEW, new ArrayList<>(List.of(8)));
        taskManager.update(playingTennisUpdated);
        Epic catFoodUpdated = new Epic("Покормить кота хорошим кормом", "Корм для толстых котов и миска", 3, TaskStatus.NEW, new ArrayList<>(List.of(4, 5, 6)));
        taskManager.update(catFoodUpdated);
        System.out.println("Список обновленных эпиков");
        taskManager.printEpic();
        System.out.println("Список подзадач конкретного эпика");
        taskManager.printingRequiredEpic(7);
        System.out.println("Список всех подзадач");
        taskManager.printSubtask();
        System.out.println("Удаление подзадачи");
        taskManager.removeSubtaskById(6);
        System.out.println("Список эпиков с обновленными подзадачами");
        taskManager.printEpic();
        taskManager.searchTaskById(2);
        taskManager.searchEpicById(7);
        taskManager.searchSubtaskById(8);
        taskManager.printHistory();

        taskManager.searchSubtaskById(8);
        taskManager.searchEpicById(7);
        taskManager.searchTaskById(2);
        System.out.println("Проверка удаления");

        taskManager.searchEpicById(7);
        taskManager.searchTaskById(2);
        taskManager.searchSubtaskById(8);

        taskManager.printHistory();

       File file = new File("fileBacked.csv");

        FileBackedTaskManager taskManagerWithFile = manager.managerWithFile(file);
        Task buyingJamDouble = new Task("Купить варенье", "Малиновое", TaskStatus.NEW);
        Task buyingCoffeeDouble = new Task("Купить кофе", "Зерновой", TaskStatus.NEW);
        Task buyingJamThrouble = new Task("Купить мяч", "Резиновый", TaskStatus.NEW);
        taskManagerWithFile.add(buyingJamDouble);
        taskManagerWithFile.add(buyingCoffeeDouble);
        taskManagerWithFile.add(buyingJamThrouble);
        Epic playingTennisDouble = new Epic("Игра в теннис", "Начало в 19.00", TaskStatus.NEW);
        taskManagerWithFile.add(playingTennisDouble);

        Subtask racketSelectionDouble = new Subtask("Выбор ракетки", "Бренд Wilson", TaskStatus.IN_PROGRESS, playingTennisDouble.getTaskId());
        taskManagerWithFile.add(racketSelectionDouble);
        Task buyingJamNewDouble = new Task("Купить варенье", "Вишневое", buyingJamDouble.getTaskId(), TaskStatus.IN_PROGRESS);
        taskManagerWithFile.update(buyingJamNewDouble);
        */


        //Проверка задач с временем
        File file = new File("fileBacked.csv");

        FileBackedTaskManager taskManagerWithFile = manager.managerWithFile(file);
        Task laptop = new Task("Купить ноутбук", "Фирма Sony", TaskStatus.NEW, Duration.ofMinutes(10),
                LocalDateTime.of(2024, 9, 15, 9, 00));
        taskManagerWithFile.add(laptop);
        Epic food = new Epic("Купить продукты", "По списку продуктов", TaskStatus.NEW);
        taskManagerWithFile.add(food);

        Subtask store = new Subtask("Выбор магазина", "Магазин около дома", TaskStatus.NEW, food.getTaskId(),
                Duration.ofMinutes(20), LocalDateTime.of(2024, 8, 21, 12, 24 ));
        taskManagerWithFile.add(store);


        Subtask list = new Subtask("Список продуктов", "Молоко хлеб", TaskStatus.NEW, food.getTaskId(),
                Duration.ofMinutes(10), LocalDateTime.of(2024, 8, 22, 13, 00 ));
        taskManagerWithFile.add(list);


        Subtask catFood = new Subtask("Корм для кота", "Корм для толстых котов", TaskStatus.NEW,food.getTaskId());

        taskManagerWithFile.add(catFood);
        Task buyingCoffee = new Task("Купить кофе", "Зерновой", TaskStatus.NEW);
        taskManagerWithFile.add(buyingCoffee);


        //Работа с существующим файлом*/
       /* File file = new File("fileBacked.csv");*/

       /*  FileBackedTaskManager taskManager2 = FileBackedTaskManager.loadFromFile(file);
        System.out.println("Проверка добавления задач");
        taskManager2.printTask();
        System.out.println("Проверка добавления эпика");
        taskManager2.printEpic();
        System.out.println("Проверка добавления подзадач");
        taskManager2.printSubtask();*/


        System.out.println("Печать отсортированного списка" + taskManagerWithFile.getPrioritizedTasks());



    }
}
