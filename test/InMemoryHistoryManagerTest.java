import enumlists.TaskStatus;
import managers.Managers;
import managers.TaskManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import taskstype.Epic;
import taskstype.Subtask;
import taskstype.Task;

import java.util.List;

class InMemoryHistoryManagerTest {
    Managers manager = new Managers();
    TaskManager taskManager = manager.getDefault();

    @BeforeEach
    void createEpic() {
        Epic catFood = new Epic("Покормить кота", "Корм для толстых котов и миска", TaskStatus.NEW);
        taskManager.add(catFood);
    }

    @Test
    void printHistoryTest() {

        Subtask storeSelection = new Subtask("Выбор магазина", "Магазин около дома", TaskStatus.NEW, 1);
        Subtask foodSelection = new Subtask("Выбор корма", "Корм для толстых котов", TaskStatus.DONE, 1);
        Subtask bowlSelection = new Subtask("Выбор миски", "Миска стеклянная", TaskStatus.DONE, 1);
        taskManager.add(storeSelection);
        taskManager.add(foodSelection);
        taskManager.add(bowlSelection);
        taskManager.searchEpicById(1);
        taskManager.searchSubtaskById(3);
        taskManager.printHistory();
    }

    @Test
    void checkingAddingToHistoryList() { //проверка добавления и последовательности
        Subtask storeSelection = new Subtask("Выбор магазина", "Магазин около дома", TaskStatus.NEW, 1);
        taskManager.add(storeSelection);
        Task buyingJam = new Task("Купить варенье", "Малиновое", TaskStatus.NEW);
        taskManager.add(buyingJam);
        taskManager.searchEpicById(1);
        taskManager.searchSubtaskById(2);
        taskManager.searchTaskById(3);
        List<Task> historyList = taskManager.getHistory();
        Task task = historyList.get(0);
        String taskName = task.getTaskName();
        Assertions.assertEquals("Покормить кота", taskName, "Задача не найдена, в листе не сохранилась");
        Task taskNext = historyList.get(1);
        String taskNextName = taskNext.getTaskName();
        Assertions.assertEquals("Выбор магазина", taskNextName, "Такой задачи на данной позиции нет");
    }

    @Test
    void checkForDuplicates () { //проверка повторов
        Subtask storeSelection = new Subtask("Выбор магазина", "Магазин около дома", TaskStatus.NEW, 1);
        taskManager.add(storeSelection);
        Task buyingJam = new Task("Купить варенье", "Малиновое", TaskStatus.NEW);
        taskManager.add(buyingJam);
        taskManager.searchEpicById(1);
        taskManager.searchSubtaskById(2);
        taskManager.searchTaskById(3);
        taskManager.searchEpicById(1);
        List<Task> historyList = taskManager.getHistory();
        Task lastTask = historyList.getLast();
        String lastTaskName = lastTask.getTaskName();
        Assertions.assertEquals("Покормить кота", lastTaskName, "Такой задачи на данной позиции нет");
        Task firstTask = historyList.getFirst();
        String firstTaskName = firstTask.getTaskName();
        Assertions.assertEquals("Выбор магазина", firstTaskName, "Такой задачи на данной позиции нет");
    }

}