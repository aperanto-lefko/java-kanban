package http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import managers.TaskManager;

import java.io.IOException;

public class HistoryHandler extends BaseHttpHandler implements HttpHandler {


    public HistoryHandler(TaskManager taskManager) {
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
                        handleGetHistory(ex);
                    }
                    break;
                default:
                    sendIncorrectMethod(ex);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleGetHistory(HttpExchange ex) throws IOException {
        try {
            sendText(ex, gson.toJson(taskManager.getHistory()), 200);
        } catch (NullPointerException e) {
            sendText(ex, "Список просмотра пуст", 404);
        }
    }
}
