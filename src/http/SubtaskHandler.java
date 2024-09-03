package http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import managers.TaskManager;
import taskstype.Subtask;

import java.io.IOException;
import java.util.Optional;

public class SubtaskHandler extends BaseHttpHandler implements HttpHandler {

    public SubtaskHandler(TaskManager taskManager) {
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
                        handleGetSubtasksResponse(ex);
                    } else {
                        if (getId(ex).isPresent()) {
                            handleGetSubtaskByIdResponse(ex, getId(ex).get());
                        } else {
                            sendIncorrectId(ex);
                        }
                    }
                    break;
                case "DELETE":
                    if (getId(ex).isPresent()) {
                        handleDeleteSubtask(ex, getId(ex).get());
                    } else {
                        sendIncorrectId(ex);
                    }
                    break;
                case "POST":
                    handlePostSubtask(ex);
                    break;
                default:
                    sendIncorrectMethod(ex);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleGetSubtasksResponse(HttpExchange ex) {
        try {
            if (taskManager.getSubtaskList().isEmpty()) {
                sendText(ex, "Список задач пуст", 404);
            } else {
                sendText(ex, gson.toJson(taskManager.getSubtaskList()), 200);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleGetSubtaskByIdResponse(HttpExchange ex, int id) throws IOException {
        if (!taskManager.getSubtaskList().containsKey(id)) {
            sendText(ex, "Задача не найдена", 404);
        } else {
            sendText(ex, gson.toJson(taskManager.searchSubtaskById(id)), 200);
        }
    }

    public void handleDeleteSubtask(HttpExchange ex, int id) throws IOException {
        if (!taskManager.getSubtaskList().containsKey(id)) {
            sendText(ex, "Задача не найдена", 404);
        } else {
            taskManager.removeSubtaskById(id);
            sendText(ex, "Подзадача с id " + id + " удалена", 200);
        }
    }

    public void handlePostSubtask(HttpExchange ex) {
        try {
            String exchange = bodyToString(ex);
            if (exchange.isEmpty()) {
                sendText(ex, "Ничего не передано", 400);
            } else {
                Subtask subtask = gson.fromJson(exchange, Subtask.class);
                Optional<Integer> id = Optional.of(subtask.getTaskId());
                if (taskManager.timeOverlayCheck(subtask)) {
                    sendText(ex, "Подзадача не добавлена, так как имеет наложение по времени", 406);
                } else if (id.get() == 0) {
                    taskManager.add(subtask);
                    sendText(ex, "Подзадача добавлена", 201); //добавить тест на проверку задачи
                } else {
                    taskManager.update(subtask);
                    sendText(ex, "Подзадача с id " + id.get() + " обновлена", 201);
                }
            }
        } catch (IOException e) {
            e.getMessage();
        }
    }
}
/* {"taskName":"Купить воду", "taskDescription": "Для толстых котов", "taskStatus": "NEW","epicId": 3, "startTime":"15.10.2024 10:55","duration": 30}*/