package managers;

import taskstype.Task;

import java.util.List;

public interface HistoryManager {

    void add(Task task);

    void remove(int id);

    List<Task> getHistory();

    void printHistory();

    void put(Integer key, Task value);

    List<Task> printHistoryMap();

    void removeFromHistory(Integer key);

}
