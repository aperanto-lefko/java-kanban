import java.io.*;

public class Managers {
    public TaskManager getDefault() {

        return new InMemoryTaskManager();
    }

    public FileBackedTaskManager managerWithFile(File file) {

        return new FileBackedTaskManager(file);
    }


    public HistoryManager getDefaultHistory() {

        return new InMemoryHistoryManager();
    }
}
