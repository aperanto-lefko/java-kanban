import java.io.*;

public class Managers {
    public TaskManager getDefault() {

        return new InMemoryTaskManager();
    }

    public FileBackedTaskManager managerWithFile() throws IOException {
        File file = new File("fileBacked.csv");
        return new FileBackedTaskManager(file);
    }

    public HistoryManager getDefaultHistory() {

        return new InMemoryHistoryManager();
    }
}
