import enumlists.TaskStatus;
import exceptions.ManagerSaveException;
import managers.FileBackedTaskManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import taskstype.Epic;
import taskstype.Subtask;
import taskstype.Task;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class FileBackedTaskManagerTest extends TaskManagerTest<FileBackedTaskManager> {

    File tempFile = new File("tempFile.csv");

    @BeforeEach
    public void setManager() {
        this.taskManager = manager.managerWithFile(tempFile);
    }

    @Test
    void fileСreationСheck() { //проверка создания файла
        Task buyingCoffee = new Task("Купить кофе", "Зерновой", TaskStatus.NEW);
        taskManager.add(buyingCoffee);
        Assertions.assertTrue(tempFile.isFile());
    }

    @Test
    void checkingAFileEntry() { //проверка записи в файл
        Task buyingJam = new Task("Купить варенье", "Малиновое", TaskStatus.NEW);
        taskManager.add(buyingJam);
        Epic playingTennis = new Epic("Игра в теннис", "Начало в 19.00", TaskStatus.NEW);
        taskManager.add(playingTennis);
        Subtask racketSelection = new Subtask("Выбор ракетки", "Бренд Wilson",
                TaskStatus.IN_PROGRESS, playingTennis.getTaskId());
        taskManager.add(racketSelection);
        try (FileReader reader = new FileReader(tempFile)) {
            BufferedReader br = new BufferedReader(reader);
            List<String> tasksList = new ArrayList<>();
            while (br.ready()) {
                String line = br.readLine();
                tasksList.add(line);
            }
            for (String string : tasksList) {
                String[] tasksLine = string.split(",");
                for (String value : tasksLine) {
                    switch (value) {
                        case "TASK":
                            Assertions.assertTrue(tasksLine[2].equals("Купить варенье"));
                            break;
                        case "EPIC":
                            Assertions.assertTrue(tasksLine[2].equals("Игра в теннис"));
                            break;
                        case "SUBTASK":
                            Assertions.assertTrue(tasksLine[2].equals("Выбор ракетки"));
                            break;
                    }
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException();
        }
    }

    @Test
    void checkingUploadFromFile() { //проверка выгрузки из файла
        Task buyingJam = new Task("Купить варенье", "Малиновое", TaskStatus.NEW);
        taskManager.add(buyingJam);
        FileBackedTaskManager taskManagerDouble = FileBackedTaskManager.loadFromFile(tempFile);
        Map<Integer, Task> taskMap = taskManagerDouble.getTaskList();
        Task task = taskMap.get(1);
        String taskName = task.getTaskName();
        Assertions.assertEquals("Купить варенье", taskName, "Задача не добавлена или добавлена неверно");
    }

    @Test
    void sortedTasksByTime() { //проверка добавления задачи и сортировка по времени
        taskManager.add(new Task("Купить ноутбук", "Фирма Sony", TaskStatus.NEW, Duration.ofMinutes(20),
                LocalDateTime.of(2024, 9, 15, 9, 0)));
        taskManager.add(new Task("Купить компьютер", "Фирма Philips", TaskStatus.NEW, Duration.ofMinutes(20),
                LocalDateTime.of(2024, 10, 15, 9, 50)));
        Epic food = new Epic("Купить продукты", "По списку продуктов", TaskStatus.NEW);
        taskManager.add(food);
        taskManager.add(new Subtask("Выбор магазина", "Магазин около дома", TaskStatus.NEW, food.getTaskId(),
                Duration.ofMinutes(20), LocalDateTime.of(2024, 10, 16, 9, 15)));
        Task firstTask = taskManager.getPrioritizedTasks().stream()
                .findFirst()
                .orElse(null);
        assert firstTask != null;
        Assertions.assertEquals("Купить ноутбук", firstTask.getTaskName(), "Задача не на первом месте");
    }

    @Test
    void checkingUploadFromFileWithTime() { //проверка выгрузки из файла со временем
        taskManager.add(new Task("Купить ноутбук", "Фирма Sony", TaskStatus.NEW, Duration.ofMinutes(20),
                LocalDateTime.of(2024, 9, 15, 9, 0)));
        Map<Integer, Task> taskMap = FileBackedTaskManager.loadFromFile(tempFile).getTaskList();
        Task task = taskMap.get(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        String time = task.getStartTime().format(formatter);
        Assertions.assertEquals("15.09.2024 09:00", time, "Время не совпадает");
    }

    @Test
    void checkingTheOverlapOfTwoTasks() { //проверка пересечения двух задач по времени

        taskManager.add(new Task("Купить ноутбук", "Фирма Sony", TaskStatus.NEW, Duration.ofMinutes(20),
                LocalDateTime.of(2024, 9, 15, 9, 00)));
        taskManager.add(new Task("Купить компьютер", "Фирма Philips", TaskStatus.NEW, Duration.ofMinutes(20),
                LocalDateTime.of(2024, 9, 15, 9, 10)));
        //смотрим список задач, компьютер не должен добавиться
        boolean isThereATask = taskManager.getTaskList().values().stream() //делаем стрим из коллекции, кот получили из значений мапы
                .anyMatch(task -> task.getTaskName().equals("Купить компьютер"));
        Assertions.assertFalse(isThereATask);
    }

    @Test
    public void testException() {
        assertThrows(ManagerSaveException.class, () -> FileBackedTaskManager.loadFromFile(new File("file")), "Метод вызывается корректно");
    }
}
