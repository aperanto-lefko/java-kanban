package taskstype;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

import enumlists.TaskStatus;

public class Task {


    private String taskName;



    private String taskDescription; //описание

    private int taskId; //идентификационный номер
    private TaskStatus taskStatus;


    private Duration duration;


    private LocalDateTime startTime;


    public Task(String taskName, String taskDescription, TaskStatus taskStatus) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskStatus = taskStatus;
    }

    public Task(String taskName, String taskDescription, TaskStatus taskStatus, Duration duration, LocalDateTime startTime) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskStatus = taskStatus;
        this.duration = duration;
        this.startTime = startTime;
    }

    public Task(String taskName, String taskDescription, int idNumber, TaskStatus taskStatus) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskId = idNumber;
        this.taskStatus = taskStatus;
    }

    public Task(String taskName, String taskDescription, int idNumber, TaskStatus taskStatus, Duration duration, LocalDateTime startTime) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskId = idNumber;
        this.taskStatus = taskStatus;
        this.duration = duration;
        this.startTime = startTime;
    }

    public Task(String taskName, String taskDescription, int idNumber, TaskStatus taskStatus, LocalDateTime startTime) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskId = idNumber;
        this.taskStatus = taskStatus;
        this.startTime = startTime;
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
                ", старт =" + startTime +
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

    public String getTaskName() {
        return taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        if (checkStartTime()) {
            return startTime.plus(duration);
        } else return null;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public boolean checkStartTime() {
        return startTime != null;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }
}




