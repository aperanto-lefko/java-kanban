package http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import managers.TaskManager;
import taskstype.Task;

import java.io.IOException;
import java.util.Optional;

public class TaskHandler extends BaseHttpHandler implements HttpHandler {


    public TaskHandler(TaskManager taskManager) {
        super(taskManager);
    }

    @Override
    public void handle(HttpExchange ex) {
        String method = ex.getRequestMethod();
        String[] split = ex.getRequestURI().getPath().split("/");
        try {
            switch (method) {
                case "GET":
                    if (split.length == 2) {
                        handleGetTasksResponse(ex);
                    } else {
                        if (getId(ex).isPresent()) {
                            handleGetTaskByIdResponse(ex, getId(ex).get());
                        } else {
                            sendIncorrectId(ex);
                        }
                    }
                    break;
                case "DELETE":
                    if (getId(ex).isPresent()) {
                        handleDeleteTask(ex, getId(ex).get());
                    } else {
                        sendIncorrectId(ex);
                    }
                    break;
                case "POST":
                    handlePostTask(ex);
                    break;
                default:
                    sendIncorrectMethod(ex);
                    break;
            }
        } catch (IOException e) {
            System.out.println("Во время выполнения запроса произошла ошибка. Проверьте URL");
        }
    }


    public void handleGetTasksResponse(HttpExchange ex) {
        try {
            if (taskManager.getTaskList().isEmpty()) {
                sendText(ex, "Список задач пуст", 404);
            } else {
                sendText(ex, gson.toJson(taskManager.getTaskList()), 200);
            }
        } catch (IOException e) {
            System.out.println("Во время выполнения запроса произошла ошибка. Проверьте URL");
        }
    }


    public void handleGetTaskByIdResponse(HttpExchange ex, int id) throws IOException {
        if (!taskManager.getTaskList().containsKey(id)) {
            sendText(ex, "Задача не найдена", 404);
        } else {
            sendText(ex, gson.toJson(taskManager.searchTaskById(id)), 200);
        }
    }

    public void handleDeleteTask(HttpExchange ex, int id) throws IOException {
        if (!taskManager.getTaskList().containsKey(id)) {
            sendText(ex, "Задача не найдена", 404);
        } else {
            taskManager.removeTaskById(id);
            sendText(ex, "Задача с id " + id + " удалена", 200);
        }
    }

    public void handlePostTask(HttpExchange ex) {
        try {
            String exchange = bodyToString(ex);
            if (exchange.isEmpty()) {
                sendText(ex, "Ничего не передано", 400);
            } else {
                Task task = gson.fromJson(exchange, Task.class);
                Optional<Integer> id = Optional.of(task.getTaskId());
                if (taskManager.timeOverlayCheck(task)) {
                    sendText(ex, "Задача не добавлена, так как имеет наложение по времени", 406);
                } else if (id.get() == 0) {
                    taskManager.add(task);
                    sendText(ex, "Задача добавлена", 201); //добавить тест на проверку задачи
                } else {
                    taskManager.update(task);
                    sendText(ex, "Задача с id " + id.get() + " обновлена", 201);
                }
            }
        } catch (IOException e) {
            System.out.println("Во время выполнения запроса произошла ошибка. Проверьте URL");
        }
    }
}
/* Задача для проверки {"taskName":"Купить кофе", "taskDescription": "Зерновой", "taskStatus": "NEW" }
                       {"taskName":"Купить корм", "taskDescription": "Для толстых котов", "taskStatus": "NEW",
                        "taskId": 6, "startTime":"12.01.2024 15:33","duration": 30}*/

