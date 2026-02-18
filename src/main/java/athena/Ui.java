package athena;

import java.util.Scanner;

/**
 * Handles all interactions with the user via the command line interface.
 */
public class Ui {
    private Scanner scanner;
    private final String line = "____________________________________________________________";

    /**
     * Initializes the Ui with a new scanner.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Reads the next line of input from the user.
     *
     * @return The full string input by the user.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays the welcome message.
     */
    public void showWelcome() {
        System.out.println(line + "\nHello! I'm Athena.\nWhat can I do for you?\n" + line);
    }

    /**
     * Displays the goodbye message.
     */
    public void showGoodbye() {
        System.out.println(line + "\nBye. Hope to see you again soon!\n" + line);
    }

    /**
     * Displays an error message framed by divider lines.
     *
     * @param message The error message to display.
     */
    public void showError(String message) {
        System.out.println(line + "\n" + message + "\n" + line);
    }

    /**
     * Displays a general message framed by divider lines.
     *
     * @param message The message to display.
     */
    public void showMessage(String message) {
        System.out.println(line + "\n" + message + "\n" + line);
    }

    /**
     * Closes the scanner to prevent resource leaks.
     */
    public void closeScanner() {
        scanner.close();
    }
}
