import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import enumlists.TaskStatus;
import http.BaseHttpHandler;
import http.HttpTaskServer;
import managers.InMemoryTaskManager;
import managers.TaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import taskstype.Task;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class HttpTaskServerTest {
    public TaskManager taskManager = new InMemoryTaskManager();
    public BaseHttpHandler handler = new BaseHttpHandler(taskManager);
    public HttpTaskServer taskServer = new HttpTaskServer(taskManager);
    public Gson gson = handler.getGson();

    public HttpTaskServerTest() throws IOException {
    }

    @BeforeEach
    public void setUp() throws IOException {
        taskServer.createHttpServer();
        taskServer.start();
    }

    @AfterEach
    public void shutDown() {
        taskServer.stop();
    }

    @Test
    public void testAddTask() throws IOException, InterruptedException {
        Task task = new Task("Test 2", "Testing task 2",
                TaskStatus.NEW, Duration.ofMinutes(5), LocalDateTime.now());
        String taskJson = gson.toJson(task);

        // создаём HTTP-клиент и запрос
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(taskJson)) //добавляет тело запроса в запрос
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response.statusCode());
        Map<Integer, Task> tasksFromManager = taskManager.getTaskList();
        assertNotNull(tasksFromManager, "Список пуст");
        Assertions.assertEquals("Test 2", Optional.of(tasksFromManager.get(1).getTaskName()).orElse("Имя отсутствует"), "Некорректное имя задачи");
        System.out.println(tasksFromManager.get(1).getTaskId());
    }

    @Test
    public void testDeleteTask() throws IOException, InterruptedException {
        Task task = new Task("Test 2", "Testing task 2",
                TaskStatus.NEW, Duration.ofMinutes(5), LocalDateTime.now());
        String taskJson = gson.toJson(task);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(taskJson)) //добавляет тело запроса в запрос
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        URI urlForDelete = URI.create("http://localhost:8080/tasks/1");
        HttpRequest requestForDelete = HttpRequest.newBuilder()
                .uri(urlForDelete)
                .DELETE()
                .build();
        HttpResponse<String> responseDelete = client.send(requestForDelete, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, responseDelete.statusCode());
        assertEquals("Задача с id 1 удалена", responseDelete.body());
        assertNull(taskManager.getTaskList().get(1), "Задача не удалена");
    }

    @Test
    public void testUpdateTask() throws IOException, InterruptedException {
        Task task = new Task("Test 2", "Testing task 2",
                TaskStatus.NEW, Duration.ofMinutes(5), LocalDateTime.now());

        String taskJson = gson.toJson(task);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(taskJson)) //добавляет тело запроса в запрос
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Task updatedTask = new Task("Test 2.1", "Testing task 2.1", 1,
                TaskStatus.DONE, Duration.ofMinutes(15), LocalDateTime.now());
        String updatedTaskJson = gson.toJson(updatedTask);
        URI urlForUpdate = URI.create("http://localhost:8080/tasks");
        HttpRequest requestForUpdate = HttpRequest.newBuilder()
                .uri(urlForUpdate)
                .POST(HttpRequest.BodyPublishers.ofString(updatedTaskJson))
                .build();
        HttpResponse<String> responseUpdate = client.send(requestForUpdate, HttpResponse.BodyHandlers.ofString());
        Map<Integer, Task> map = taskManager.getTaskList();
        assertEquals(201, responseUpdate.statusCode());
        assertEquals("Задача с id 1 обновлена", responseUpdate.body());
        assertEquals("Test 2.1", Optional.of(taskManager.getTaskList().get(1).getTaskName()).orElse("Имя отсутствует"));
    }

    @Test
    public void testPriorityListTask() throws IOException, InterruptedException {
        Task task1 = new Task("Test 1", "Testing task 1",
                TaskStatus.NEW, Duration.ofMinutes(5), LocalDateTime.of(2024, 03, 12, 15, 01));
        Task task2 = new Task("Test 2", "Testing task 2",
                TaskStatus.NEW, Duration.ofMinutes(20), LocalDateTime.of(2024, 01, 01, 07, 13));
        Task task3 = new Task("Test 3", "Testing task 3",
                TaskStatus.NEW, Duration.ofMinutes(35), LocalDateTime.of(2024, 02, 14, 21, 02));
        URI url = URI.create("http://localhost:8080/tasks");
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(task1)))
                .build();
        HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
        HttpRequest request2 = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(task2)))
                .build();
        HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());
        HttpRequest request3 = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(task3)))
                .build();
        HttpResponse<String> response3 = client.send(request3, HttpResponse.BodyHandlers.ofString());
        Task firstTask = taskManager.getPrioritizedTasks().stream()
                .findFirst()
                .orElse(null);
        assert firstTask != null;
        Assertions.assertEquals("Test 2", Optional.of(firstTask.getTaskName()).orElse("Имя отсутствует"), "Задача не на первом месте");
    }
}

class TaskTypeToken extends TypeToken<List<Task>> {

}
