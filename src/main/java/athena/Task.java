package athena;

/**
 * Represents a generic task that can be marked as done.
 */
public class Task {
    private static final String ICON_DONE = "X";
    private static final String ICON_NOT_DONE = " ";
    private static final String SAVE_DONE = "1";
    private static final String SAVE_NOT_DONE = "0";
    private static final String SAVE_SEPARATOR = " | ";

    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public void markAsDone() {
        isDone = true;
    }

    public void markAsNotDone() {
        isDone = false;
    }

    public String getStatusIcon() {
        return isDone ? ICON_DONE : ICON_NOT_DONE;
    }

    public String toSaveFormat() {
        String status = isDone ? SAVE_DONE : SAVE_NOT_DONE;
        return status + SAVE_SEPARATOR + description;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
