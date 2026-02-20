package athena;

public class Event extends Task {
    private static final String SAVE_TYPE = "E";
    private static final String SAVE_SEPARATOR = " | ";

    protected String from;
    protected String to;

    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toSaveFormat() {
        return SAVE_TYPE + SAVE_SEPARATOR + super.toSaveFormat()
                + SAVE_SEPARATOR + from + SAVE_SEPARATOR + to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
