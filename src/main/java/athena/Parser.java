package athena;

/**
 * Provides methods to parse raw user input into meaningful components.
 */
public class Parser {
    private static final String SPACE_DELIMITER = " ";
    private static final String BY_KEYWORD = " /by ";
    private static final String FROM_KEYWORD = " /from ";
    private static final String TO_KEYWORD = " /to ";

    /**
     * Extracts the command word from the user input.
     *
     * @param input Raw user input.
     * @return The first word of the input in lowercase.
     */
    public static String getCommandWord(String input) {
        return input.trim().split(SPACE_DELIMITER)[0];
    }

    private static String removeCommandWord(String input) {
        String trimmed = input.trim();
        int firstSpace = trimmed.indexOf(SPACE_DELIMITER);
        if (firstSpace == -1) {
            return "";
        }
        return trimmed.substring(firstSpace).trim();
    }

    /**
     * Parses the description for a Todo task.
     *
     * @param input Raw user input.
     * @return The description of the Todo.
     * @throws AthenaException If the description is empty.
     */
    public static String parseTodoDescription(String input) throws AthenaException {
        String arguments = removeCommandWord(input);
        if (arguments.isEmpty()) {
            throw new AthenaException("The description of a todo cannot be empty.");
        }
        return arguments;
    }

    /**
     * Parses the deadline command input.
     *
     * @param input Raw user input string.
     * @return A string array containing the description and the deadline.
     * @throws AthenaException If the /by keyword is missing.
     */
    public static String[] parseDeadline(String input) throws AthenaException {
        String arguments = removeCommandWord(input);
        int byIdx = arguments.indexOf(BY_KEYWORD);

        if (byIdx == -1) {
            throw new AthenaException("A deadline must have a /by time.");
        }

        String desc = arguments.substring(0, byIdx).trim();
        String by = arguments.substring(byIdx + BY_KEYWORD.length()).trim();
        return new String[]{desc, by};
    }

    /**
     * Parses the event command input.
     *
     * @param input Raw user input string.
     * @return A string array containing the description, start time, and end time.
     * @throws AthenaException If /from or /to keywords are missing.
     */
    public static String[] parseEvent(String input) throws AthenaException {
        String arguments = removeCommandWord(input);
        int fromIdx = arguments.indexOf(FROM_KEYWORD);
        int toIdx = arguments.indexOf(TO_KEYWORD);

        if (fromIdx == -1 || toIdx == -1) {
            throw new AthenaException("An event must have /from and /to times.");
        }

        String desc = arguments.substring(0, fromIdx).trim();
        String from = arguments.substring(fromIdx + FROM_KEYWORD.length(), toIdx).trim();
        String to = arguments.substring(toIdx + TO_KEYWORD.length()).trim();
        return new String[]{desc, from, to};
    }

    /**
     * Parses the find command input to extract the search keyword.
     *
     * @param input Raw user input string.
     * @return The keyword provided by the user.
     * @throws AthenaException If the keyword is missing.
     */
    public static String parseFindKeyword(String input) throws AthenaException {
        String arguments = removeCommandWord(input);
        if (arguments.isEmpty()) {
            throw new AthenaException("Please specify a keyword to find.");
        }
        return arguments;
    }

    /**
     * Extracts the index from a command (e.g., mark 1).
     *
     * @param input Raw user input.
     * @return The zero-based integer index.
     */
    public static int parseIndex(String input) {
        String arguments = removeCommandWord(input);
        return Integer.parseInt(arguments) - 1;
    }
}
