import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    static int nextId = 1;
    HashMap<Integer, Task> taskList = new HashMap<>();
    HashMap<Integer, Subtask> subtaskList = new HashMap<>();
    HashMap<Integer, Epic> epicList = new HashMap<>();

    private void generateNextId() {
        nextId++;
    }

    public void add(Task newTask) {
        newTask.setTaskId(nextId);
        generateNextId();
        taskList.put(newTask.getTaskId(), newTask);
    }

    public void add(Subtask newSubtask) {

        newSubtask.setTaskId(nextId);
        generateNextId();
        subtaskList.put(newSubtask.getTaskId(), newSubtask); //сохраняем в таблицу задачу с ключом id
        Epic epic = epicList.get(newSubtask.getEpicId()); // берем эпик из мапы с конкретным номером
        epic.subtaskId.add(newSubtask.getTaskId()); //и добавляем номер подзадачи в список подзадач эпика
        epic.setTaskStatus(checkingEpicStatus(epic));
    }

    public void add(Epic newEpic) {
        newEpic.setTaskId(nextId);
        generateNextId();
        epicList.put(newEpic.getTaskId(), newEpic);
    }

    public void printTask() {
        if (!taskList.isEmpty()) {
            for (Integer id : taskList.keySet()) {
                System.out.println("Идентификационный номер: " + id);
                System.out.println(taskList.get(id));
            }
        } else {
            System.out.println("Список задач пуст");
        }
    }

    public void printSubtask() {
        if (!subtaskList.isEmpty()) {
            for (Integer id : subtaskList.keySet()) {
                System.out.println("Идентификационный номер: " + id);
                System.out.println(subtaskList.get(id));
            }
        } else {
            System.out.println("Список подзадач пуст");
        }
    }

    public void printEpic() {
        for (Integer id : epicList.keySet()) {
            System.out.println("Идентификационный номер: " + id);
            System.out.println("Наименование: " + epicList.get(id));
            System.out.println("Список подзадач: ");
            Epic epic = epicList.get(id); // берем эпик из мапы с конкретным номером
            ArrayList<Integer> idSubtaskList = epic.subtaskId;
            for (int subtaskId : idSubtaskList) {
                System.out.println(subtaskList.get(subtaskId));
            }
        }
    }

    public void printingRequiredEpic(int idNumber) {
        System.out.println("Наименование: " + epicList.get(idNumber));
        System.out.println("Список подзадач: ");
        Epic epic = epicList.get(idNumber); // берем эпик из мапы с конкретным номером
        ArrayList<Integer> idSubtaskList = epic.subtaskId;
        for (int subtaskId : idSubtaskList) {
            System.out.println(subtaskList.get(subtaskId));
        }
    }

    public void removeAllTasks() {
        taskList.clear();
    }

    public void removeAllSubtasks() {
        subtaskList.clear();
    }

    public void removeAllEpics() {
        epicList.clear();
    }

    public void searchTaskById(int idNumber) {
        if (!taskList.isEmpty()) {
            System.out.println("Задача с id " + idNumber + ": " + taskList.get(idNumber));
        } else {
            System.out.println("Список задач пуст");
        }
    }

    public void searchSubtaskById(int idNumber) {
        if (!subtaskList.isEmpty()) {
            System.out.println("Задача с id " + idNumber + ": " + subtaskList.get(idNumber));
        } else {
            System.out.println("Список задач пуст");
        }
    }

    public void searchEpicById(int idNumber) {
        if (!epicList.isEmpty()) {
            System.out.println("Эпик с id " + idNumber + ": " + epicList.get(idNumber));
        } else {
            System.out.println("Список задач пуст");
        }
    }

    public void update(Task updatedTask) {
        taskList.put(updatedTask.getTaskId(), updatedTask);
    }

    public void update(Subtask updatedSubtask) {
        subtaskList.put(updatedSubtask.getTaskId(), updatedSubtask); //заменили в хэштаблице обновленную задачу
        Epic epic = epicList.get(updatedSubtask.getEpicId());
        epic.setTaskStatus(checkingEpicStatus(epic));
    }

    public void update(Epic updatedEpic) {
        epicList.put(updatedEpic.getTaskId(), updatedEpic);
        updatedEpic.setTaskStatus(checkingEpicStatus(updatedEpic));
    }

    public TaskStatus checkingEpicStatus(Epic epic) {
        int sumNewStatus = 0;
        int sumDoneStatus = 0;
        TaskStatus taskStatus;
        for (int id : epic.subtaskId) {
            Subtask subtask = subtaskList.get(id);
            if (subtask.getTaskStatus() == TaskStatus.NEW) {
                sumNewStatus++;
            } else if (subtask.getTaskStatus() == TaskStatus.DONE) {
                sumDoneStatus++;
            }
        }
        if (sumNewStatus == epic.subtaskId.size()) {
            taskStatus = TaskStatus.NEW;
        } else if (sumDoneStatus == epic.subtaskId.size()) {
            taskStatus = TaskStatus.DONE;
        } else {
            taskStatus = TaskStatus.IN_PROGRESS;
            ;
        }
        return taskStatus;
    }

    public void removeTaskById(int idNumber) {
        if (!taskList.isEmpty()) {
            taskList.remove(idNumber);
            System.out.println("Задача с id " + idNumber + ": " + "удалена");

        } else {
            System.out.println("Список задач пуст");
        }
    }

    public void removeSubtaskById(int idNumber) {
        if (!subtaskList.isEmpty()) {
            subtaskList.remove(idNumber);
            System.out.println("Подзадача с id " + idNumber + ": " + "удалена");

        } else {
            System.out.println("Список задач пуст");
        }
    }

    public void removeEpicById(int idNumber) {
        if (!epicList.isEmpty()) {
            epicList.remove(idNumber);
            System.out.println("Эпик с id " + idNumber + ": " + "удалена");

        } else {
            System.out.println("Список задач пуст");
        }
    }



/* a. Получение списка всех задач. +
 b. Удаление всех задач. +
 c. Получение по идентификатору.+
 d. Создание. Сам объект должен передаваться в качестве параметра. +
 e. Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.
 f. Удаление по идентификатору.+
Дополнительные методы:
a. Получение списка всех подзадач определённого эпика.+
*/


}
