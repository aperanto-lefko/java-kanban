package http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import managers.TaskManager;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {
    private static final int PORT = 8080;
    TaskManager taskManager;

    public HttpTaskServer(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    private HttpServer httpServer;


    public void createHttpServer() throws IOException {
        httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);

        httpServer.createContext("/tasks", new TaskHandler(taskManager));
        // httpServer.createContext("/subtasks", new SubtaskHandler());
        // httpServer.createContext("/epics", new EpicHandler());
        // httpServer.createContext("/history", new HistoryHandler());
        //httpServer.createContext("/prioritized", new PriorityHandler());

    }

    public void start() {
        httpServer.start();
        System.out.println("HTTP-сервер запущен на " + PORT + " порту");
    }

}