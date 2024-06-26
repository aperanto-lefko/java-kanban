import java.util.Objects;

class Task {

    private final String taskName;
    private final String taskDescription; //описание

    private int taskId; //идентификационный номер
    private TaskStatus taskStatus;


    Task(String taskName, String taskDescription) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
    }

    Task(String taskName, String taskDescription, TaskStatus taskStatus) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskStatus = taskStatus;
    }

    Task(String taskName, String taskDescription, int idNumber, TaskStatus taskStatus) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskId = idNumber;
        this.taskStatus = taskStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return taskId == task.taskId && Objects.equals(taskName, task.taskName) && Objects.equals(taskDescription, task.taskDescription) && taskStatus == task.taskStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskName, taskDescription, taskId, taskStatus);
    }

    @Override
    public String toString() {
        return "{" +
                "Наименование='" + taskName + '\'' +
                ", описание='" + taskDescription + '\'' +
                ", id=" + taskId +
                ", статус =" + taskStatus +
                '}';
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

}



