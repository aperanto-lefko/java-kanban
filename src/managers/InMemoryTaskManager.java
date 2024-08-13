package managers;

import taskstype.Epic;
import taskstype.Subtask;
import taskstype.Task;
import enumlists.TaskStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {

    private int id = 1;


    private Map<Integer, Task> taskList = new HashMap<>();


    private Map<Integer, Subtask> subtaskList = new HashMap<>();
    private Map<Integer, Epic> epicList = new HashMap<>();


    Managers manager = new Managers();
    HistoryManager history = manager.getDefaultHistory();


    public void generateNextId() {
        id++;
    }

    @Override
    public void add(Task newTask) {
        newTask.setTaskId(id);
        generateNextId();
        taskList.put(newTask.getTaskId(), newTask);
    }

    @Override
    public void addWithId(Task newTask) {
        taskList.put(newTask.getTaskId(), newTask);
    }


    @Override
    public void add(Subtask newSubtask) {

        newSubtask.setTaskId(id);
        generateNextId();
        subtaskList.put(newSubtask.getTaskId(), newSubtask); //сохраняем в таблицу задачу с ключом id
        Epic epic = epicList.get(newSubtask.getEpicId()); // берем эпик из мапы с конкретным номером
        List<Integer> subtaskIdUpdated = epic.getSubtaskId();
        subtaskIdUpdated.add(newSubtask.getTaskId()); //добавляем номер подзадачи в список подзадач эпика
        epic.setSubtaskId(subtaskIdUpdated);
        epic.setTaskStatus(checkingEpicStatus(epic));
    }

    @Override
    public void addWithId(Subtask newSubtask) {

        subtaskList.put(newSubtask.getTaskId(), newSubtask); //сохраняем в таблицу задачу с ключом id
        Epic epic = epicList.get(newSubtask.getEpicId()); // берем эпик из мапы с конкретным номером
        List<Integer> subtaskIdUpdated = epic.getSubtaskId();
        subtaskIdUpdated.add(newSubtask.getTaskId()); //добавляем номер подзадачи в список подзадач эпика
        epic.setSubtaskId(subtaskIdUpdated);
        epic.setTaskStatus(checkingEpicStatus(epic));
    }


    @Override
    public void add(Epic newEpic) {
        newEpic.setTaskId(id);
        generateNextId();
        epicList.put(newEpic.getTaskId(), newEpic);
    }

    @Override
    public void addWithId(Epic newEpic) {
        epicList.put(newEpic.getTaskId(), newEpic);
    }


    @Override
    public void printTask() {
        if (!taskList.isEmpty()) {
            for (Integer id : taskList.keySet()) {
                System.out.println("Задача: идентификационный номер: " + id);
                System.out.println(taskList.get(id));
            }
        } else {
            System.out.println("Список задач пуст");
        }
    }

    @Override
    public void printSubtask() {
        if (!subtaskList.isEmpty()) {
            for (Integer id : subtaskList.keySet()) {
                System.out.println("Подзадача: идентификационный номер: " + id);
                System.out.println(subtaskList.get(id));
            }
        } else {
            System.out.println("Список подзадач пуст");
        }
    }

    @Override
    public void printEpic() {
        for (Integer id : epicList.keySet()) {
            System.out.println("Эпик: идентификационный номер: " + id);
            System.out.println("Наименование эпика: " + epicList.get(id));
            System.out.println("Список подзадач: ");
            Epic epic = epicList.get(id); // берем эпик из мапы с конкретным номером
            List<Integer> idSubtaskList = epic.getSubtaskId();
            for (int subtaskId : idSubtaskList) {
                System.out.println(subtaskList.get(subtaskId));
            }
        }
    }

    @Override
    public void printingRequiredEpic(int idNumber) {
        System.out.println("Наименование эпика: " + epicList.get(idNumber));
        System.out.println("Список подзадач: ");
        Epic epic = epicList.get(idNumber); // берем эпик из мапы с конкретным номером
        List<Integer> idSubtaskList = epic.getSubtaskId();
        for (int subtaskId : idSubtaskList) {
            System.out.println(subtaskList.get(subtaskId));
        }
    }

    @Override
    public void removeAllTasks() {
        taskList.clear();
    }

    @Override
    public void removeAllSubtasks() {
        subtaskList.clear();
    }

    @Override
    public void removeAllEpics() {
        epicList.clear();
    }

    @Override
    public Task searchTaskById(int idNumber) {
        if (!taskList.isEmpty()) {
            System.out.println("Задача с id " + idNumber + ": " + taskList.get(idNumber));
            history.add(taskList.get(idNumber));

            return taskList.get(idNumber);
        } else {
            System.out.println("Список задач пуст");
        }
        return null;
    }


    @Override
    public Subtask searchSubtaskById(int idNumber) {
        if (!subtaskList.isEmpty()) {
            System.out.println("Подзадача с id " + idNumber + ": " + subtaskList.get(idNumber));
            history.add(subtaskList.get(idNumber));
            return subtaskList.get(idNumber);
        } else {
            System.out.println("Список задач пуст");
        }
        return null;
    }

    @Override
    public Epic searchEpicById(int idNumber) {
        if (!epicList.isEmpty()) {
            System.out.println("Эпик с id " + idNumber + ": " + epicList.get(idNumber));
            history.add(epicList.get(idNumber));
            return epicList.get(idNumber);
        } else {
            System.out.println("Список задач пуст");
        }
        return null;
    }

    @Override
    public void update(Task updatedTask) {
        int updatedTaskId = updatedTask.getTaskId();
        if (subtaskList.containsKey(updatedTaskId) || epicList.containsKey(updatedTaskId)) { //проверяем, чтобы не было пересечений по id подзадач и эпиков
            System.out.println("Задача не может быть обновлена с данным id");
        } else {
            taskList.put(updatedTask.getTaskId(), updatedTask);
        }
    }

    @Override
    public void update(Subtask updatedSubtask) {
        int updatedSubtaskId = updatedSubtask.getTaskId();
        if (taskList.containsKey(updatedSubtaskId) || epicList.containsKey(updatedSubtaskId)) {
            System.out.println("Подзадача не может быть обновлена с данным id");
        } else {
            subtaskList.put(updatedSubtask.getTaskId(), updatedSubtask); //заменили в хэштаблице обновленную задачу
            Epic epic = epicList.get(updatedSubtask.getEpicId());
            epic.setTaskStatus(checkingEpicStatus(epic));
        }
    }

    @Override
    public void update(Epic updatedEpic) {
        int updatedEpicId = updatedEpic.getTaskId();
        List<Integer> updatedEpicSubtaskList = updatedEpic.getSubtaskId();
        if (taskList.containsKey(updatedEpicId) || subtaskList.containsKey(updatedEpicId)) {
            System.out.println("Эпик не может быть обновлен с данным id");
        } else if (updatedEpicSubtaskList.contains(updatedEpicId)) {
            System.out.println("Эпик не может быть обновлен с данными id подзадач");
        } else {
            epicList.put(updatedEpic.getTaskId(), updatedEpic);
            updatedEpic.setTaskStatus(checkingEpicStatus(updatedEpic));
        }
    }

    @Override
    public TaskStatus checkingEpicStatus(Epic epic) {
        int sumNewStatus = 0;
        int sumDoneStatus = 0;
        TaskStatus taskStatus;
        for (int id : epic.getSubtaskId()) {
            Subtask subtask = subtaskList.get(id);
            if (subtask.getTaskStatus() == TaskStatus.NEW) {
                sumNewStatus++;
            } else if (subtask.getTaskStatus() == TaskStatus.DONE) {
                sumDoneStatus++;
            }
        }
        if (sumNewStatus == epic.getSubtaskId().size()) {
            taskStatus = TaskStatus.NEW;
        } else if (sumDoneStatus == epic.getSubtaskId().size()) {
            taskStatus = TaskStatus.DONE;
        } else {
            taskStatus = TaskStatus.IN_PROGRESS;
        }
        return taskStatus;
    }

    @Override
    public void removeTaskById(int idNumber) {
        if (!taskList.isEmpty()) {
            taskList.remove(idNumber);
            history.remove(idNumber); //новая строка удаляем из истории просмотра
            System.out.println("Задача с id " + idNumber + ": " + "удалена");

        } else {
            System.out.println("Список задач пуст");
        }
    }

    @Override
    public void removeSubtaskById(int idNumber) {
        Subtask removeSubtask = subtaskList.get(idNumber); //получаем из общего списка подзадачу
        Epic epic = epicList.get(removeSubtask.getEpicId()); //получаем сначала номер эпика, затем сам эпик
        int index = epic.getSubtaskId().indexOf(idNumber); //берем индекс в списке номеров подзадач
        if (!subtaskList.isEmpty()) {
            List<Integer> subtaskIdUpdated = epic.getSubtaskId(); //берем список подзадач конкретного эпика
            subtaskIdUpdated.remove(index); //по индексу удаляем подзадачу
            epic.setSubtaskId(subtaskIdUpdated); //сохраняем новый список подзадач
            subtaskList.remove(idNumber); //удаляем дополнительно из общего списка подзадач подзадачу с id-номером
            history.remove(idNumber); //новая строка удаляем из истории просмотра
            System.out.println("Подзадача с id " + idNumber + ": " + "удалена");
            epic.setTaskStatus(checkingEpicStatus(epic));
        } else {
            System.out.println("Список задач пуст");
        }
    }

    @Override
    public void removeEpicById(int idNumber) {
        if (!epicList.isEmpty()) {
            epicList.remove(idNumber);
            history.remove(idNumber); //новая строка удаляем из истории просмотра
            System.out.println("Эпик с id " + idNumber + ": " + "удалена");

        } else {
            System.out.println("Список задач пуст");
        }
    }

    @Override
    public void printHistory() {
        history.printHistory();
    }

    @Override
    public List<Task> getHistory() {
        return history.getHistory();
    }

    @Override
    public Map<Integer, Task> getTaskList() {
        return taskList;
    }

    public Map<Integer, Subtask> getSubtaskList() {
        return subtaskList;
    }

    public Map<Integer, Epic> getEpicList() {
        return epicList;
    }


}



