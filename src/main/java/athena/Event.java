package athena;

/**
 * Represents a task that starts at a specific time and ends at a specific time.
 */
public class Event extends Task {
    protected String from;
    protected String to;

    /**
     * Initializes an event task with a description and its duration.
     *
     * @param description Brief description of the event.
     * @param from The start time/date of the event.
     * @param to The end time/date of the event.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toSaveFormat() {
        return "E | " + super.toSaveFormat() + " | " + from + " | " + to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
