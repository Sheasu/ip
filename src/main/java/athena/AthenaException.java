package athena;

/**
 * Represents exceptions specific to the Athena application.
 * Used to handle errors related to user input, file I/O, and command parsing.
 */
public class AthenaException extends Exception {

    /**
     * Constructs a new AthenaException with the specified detail message.
     *
     * @param message The error message to be displayed to the user.
     */
    public AthenaException(String message) {
        super(message);
    }
}
