import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    Managers manager = new Managers();
    TaskManager taskManager = manager.getDefault();


    @Test
    void addTask() {
        Task buyingCoffee = new Task("Купить кофе", "Зерновой", TaskStatus.NEW);
        taskManager.add(buyingCoffee);
        HashMap<Integer, Task> taskList = taskManager.getTaskList();
        Task task = taskList.get(1);
        String taskName = task.getTaskName();
        Assertions.assertEquals("Купить кофе", taskName, "Задача не добавлена или добавлена неверно");
    }

    @Test
    void addEpic() {
        Epic catFood = new Epic("Покормить кота", "Корм для толстых котов и миска", TaskStatus.NEW);
        taskManager.add(catFood);
        HashMap<Integer, Epic> epicList = taskManager.getEpicList();
        Epic epic = epicList.get(1);
        String epicName = epic.getTaskName();
        Assertions.assertEquals("Покормить кота", epicName, "Задача не добавлена или добавлена неверно");
    }

    @Test
    void addSubtask() {
        Epic catFood = new Epic("Покормить кота", "Корм для толстых котов и миска", TaskStatus.NEW);
        taskManager.add(catFood);
        Subtask storeSelection = new Subtask("Выбор магазина", "Магазин около дома", TaskStatus.NEW, 1);
        taskManager.add(storeSelection);
        HashMap<Integer, Subtask> subtaskList = taskManager.getSubtaskList();
        Subtask subtask = subtaskList.get(2);
        String subtaskName = subtask.getTaskName();
        Assertions.assertEquals("Выбор магазина", subtaskName, "Задача не добавлена или добавлена неверно");
    }

    @Test
    void searchTaskById() {
        Task buyingJam = new Task("Купить варенье", "Малиновое", TaskStatus.NEW);
        taskManager.add(buyingJam);
        Task task = taskManager.searchTaskById(1);
        Integer taskId = task.getTaskId();
        Assertions.assertEquals(1, taskId, "Номер id не совпадает");
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
        ArrayList<Task> historyList = taskManager.getHistoryList();
        Task task = historyList.get(0);
        String taskName = task.getTaskName();
        Assertions.assertEquals("Покормить кота", taskName, "Задача не найдена, в листе не сохранилась");
    }

    @Test
    void constancyOfTheTask() {
        Task buyingJam = new Task("Купить варенье", "Малиновое", TaskStatus.NEW);
        taskManager.add(buyingJam);
        HashMap<Integer, Task> taskList = taskManager.getTaskList();
        Task task = taskList.get(1);
        Assertions.assertEquals("Купить варенье", task.getTaskName(), "Имя задачи не совпадает");
        Assertions.assertEquals("Малиновое", task.getTaskDescription(), "Описание  задачи не совпадает");
        Assertions.assertEquals(TaskStatus.NEW, task.getTaskStatus(), "Статус  задачи не совпадает");
    }

    @Test
    void utilityClassCheck() {
        HashMap<Integer, Task> newTaskHashMap = new HashMap<>();
        HashMap<Integer, Subtask> newSubtaskHashMap = new HashMap<>();
        HashMap<Integer, Epic> newEpictaskHashMap = new HashMap<>();
        HistoryManager history = manager.getDefaultHistory();
        ArrayList<Task> newHistoryList = new ArrayList<>();
        Assertions.assertEquals(newTaskHashMap, taskManager.getTaskList(), "Таблица не создана");
        Assertions.assertEquals(newSubtaskHashMap, taskManager.getSubtaskList(), "Таблица не создана");
        Assertions.assertEquals(newEpictaskHashMap, taskManager.getEpicList(), "Таблица не создана");
        Assertions.assertEquals(newHistoryList, history.getHistory(), "Список не создан");
    }

    @Test
    void taskComparisonById() {
        Task buyingJam = new Task("Купить варенье", "Малиновое", TaskStatus.NEW);
        taskManager.add(buyingJam);
        Task buyingJamTwo = new Task("Купить варенье", "Малиновое", 1, TaskStatus.NEW);
        Assertions.assertEquals(buyingJam, buyingJamTwo, "Задачи не совпадают");
    }

    @Test
    void epicComparisonById() {
        Epic playingTennis = new Epic("Игра в теннис", "Начало в 19.00", TaskStatus.NEW);
        taskManager.add(playingTennis);
        Epic playingTennisForCheking = new Epic("Игра в теннис", "Начало в 19.00", 1, TaskStatus.NEW, new ArrayList<>(List.of(0)));
        Assertions.assertEquals(playingTennis, playingTennisForCheking, "Эпики не совпадают");
    }

    @Test
    void subtaskComparisonById() {
        Epic catFood = new Epic("Покормить кота", "Корм для толстых котов и миска", TaskStatus.NEW);
        taskManager.add(catFood);
        Subtask storeSelection = new Subtask("Выбор магазина", "Магазин около дома", TaskStatus.NEW, 1);
        taskManager.add(storeSelection);
        Subtask storeSelectionForCheking = new Subtask("Выбор магазина", "Магазин около дома", TaskStatus.NEW, 2, 1);
        Assertions.assertEquals(storeSelection, storeSelectionForCheking, "Задачи не совпадают");
    }


    @Test
    void checkingIdForConflict() {
        //проверка на кофликт при обновлении одного типа
        Task buyingJam = new Task("Купить варенье", "Малиновое", TaskStatus.NEW);
        taskManager.add(buyingJam);
        int buyingJamId = buyingJam.getTaskId();
        Task buyingJamTwo = new Task("Купить варенье", "Вишневое", buyingJamId, TaskStatus.NEW);
        taskManager.update(buyingJamTwo);
        HashMap<Integer, Task> taskHashMap = taskManager.getTaskList();
        Task buyingJamTwoForCheking = taskHashMap.get(buyingJamId);
        Assertions.assertEquals(buyingJamTwoForCheking, buyingJamTwo, "Задачи не совпадают");
        //проверка на конфликт при обновлении разных типов
        Epic catFood = new Epic("Покормить кота", "Корм для толстых котов и миска", TaskStatus.NEW);
        taskManager.add(catFood);
        int epicId = catFood.getTaskId();
        Task buyingJamThree = new Task("Купить варенье", "Вишневое", epicId, TaskStatus.NEW);
        taskManager.update(buyingJamThree);
        ArrayList<Integer> taskIdList = taskManager.getIdTaskList();
        Assertions.assertFalse(taskIdList.contains(epicId));//должен быть false, в списке id задач не может быть номер эпика
    }

    @Test
    void assigningEpicAsASubtask() {
        Epic catFood = new Epic("Покормить кота", "Корм для толстых котов и миска", TaskStatus.NEW);
        taskManager.add(catFood);
        Integer epicId = catFood.getTaskId();
        Subtask storeSelection = new Subtask("Выбор магазина", "Магазин около дома", TaskStatus.NEW, epicId);
        taskManager.add(storeSelection);
        int storeSelectionId = storeSelection.getTaskId();
        Epic catFoodUpdate = new Epic("Покормить кота", "Корм для толстых котов и миска", epicId, TaskStatus.NEW, new ArrayList<>(List.of(epicId)));
        taskManager.update(catFoodUpdate);
        HashMap<Integer, Epic> epicHashMap = taskManager.getEpicList(); //достаем эпик с конкретным id, проверить обновился или нет
        Epic catFoodCheking = epicHashMap.get(epicId);
        ArrayList<Integer> catFoodChekingSubtaskId = catFoodCheking.getSubtaskId(); //достаем список подзадач проверяемого эпика
        Assertions.assertFalse(catFoodChekingSubtaskId.contains(epicId)); //список подзадач не содержит id эпика
    }

    @Test
    void assigningSubtaskAsEpic() {
        Epic catFood = new Epic("Покормить кота", "Корм для толстых котов и миска", TaskStatus.NEW);
        taskManager.add(catFood);
        int epicId = catFood.getTaskId();
        Subtask storeSelection = new Subtask("Выбор магазина", "Магазин около дома", TaskStatus.NEW, epicId);
        taskManager.add(storeSelection);
        int subtaskId = storeSelection.getTaskId();
        ArrayList<Integer> epicIdList = taskManager.getIdEpicList();
        Assertions.assertFalse(epicIdList.contains(subtaskId)); // в списке id эпиков нет id подзадачи, подзадача не добавится
    }

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
        taskManager.getHistory();
    }
}





