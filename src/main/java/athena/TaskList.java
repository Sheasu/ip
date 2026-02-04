package athena;

import java.util.ArrayList;

/**
 * Contains the task list and has operations to add/delete tasks in the list.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public ArrayList<Task> getAllTasks() {
        return tasks;
    }

    public Task getTask(int index) {
        return tasks.get(index);
    }

    public void add(Task t) {
        tasks.add(t);
    }

    public Task delete(int index) {
        return tasks.remove(index);
    }

    public int getSize() {
        return tasks.size();
    }
}