package fr.todooz.domain;

import java.util.List;

public class Tasks {
    private List<Task> tasks;

    public Tasks() {
    }

    public Tasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

}
