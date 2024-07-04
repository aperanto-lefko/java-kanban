import java.util.ArrayList;
import java.util.HashMap;

public interface TaskManager {
    void add(Task newTask);

    void add(Subtask newSubtask);

    void add(Epic newEpic);

    void printTask();

    void printSubtask();

    void printEpic();

    void printingRequiredEpic(int idNumber);

    void removeAllTasks();

    void removeAllSubtasks();

    void removeAllEpics();

    Task searchTaskById(int idNumber);

    Subtask searchSubtaskById(int idNumber);

    Epic searchEpicById(int idNumber);

    void update(Task updatedTask);

    void update(Subtask updatedSubtask);

    void update(Epic updatedEpic);

    TaskStatus checkingEpicStatus(Epic epic);

    void removeTaskById(int idNumber);

    void removeSubtaskById(int idNumber);

    void removeEpicById(int idNumber);
    void getHistory();
    HashMap<Integer, Task> getTaskList();
    HashMap<Integer, Subtask> getSubtaskList();
   HashMap<Integer, Epic> getEpicList();
    ArrayList<Task> getHistoryList();
    ArrayList<Integer> getIdTaskList();
    ArrayList<Integer> getIdSubtaskList();
    ArrayList<Integer> getIdEpicList();

}
