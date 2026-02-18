package athena;

/**
 * Deals with making sense of the user command.
 */
public class Parser {

    /**
     * Extracts the command word from the user input.
     *
     * @param input Full raw user input string.
     * @return The first word of the input string.
     */
    public static String getCommandWord(String input) {
        return input.trim().split(" ")[0];
    }

    /**
     * Parses the task index from commands that target a specific task.
     *
     * @param input Full raw user input string.
     * @return The zero-based index of the task.
     */
    public static int parseIndex(String input) {
        String[] parts = input.split(" ");
        return Integer.parseInt(parts[1]) - 1;
    }

    /**
     * Extracts the description for a todo task.
     *
     * @param input Full raw user input string.
     * @return Description of the todo.
     * @throws AthenaException If the description is empty.
     */
    public static String parseTodoDescription(String input) throws AthenaException {
        if (input.trim().length() <= 4) {
            throw new AthenaException("The description of a todo cannot be empty.");
        }
        return input.substring(5).trim();
    }

    /**
     * Parses the input to extract description and date/time for a deadline.
     *
     * @param input Full raw user input string.
     * @return A string array where index 0 is the description and index 1 is the deadline time.
     * @throws AthenaException If the /by keyword is missing or description is empty.
     */
    public static String[] parseDeadline(String input) throws AthenaException {
        int byIdx = input.indexOf(" /by ");
        if (byIdx == -1) {
            throw new AthenaException("A deadline must have a /by time.");
        }
        String desc = input.substring(9, byIdx).trim();
        String by = input.substring(byIdx + 5).trim();
        return new String[]{desc, by};
    }

    /**
     * Parses the input to extract description and the start/end times for an event.
     *
     * @param input Full raw user input string.
     * @return A string array where index 0 is description, index 1 is start time, and index 2 is end time.
     * @throws AthenaException If /from or /to keywords are missing.
     */
    public static String[] parseEvent(String input) throws AthenaException {
        int fromIdx = input.indexOf(" /from ");
        int toIdx = input.indexOf(" /to ");
        if (fromIdx == -1 || toIdx == -1) {
            throw new AthenaException("An event must have /from and /to times.");
        }
        String desc = input.substring(6, fromIdx).trim();
        String from = input.substring(fromIdx + 7, toIdx).trim();
        String to = input.substring(toIdx + 5).trim();
        return new String[]{desc, from, to};
    }

    /**
     * Parses the search keyword from the find command.
     *
     * @param input Full raw user input string.
     * @return The keyword to search for.
     * @throws AthenaException If the keyword is missing.
     */
    public static String parseFindKeyword(String input) throws AthenaException {
        String trimmed = input.trim();
        if (trimmed.length() <= 5) {
            throw new AthenaException("Please specify a keyword to find.");
        }
        return trimmed.substring(5).trim();
    }
}
