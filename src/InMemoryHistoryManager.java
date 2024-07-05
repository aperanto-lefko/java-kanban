import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    List<Task> listOfViewedTasks = new ArrayList<>();

    @Override
    public void add(Task task) {
        if (listOfViewedTasks.size() < 10) {
            listOfViewedTasks.add(task);
        } else {
            listOfViewedTasks.remove(0);
            listOfViewedTasks.add(task);
        }
    }

    @Override
    public List<Task> getHistory() {
        return listOfViewedTasks;
    }

    @Override
    public void printHistory() {
        System.out.println("История просмотра");
        for (Task task : listOfViewedTasks) {
            System.out.println(task);
        }

    }
}
