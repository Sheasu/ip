package athena;

/**
 * Deals with making sense of the user command.
 */
public class Parser {
    private static final String SPACE_DELIMITER = " ";
    private static final String BY_KEYWORD = " /by ";
    private static final String FROM_KEYWORD = " /from ";
    private static final String TO_KEYWORD = " /to ";

    private static final int TODO_PREFIX_LENGTH = 5;
    private static final int DEADLINE_PREFIX_LENGTH = 9;
    private static final int EVENT_PREFIX_LENGTH = 6;
    private static final int FIND_PREFIX_LENGTH = 5;

    public static String getCommandWord(String input) {
        assert input != null : "Input cannot be null";
        return input.trim().split(SPACE_DELIMITER)[0];
    }

    public static int parseIndex(String input) {
        assert input != null : "Input cannot be null";
        String[] parts = input.split(SPACE_DELIMITER);
        return Integer.parseInt(parts[1]) - 1;
    }

    public static String parseTodoDescription(String input) throws AthenaException {
        String trimmedInput = input.trim();
        if (trimmedInput.length() < TODO_PREFIX_LENGTH) {
            throw new AthenaException("The description of a todo cannot be empty.");
        }
        return trimmedInput.substring(TODO_PREFIX_LENGTH).trim();
    }

    public static String[] parseDeadline(String input) throws AthenaException {
        int byIdx = input.indexOf(BY_KEYWORD);
        if (byIdx == -1) {
            throw new AthenaException("A deadline must have a /by time.");
        }

        String desc = input.substring(DEADLINE_PREFIX_LENGTH, byIdx).trim();
        String by = input.substring(byIdx + BY_KEYWORD.length()).trim();
        return new String[]{desc, by};
    }

    public static String[] parseEvent(String input) throws AthenaException {
        int fromIdx = input.indexOf(FROM_KEYWORD);
        int toIdx = input.indexOf(TO_KEYWORD);

        if (fromIdx == -1 || toIdx == -1) {
            throw new AthenaException("An event must have /from and /to times.");
        }

        String desc = input.substring(EVENT_PREFIX_LENGTH, fromIdx).trim();
        String from = input.substring(fromIdx + FROM_KEYWORD.length(), toIdx).trim();
        String to = input.substring(toIdx + TO_KEYWORD.length()).trim();
        return new String[]{desc, from, to};
    }

    public static String parseFindKeyword(String input) throws AthenaException {
        String trimmed = input.trim();
        if (trimmed.length() < FIND_PREFIX_LENGTH) {
            throw new AthenaException("Please specify a keyword to find.");
        }
        return trimmed.substring(FIND_PREFIX_LENGTH).trim();
    }
}
