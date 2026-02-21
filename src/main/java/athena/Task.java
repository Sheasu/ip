package athena;

/**
 * Represents a generic task in the Athena system.
 * Serves as a base class for Todo, Deadline, and Event tasks.
 */
public class Task {
    private static final String ICON_DONE = "X";
    private static final String ICON_NOT_DONE = " ";
    private static final String SAVE_DONE = "1";
    private static final String SAVE_NOT_DONE = "0";
    private static final String SAVE_SEPARATOR = " | ";

    protected String description;
    protected boolean isDone;

    /**
     * Constructs a new Task with the given description.
     * By default, the task is marked as not done.
     *
     * @param description The content of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Marks the task as completed in the scrolls.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Marks the task as incomplete.
     */
    public void markAsNotDone() {
        isDone = false;
    }

    /**
     * Returns the status icon representing completion.
     *
     * @return "X" if done, a blank space otherwise.
     */
    public String getStatusIcon() {
        return isDone ? ICON_DONE : ICON_NOT_DONE;
    }

    /**
     * Formats the task data for persistent storage.
     *
     * @return A string formatted for the text-based scrolls.
     */
    public String toSaveFormat() {
        String status = isDone ? SAVE_DONE : SAVE_NOT_DONE;
        return status + SAVE_SEPARATOR + description;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
