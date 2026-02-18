package athena;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;

/**
 * The main entry point of the Athena task management application.
 * Handles the high-level program flow and delegates work to other components.
 */
public class Athena {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Creates a new Athena instance using the specified file path for storage.
     *
     * @param filePath The path to the file used for saving and loading tasks
     */
    public Athena(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (AthenaException e) {
            ui.showError("Could not load tasks.");
            tasks = new TaskList();
        }
    }

    /**
     * Runs the main command-processing loop of the application.
     * Continuously reads user input and executes commands until exit.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;

        while (!isExit) {
            String input = ui.readCommand();
            try {
                isExit = executeCommand(input);
            } catch (AthenaException e) {
                ui.showError(e.getMessage());
            } catch (DateTimeParseException e) {
                ui.showError("Please use the date format: yyyy-mm-dd HHmm");
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                ui.showError("Please provide a valid task number.");
            } catch (Exception e) {
                ui.showError("An unexpected error occurred: " + e.getMessage());
            }
        }

        ui.showGoodbye();
        ui.closeScanner();
    }

    private boolean executeCommand(String input) throws AthenaException {
        String commandWord = Parser.getCommandWord(input);

        if (commandWord.isEmpty()) {
            throw new AthenaException("Please enter a command.");
        }

        switch (commandWord) {
        case "bye":
            return true;
        case "list":
            handleList();
            break;
        case "mark":
            handleMark(input);
            break;
        case "unmark":
            handleUnmark(input);
            break;
        case "todo":
            handleTodo(input);
            break;
        case "deadline":
            handleDeadline(input);
            break;
        case "event":
            handleEvent(input);
            break;
        case "delete":
            handleDelete(input);
            break;
        case "find":
            handleFind(input);
            break;
        default:
            throw new AthenaException("I don't know what that means.");
        }
        return false;
    }

    private void handleList() {
        StringBuilder out = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.getSize(); i++) {
            out.append(i + 1).append(".").append(tasks.getTask(i));
            if (i < tasks.getSize() - 1) {
                out.append("\n");
            }
        }
        ui.showMessage(out.toString());
    }

    private void handleMark(String input) {
        int idx = Parser.parseIndex(input);
        Task t = tasks.getTask(idx);
        t.markAsDone();
        storage.save(tasks.getAllTasks());
        ui.showMessage("Nice! I've marked this task as done:\n" + t);
    }

    private void handleUnmark(String input) {
        int idx = Parser.parseIndex(input);
        Task t = tasks.getTask(idx);
        t.markAsNotDone();
        storage.save(tasks.getAllTasks());
        ui.showMessage("OK, I've marked this task as not done yet:\n" + t);
    }

    private void handleTodo(String input) throws AthenaException {
        Task t = new Todo(Parser.parseTodoDescription(input));
        tasks.add(t);
        storage.save(tasks.getAllTasks());
        ui.showMessage("Got it. Added:\n  " + t
                + "\nNow you have " + tasks.getSize() + " tasks.");
    }

    private void handleDeadline(String input) throws AthenaException {
        String[] parts = Parser.parseDeadline(input);
        Task d = new Deadline(parts[0], parts[1]);
        tasks.add(d);
        storage.save(tasks.getAllTasks());
        ui.showMessage("Got it. Added:\n  " + d
                + "\nNow you have " + tasks.getSize() + " tasks.");
    }

    private void handleEvent(String input) throws AthenaException {
        String[] parts = Parser.parseEvent(input);
        Task e = new Event(parts[0], parts[1], parts[2]);
        tasks.add(e);
        storage.save(tasks.getAllTasks());
        ui.showMessage("Got it. Added:\n  " + e
                + "\nNow you have " + tasks.getSize() + " tasks.");
    }

    private void handleDelete(String input) {
        int idx = Parser.parseIndex(input);
        Task removed = tasks.delete(idx);
        storage.save(tasks.getAllTasks());
        ui.showMessage("Noted. Removed:\n  " + removed
                + "\nNow you have " + tasks.getSize() + " tasks.");
    }

    private void handleFind(String input) throws AthenaException {
        String keyword = Parser.parseFindKeyword(input);
        ArrayList<Task> found = tasks.findTasks(keyword);

        if (found.isEmpty()) {
            ui.showMessage("No matching tasks found for: " + keyword);
            return;
        }

        StringBuilder result = new StringBuilder("Here are the matching tasks in your list:\n");
        for (int i = 0; i < found.size(); i++) {
            result.append(i + 1).append(".").append(found.get(i));
            if (i < found.size() - 1) {
                result.append("\n");
            }
        }
        ui.showMessage(result.toString());
    }

    public static void main(String[] args) {
        new Athena("./data/athena.txt").run();
    }
}
