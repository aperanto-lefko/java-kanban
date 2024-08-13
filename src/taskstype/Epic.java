package taskstype;

import java.util.ArrayList;
import java.util.List;
import enumlists.TaskStatus;

public class Epic extends Task {


    private List<Integer> subtaskId; //список номеров подзадач

    public Epic(String taskName, String taskDescription, TaskStatus taskStatus) {
        super(taskName, taskDescription, taskStatus);
        subtaskId = new ArrayList<>();
    }

    public Epic(String taskName, String taskDescription, int idNumber, TaskStatus taskStatus, ArrayList<Integer> subtaskId) {
        super(taskName, taskDescription, idNumber, taskStatus);
        this.subtaskId = subtaskId;
    }

    public List<Integer> getSubtaskId() {
        return subtaskId;
    }

    public void setSubtaskId(List<Integer> subtaskId) {
        this.subtaskId = subtaskId;
    }
}
