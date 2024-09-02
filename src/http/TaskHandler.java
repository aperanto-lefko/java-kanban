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
    public void handle(HttpExchange ex) throws IOException {
        String method = ex.getRequestMethod();
        String[] split = ex.getRequestURI().getPath().split("/");
        switch (method) {
            case "GET":
                if (split.length == 2) {
                    handleGetTasksResponse(ex);
                } else {
                    Optional<Integer> id = getId(ex);
                    if (id.isPresent()) {
                        handleGetTaskByIdResponse(ex, id.get());
                    }
                }
                break;
            case "DELETE":
                if (getId(ex).isPresent()) {
                    handleDeleteTask(ex, getId(ex).get()); //сделать тест на удаление
                }
                break;
            case "POST":
                handlePostTask(ex);
                break;
        }
    }


    public void handleGetTasksResponse(HttpExchange ex) throws IOException {

        if (taskManager.getTaskList().isEmpty()) {
            sendText(ex, "Список задач пуст", 404);
        } else {

            sendText(ex, gson.toJson(taskManager.getTaskList().toString()), 200);
        }
    }

    public void handleGetTaskByIdResponse(HttpExchange ex, int id) throws IOException {
        if (!taskManager.getTaskList().containsKey(id)) {
            sendText(ex, "Задача не найдена", 404);
        } else {
            sendText(ex, gson.toJson(taskManager.getTaskList().get(id).toString()), 200);
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

    public void handlePostTask(HttpExchange ex) throws IOException {
        Task task = gson.fromJson(bodyToString(ex), Task.class);
        taskManager.add(task);
        //{"taskName":"Купить кофе", "taskDescription": "Зерновой", "taskStatus": "TaskStatus.NEW" } 2024-10-15T09:50
        sendText(ex, "Задача добавлена", 200);
    }
}
