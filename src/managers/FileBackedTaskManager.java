package managers;

import enumlists.TaskStatus;
import enumlists.TaskType;
import exceptions.ManagerSaveException;
import taskstype.Epic;
import taskstype.Subtask;
import taskstype.Task;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class FileBackedTaskManager extends InMemoryTaskManager {

    File fileForWrite;
    final String NAME_LIST = String.format("id,type,name,status,description,epic %n");
    public FileBackedTaskManager(File fileForWrite) {
        this.fileForWrite = fileForWrite;
    }

    public void save() {
        try (Writer listOfActions = new FileWriter(fileForWrite)) {
            listOfActions.write(NAME_LIST);
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

    public static FileBackedTaskManager loadFromFile(File file) {
        Managers manager = new Managers();
        FileBackedTaskManager taskManager = manager.managerWithFile(file);
        try (FileReader reader = new FileReader(file)) {
            BufferedReader br = new BufferedReader(reader);
            List<String> tasksList = new ArrayList<>();
            while (br.ready()) {
                String line = br.readLine();
                tasksList.add(line);
            }
            tasksList.remove("id,type,name,status,description,epic ");
            for (String string : tasksList) {
                System.out.println("Проверка списка" + string);
                String[] tasksLine = string.split(",");
                TaskStatus status = null;
                String taskStatus = tasksLine[3]; //определение статуса
                switch (taskStatus) {
                    case "NEW":
                        status = TaskStatus.NEW;
                        break;
                    case "IN_PROGRESS":
                        status = TaskStatus.IN_PROGRESS;
                        break;
                    case "DONE":
                        status = TaskStatus.DONE;
                        break;
                }
                int id = Integer.parseInt(tasksLine[0]);
                for (String value : tasksLine) {
                    switch (value) {
                        case "TASK":
                            Task task = new Task(tasksLine[2], tasksLine[4], id, status);
                            taskManager.addWithId(task);
                            break;
                        case "EPIC":
                            Epic epic = new Epic(tasksLine[2], tasksLine[4], id, status);
                            taskManager.addWithId(epic);
                            break;
                        case "SUBTASK":
                            int epicId = Integer.parseInt(tasksLine[5]);
                            Subtask subtask = new Subtask(tasksLine[2], tasksLine[4], id, status, epicId);
                            taskManager.addWithId(subtask);
                    }
                }
            }
            return taskManager;
        } catch (IOException e) {
            throw new ManagerSaveException();
        }
    }

    @Override
    public void add(Task newTask) {
        super.add(newTask);
        save();
    }
    @Override
    public void addWithId(Task newTask) {
        super.add(newTask);
        save();
    }

    @Override
    public void add(Subtask newSubtask) {
        super.add(newSubtask);
        save();
    }
    @Override
    public void addWithId(Subtask newSubtask) {
        super.add(newSubtask);
        save();
    }

    @Override
    public void add(Epic newEpic) {
        super.add(newEpic);
        save();
    }
    @Override
    public void addWithId(Epic newEpic) {
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
