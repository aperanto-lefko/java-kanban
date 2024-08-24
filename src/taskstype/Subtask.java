package taskstype;
import enumlists.TaskStatus;

import java.time.Duration;
import java.time.LocalDateTime;

public class Subtask extends Task {


    private int epicId; //номер эпика

    public Subtask(String taskName, String taskDescription, TaskStatus taskStatus, int epicId) {
        super(taskName, taskDescription, taskStatus);
        this.epicId = epicId;
    }

    public Subtask(String taskName, String taskDescription, TaskStatus taskStatus, int epicId, Duration duration, LocalDateTime startTime) {
        super(taskName, taskDescription, taskStatus, duration, startTime);
        this.epicId = epicId;
    }

    public Subtask(String taskName, String taskDescription, TaskStatus taskStatus, int idNumber, int epicId) {
        super(taskName, taskDescription, idNumber, taskStatus);
        this.epicId = epicId;
    }

    public Subtask(String taskName, String taskDescription, TaskStatus taskStatus, int idNumber, int epicId, Duration duration, LocalDateTime startTime) {
        super(taskName, taskDescription, idNumber, taskStatus, duration, startTime);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }
}
