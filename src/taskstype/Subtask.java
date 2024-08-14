package taskstype;
import enumlists.TaskStatus;

public class Subtask extends Task {


    private int epicId; //номер эпика

    public Subtask(String taskName, String taskDescription, TaskStatus taskStatus, int epicId) {
        super(taskName, taskDescription, taskStatus);
        this.epicId = epicId;
    }

    public Subtask(String taskName, String taskDescription, int idNumber, TaskStatus taskStatus, int epicId) {
        super(taskName, taskDescription, idNumber, taskStatus);
        this.epicId = epicId;
    }

    public Subtask(String taskName, String taskDescription, TaskStatus taskStatus, int idNumber, int epicId) {
        super(taskName, taskDescription, idNumber, taskStatus);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }
}
