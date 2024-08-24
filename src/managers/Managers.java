package managers;

import managers.InMemoryHistoryManager;
import managers.InMemoryTaskManager;



import java.io.*;

public class Managers {
    public InMemoryTaskManager getDefault() {

        return new InMemoryTaskManager();
    }

    public FileBackedTaskManager managerWithFile(File file) {

        return new FileBackedTaskManager(file);
    }


    public HistoryManager getDefaultHistory() {

        return new InMemoryHistoryManager();
    }
}
