package managers;

import taskstype.Epic;
import taskstype.Subtask;
import taskstype.Task;
import enumlists.TaskStatus;

import java.util.List;
import java.util.Map;

public interface TaskManager {
    void add(Task newTask);
    void addWithId(Task newTask);

    void add(Subtask newSubtask);

    void addWithId(Subtask newSubtask);

    void add(Epic newEpic);

    void addWithId (Epic newEpic);

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


    Map<Integer, Task> getTaskList();

    Map<Integer, Subtask> getSubtaskList();

    Map<Integer, Epic> getEpicList();

    void printHistory();

    List<Task> getHistory();

}
