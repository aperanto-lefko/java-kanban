public class Managers {
    public TaskManager getDefault() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        return taskManager;
    }
    public HistoryManager getDefaultHistory () {
        InMemoryHistoryManager history = new InMemoryHistoryManager();
        return history;
    }
}
