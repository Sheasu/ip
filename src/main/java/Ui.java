import java.util.Scanner;

public class Ui {
    private Scanner scanner;
    private final String line = "____________________________________________________________";

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showWelcome() {
        System.out.println(line + "\nHello! I'm Athena\nWhat can I do for you?\n" + line);
    }

    public void showGoodbye() {
        System.out.println(line + "\nBye. Hope to see you again soon!\n" + line);
    }

    public void showLine() {
        System.out.println(line);
    }

    public void showError(String message) {
        System.out.println(line + "\n" + message + "\n" + line);
    }

    public void showMessage(String message) {
        System.out.println(line + "\n" + message + "\n" + line);
    }

    public void closeScanner() {
        scanner.close();
    }
}