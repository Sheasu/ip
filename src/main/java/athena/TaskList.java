package athena;

import java.util.ArrayList;

/**
 * Manages the collection of tasks in memory.
 * Provides operations to add, delete, and find tasks.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Initializes the TaskList with an existing list of tasks.
     *
     * @param tasks List of tasks loaded from storage.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Initializes an empty TaskList.
     */
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

    /**
     * Adds a task to the phalanx.
     *
     * @param t The task to be added.
     */
    public void add(Task t) {
        assert t != null;
        tasks.add(t);
    }

    /**
     * Removes a task from the list at the specified index.
     *
     * @param index The zero-based index of the task.
     * @return The task that was removed.
     */
    public Task delete(int index) {
        assert index >= 0 && index < tasks.size();
        return tasks.remove(index);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return The size of the task list.
     */
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

    /**
     * Finds tasks that contain the specified keyword in their description.
     *
     * @param keyword The search term.
     * @return A list of matching tasks.
     */
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
