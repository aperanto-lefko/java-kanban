import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    Managers manager = new Managers();
    TaskManager taskManager = manager.getDefault();

    @Test
    void printHistoryTest() {
        Epic catFood = new Epic("Покормить кота", "Корм для толстых котов и миска", TaskStatus.NEW);
        taskManager.add(catFood);
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
    void checkingAddingToHistoryList() {
        Epic catFood = new Epic("Покормить кота", "Корм для толстых котов и миска", TaskStatus.NEW);
        taskManager.add(catFood);
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
    }

}