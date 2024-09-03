package http;

import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import managers.FileBackedTaskManager;
import managers.InMemoryTaskManager;
import managers.Managers;

import java.awt.dnd.DragSource;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import com.google.gson.Gson;
import managers.TaskManager;

public class BaseHttpHandler {
    TaskManager taskManager;
    protected static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    public BaseHttpHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(Duration.class, new DurationAdapter())
            .create();

    public void sendText(HttpExchange ex, String response, int code) throws IOException {
        ex.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        ex.sendResponseHeaders(code, 0);
        try (OutputStream os = ex.getResponseBody()) {
            os.write(response.getBytes(DEFAULT_CHARSET));
        }
    }

    public void sendIncorrectId(HttpExchange ex) throws IOException {
        ex.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        ex.sendResponseHeaders(400, 0);
        try (OutputStream os = ex.getResponseBody()) {
            os.write("id введен некорректно".getBytes(DEFAULT_CHARSET));
        }
    }

    public void sendIncorrectMethod(HttpExchange ex) throws IOException {
        ex.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        ex.sendResponseHeaders(400, 0);
        try (OutputStream os = ex.getResponseBody()) {
            os.write("Обработка метода не предусмотрена".getBytes(DEFAULT_CHARSET));
        }
    }

    public Optional<Integer> getId(HttpExchange ex) {
        try {
            String[] splitPath = ex.getRequestURI().getPath().split("/");
            return Optional.of(Integer.parseInt(splitPath[2]));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public String bodyToString(HttpExchange ex) throws IOException {
        return new String(ex.getRequestBody().readAllBytes(), DEFAULT_CHARSET);
    }

    public Gson getGson() {
        return gson;
    }
}
