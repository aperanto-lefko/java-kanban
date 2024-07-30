import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    HistoryMap historyMap = new HistoryMap<>();


    @Override
    public void add(Task task) {
        historyMap.put(task.getTaskId(), task);
    }

    @Override //удаление из истории просмотра
    public void remove(int id) {
        historyMap.removeFromHistory(id);
    }


    @Override
    public List<Task> getHistory() {
        return historyMap.printHistoryMap();
    }

    @Override
    public void printHistory() {

        System.out.println("История просмотра");

        for (Object task : historyMap.printHistoryMap()) {
            System.out.println(task);
        }

    }
}
