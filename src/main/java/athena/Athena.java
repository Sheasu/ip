package athena;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class Athena {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public Athena() {
        this("./data/athena.txt");
    }

    public Athena(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (AthenaException e) {
            tasks = new TaskList();
        }
    }

    public String getResponse(String input) {
        try {
            return executeCommand(input);
        } catch (AthenaException e) {
            return "Wisdom: " + e.getMessage();
        } catch (DateTimeParseException e) {
            return "Please use the date format: yyyy-mm-dd HHmm";
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            return "Please provide a valid task number.";
        } catch (Exception e) {
            return "An unexpected error occurred: " + e.getMessage();
        }
    }

    private String executeCommand(String input) throws AthenaException {
        String commandWord = Parser.getCommandWord(input);

        if (commandWord.isEmpty()) {
            throw new AthenaException("Please enter a command.");
        }

        switch (commandWord) {
        case "bye":
            return "Farewell! Your tasks are preserved in the scrolls.";
        case "list":
            return handleList();
        case "mark":
            return handleMark(input);
        case "unmark":
            return handleUnmark(input);
        case "todo":
            return handleTodo(input);
        case "deadline":
            return handleDeadline(input);
        case "event":
            return handleEvent(input);
        case "delete":
            return handleDelete(input);
        case "find":
            return handleFind(input);
        default:
            throw new AthenaException("I do not recognize this command in my archives.");
        }
    }

    private String handleList() {
        if (tasks.getSize() == 0) return "Your list is currently empty, spartan.";
        StringBuilder out = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.getSize(); i++) {
            out.append(i + 1).append(".").append(tasks.getTask(i));
            if (i < tasks.getSize() - 1) out.append("\n");
        }
        return out.toString();
    }

    private String handleMark(String input) {
        int idx = Parser.parseIndex(input);
        Task t = tasks.getTask(idx);
        t.markAsDone();
        storage.save(tasks.getAllTasks());
        return "Victory! I've marked this task as done:\n" + t;
    }

    private String handleUnmark(String input) {
        int idx = Parser.parseIndex(input);
        Task t = tasks.getTask(idx);
        t.markAsNotDone();
        storage.save(tasks.getAllTasks());
        return "Understood. I've marked this as not done yet:\n" + t;
    }

    private String handleTodo(String input) throws AthenaException {
        Task t = new Todo(Parser.parseTodoDescription(input));
        tasks.add(t);
        storage.save(tasks.getAllTasks());
        return "Added to the scrolls:\n  " + t + "\nYou now have " + tasks.getSize() + " tasks.";
    }

    private String handleDeadline(String input) throws AthenaException {
        String[] parts = Parser.parseDeadline(input);
        Task d = new Deadline(parts[0], parts[1]);
        tasks.add(d);
        storage.save(tasks.getAllTasks());
        return "Added deadline:\n  " + d + "\nYou now have " + tasks.getSize() + " tasks.";
    }

    private String handleEvent(String input) throws AthenaException {
        String[] parts = Parser.parseEvent(input);
        Task e = new Event(parts[0], parts[1], parts[2]);
        tasks.add(e);
        storage.save(tasks.getAllTasks());
        return "Added event:\n  " + e + "\nYou now have " + tasks.getSize() + " tasks.";
    }

    private String handleDelete(String input) {
        int idx = Parser.parseIndex(input);
        Task removed = tasks.delete(idx);
        storage.save(tasks.getAllTasks());
        return "Removed from the records:\n  " + removed + "\nYou now have " + tasks.getSize() + " tasks.";
    }

    private String handleFind(String input) throws AthenaException {
        String keyword = Parser.parseFindKeyword(input);
        ArrayList<Task> found = tasks.findTasks(keyword);
        if (found.isEmpty()) return "No matching tasks found for: " + keyword;

        StringBuilder result = new StringBuilder("Matching tasks found:\n");
        for (int i = 0; i < found.size(); i++) {
            result.append(i + 1).append(".").append(found.get(i));
            if (i < found.size() - 1) result.append("\n");
        }
        return result.toString();
    }
}