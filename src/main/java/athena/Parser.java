package athena;

public class Parser {

    public static String getCommandWord(String input) {
        return input.trim().split(" ")[0];
    }

    public static int parseIndex(String input) {
        String[] parts = input.split(" ");
        return Integer.parseInt(parts[1]) - 1;
    }

    public static String parseTodoDescription(String input) throws AthenaException {
        if (input.trim().length() <= 4) {
            throw new AthenaException("The description of a todo cannot be empty.");
        }
        return input.substring(5).trim();
    }

    public static String[] parseDeadline(String input) throws AthenaException {
        int byIdx = input.indexOf(" /by ");
        if (byIdx == -1) {
            throw new AthenaException("A deadline must have a /by time.");
        }
        String desc = input.substring(9, byIdx).trim();
        String by = input.substring(byIdx + 5).trim();
        return new String[]{desc, by};
    }

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