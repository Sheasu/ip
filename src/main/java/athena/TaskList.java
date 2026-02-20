package athena;

import java.util.ArrayList;

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
        assert index >= 0 && index < tasks.size();
        return tasks.get(index);
    }

    public void add(Task t) {
        assert t != null;
        tasks.add(t);
    }

    public Task delete(int index) {
        assert index >= 0 && index < tasks.size();
        return tasks.remove(index);
    }

    public int getSize() {
        return tasks.size();
    }

    public String getFormattedList() {
        StringBuilder out = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            out.append(i + 1).append(".").append(tasks.get(i));
            if (i < tasks.size() - 1) {
                out.append("\n");
            }
        }
        return out.toString();
    }

    public ArrayList<Task> findTasks(String keyword) {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.toString().toLowerCase().contains(keyword.toLowerCase())) {
                matchingTasks.add(task);
            }
        }
        return matchingTasks;
    }
}
