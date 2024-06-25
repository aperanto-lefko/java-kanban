import java.util.ArrayList;

class Epic extends Task {

    ArrayList<Integer> subtaskId; //список номеров подзадач

    public Epic(String taskName, String taskDescription, TaskStatus taskStatus) {
        super(taskName, taskDescription, taskStatus);
        subtaskId = new ArrayList<>();
    }

    public Epic(String taskName, String taskDescription, int idNumber, TaskStatus taskStatus, ArrayList<Integer> subtaskId) {
        super(taskName, taskDescription, idNumber, taskStatus);
        this.subtaskId = subtaskId;
    }

}
