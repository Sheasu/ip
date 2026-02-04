package athena;

/**
 * Represents a basic task without any specific date or time constraints.
 */
public class Todo extends Task {

    /**
     * Constructs a new Todo task with the specified description.
     *
     * @param description The content of the todo task.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Formats the todo task for storage in the text file.
     *
     * @return String representation in save format prefixed with "T".
     */
    @Override
    public String toSaveFormat() {
        return "T | " + super.toSaveFormat();
    }

    /**
     * Returns a string representation of the todo task, including its type and status.
     *
     * @return A string formatted as "[T][Status] description".
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}