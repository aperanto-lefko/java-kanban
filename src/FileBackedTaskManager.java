
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class FileBackedTaskManager extends InMemoryTaskManager {

    File fileForWrite;

    public FileBackedTaskManager(File fileForWrite) {
        this.fileForWrite = fileForWrite;
    }

    public void save() {
        try (Writer listOfActions = new FileWriter(fileForWrite)) {
            listOfActions.write(String.format("id,type,name,status,description,epic %n"));
            if (!super.getTaskList().isEmpty() || !super.getEpicList().isEmpty() || !super.getSubtaskList().isEmpty()) {
                List<String> tasks = taskToString();
                for (String task : tasks) {
                    listOfActions.write(String.format(task + "%n"));
                }
                List<String> epics = epicToString();
                for (String epic : epics) {
                    listOfActions.write(String.format(epic + "%n"));
                }
                List<String> subtasks = subtaskToString();
                for (String subtask : subtasks) {
                    listOfActions.write(String.format(subtask + "%n"));
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException();

        }
    }

    public List<String> taskToString() {
        Map<Integer, Task> taskList = super.getTaskList();
        List<String> tasks = new ArrayList<>();
        for (Integer id : taskList.keySet()) {
            Task task = taskList.get(id);
            String taskForWrite = String.format("%d,%s,%s,%s,%s", task.getTaskId(), TaskType.TASK, task.getTaskName(),
                    task.getTaskStatus(), task.getTaskDescription());
            tasks.add(taskForWrite);
        }
        return tasks;
    }

    public List<String> epicToString() {
        Map<Integer, Epic> epicList = super.getEpicList();
        List<String> epics = new ArrayList<>();
        for (Integer id : epicList.keySet()) {
            Epic epic = epicList.get(id);
            String epicForWrite = String.format("%d,%s,%s,%s,%s", epic.getTaskId(), TaskType.EPIC, epic.getTaskName(),
                    epic.getTaskStatus(), epic.getTaskDescription());
            epics.add(epicForWrite);
        }
        return epics;
    }

    public List<String> subtaskToString() {
        Map<Integer, Subtask> subtaskList = super.getSubtaskList();
        List<String> subtasks = new ArrayList<>();
        for (Integer id : subtaskList.keySet()) {
            Subtask subtask = subtaskList.get(id);
            String subtaskForWrite = String.format("%d,%s,%s,%s,%s,%s", subtask.getTaskId(), TaskType.SUBTASK,
                    subtask.getTaskName(), subtask.getTaskStatus(), subtask.getTaskDescription(), subtask.getEpicId());
            subtasks.add(subtaskForWrite);
        }
        return subtasks;
    }

    @Override
    public void add(Task newTask) {
        super.add(newTask);
        save();
    }

    @Override
    public void add(Subtask newSubtask) {
        super.add(newSubtask);
        save();
    }

    @Override
    public void add(Epic newEpic) {
        super.add(newEpic);
        save();
    }

    @Override
    public void removeAllTasks() {
        super.removeAllTasks();
        save();
    }

    @Override
    public void removeAllSubtasks() {
        super.removeAllSubtasks();
        save();
    }

    @Override
    public void removeAllEpics() {
        super.removeAllEpics();
        save();
    }

    @Override
    public void update(Task updatedTask) {
        super.update(updatedTask);
        save();
    }

    @Override
    public void update(Subtask updatedSubtask) {
        super.update(updatedSubtask);
        save();
    }

    @Override
    public void update(Epic updatedEpic) {
        super.update(updatedEpic);
        save();
    }

    @Override
    public void removeTaskById(int idNumber) {
        super.removeTaskById(idNumber);
        save();
    }

    @Override
    public void removeSubtaskById(int idNumber) {
        super.removeSubtaskById(idNumber);
        save();
    }

    @Override
    public void removeEpicById(int idNumber) {
        super.removeEpicById(idNumber);
        save();
    }
}
