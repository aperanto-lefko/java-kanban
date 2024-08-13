import enumlists.TaskStatus;
import exceptions.ManagerSaveException;
import managers.FileBackedTaskManager;
import managers.Managers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import taskstype.Epic;
import taskstype.Subtask;
import taskstype.Task;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class FileBackedTaskManagerTest {

    Managers manager = new Managers();
    File tempFile = new File("tempFile.csv");
    FileBackedTaskManager taskManager = manager.managerWithFile(tempFile);


    @Test
    void addTask() { //проверка добавления задачи
        Task buyingCoffee = new Task("Купить кофе", "Зерновой", TaskStatus.NEW);
        taskManager.add(buyingCoffee);
        Map<Integer, Task> taskList = taskManager.getTaskList();
        Task task = taskList.get(1);
        String taskName = task.getTaskName();
        Assertions.assertEquals("Купить кофе", taskName, "Задача не добавлена или добавлена неверно");
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
}
