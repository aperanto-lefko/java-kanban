package http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import managers.TaskManager;
import taskstype.Epic;

import java.io.IOException;
import java.util.Optional;

public class EpicHandler extends BaseHttpHandler implements HttpHandler {
    public EpicHandler(TaskManager taskManager) {
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
                        handleGetEpicsResponse(ex);
                    } else {
                        if (getId(ex).isPresent()) {
                            handleGetEpicByIdResponse(ex, getId(ex).get());
                        } else {
                            sendIncorrectId(ex);
                        }
                    }
                    break;
                case "DELETE":
                    if (getId(ex).isPresent()) {
                        handleDeleteEpic(ex, getId(ex).get()); //сделать тест на удаление
                    } else {
                        sendIncorrectId(ex);
                    }
                    break;
                case "POST":
                    handlePostEpic(ex);
                    break;
                default:
                    sendIncorrectMethod(ex);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleGetEpicsResponse(HttpExchange ex) {
        try {
            if (taskManager.getEpicList().isEmpty()) {
                sendText(ex, "Список эпиков пуст", 404);
            } else {
                sendText(ex, gson.toJson(taskManager.getEpicList()), 200);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleGetEpicByIdResponse(HttpExchange ex, int id) throws IOException {
        if (!taskManager.getEpicList().containsKey(id)) {
            sendText(ex, "Эпик не найден", 404);
        } else {
            sendText(ex, gson.toJson(taskManager.searchEpicById(id)), 200);
        }
    }

    public void handleDeleteEpic(HttpExchange ex, int id) throws IOException {
        if (!taskManager.getEpicList().containsKey(id)) {
            sendText(ex, "Эпик не найден", 404);
        } else {
            taskManager.removeEpicById(id);
            sendText(ex, "Эпик с id " + id + " удален", 200);
        }
    }

    public void handlePostEpic(HttpExchange ex) {
        try {
            String exchange = bodyToString(ex);
            if (exchange.isEmpty()) {
                sendText(ex, "Ничего не передано", 400);
            } else {
                Epic epic = gson.fromJson(exchange, Epic.class);
                Optional<Integer> id = Optional.of(epic.getTaskId());
                if (id.get() == 0) {
                    taskManager.add(epic);
                    sendText(ex, "Эпик добавлен", 201); //добавить тест на проверку задачи
                } else {
                    taskManager.update(epic);
                    sendText(ex, "Эпик с id " + id.get() + " обновлен", 201);
                }
            }
        } catch (IOException e) {
            e.getMessage();
        }
    }
}
