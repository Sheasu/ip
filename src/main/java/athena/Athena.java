package athena;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class Athena {
    private final Storage storage;
    private TaskList tasks;
    private final Ui ui;

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
            return "An unexpected error occurred.";
        }
    }

    private String executeCommand(String input) throws AthenaException {
        String commandWord = Parser.getCommandWord(input);
        if (commandWord.isEmpty()) {
            throw new AthenaException("Please enter a command.");
        }

        switch (commandWord) {
        case "bye":
        case "b":
            return "Farewell! Your tasks are preserved in the scrolls.";
        case "list":
        case "l":
            return handleList();
        case "mark":
        case "m":
            return handleMarkStatus(input, true);
        case "unmark":
        case "u":
            return handleMarkStatus(input, false);
        case "todo":
        case "t":
            return handleTodo(input);
        case "deadline":
        case "d":
            return handleDeadline(input);
        case "event":
        case "e":
            return handleEvent(input);
        case "delete":
        case "del":
            return handleDelete(input);
        case "find":
        case "f":
            return handleFind(input);
        default:
            throw new AthenaException("I do not recognize this command.");
        }
    }

    private String handleList() {
        if (tasks.getSize() == 0) {
            return "Your list is currently empty, spartan.";
        }
        return tasks.getFormattedList();
    }

    private String handleMarkStatus(String input, boolean isDone) {
        int idx = Parser.parseIndex(input);
        Task t = tasks.getTask(idx);
        if (isDone) {
            t.markAsDone();
        } else {
            t.markAsNotDone();
        }
        storage.save(tasks.getAllTasks());
        String status = isDone ? "marked this task as done" : "marked this as not done";
        return "Victory! I've " + status + ":\n" + t;
    }

    private String handleTodo(String input) throws AthenaException {
        Task t = new Todo(Parser.parseTodoDescription(input));
        return addTaskAndSave(t);
    }

    private String handleDeadline(String input) throws AthenaException {
        String[] parts = Parser.parseDeadline(input);
        return addTaskAndSave(new Deadline(parts[0], parts[1]));
    }

    private String handleEvent(String input) throws AthenaException {
        String[] parts = Parser.parseEvent(input);
        return addTaskAndSave(new Event(parts[0], parts[1], parts[2]));
    }

    private String addTaskAndSave(Task t) {
        tasks.add(t);
        storage.save(tasks.getAllTasks());
        return "Added to the scrolls:\n  " + t + "\nYou now have " + tasks.getSize() + " tasks.";
    }

    private String handleDelete(String input) {
        int idx = Parser.parseIndex(input);
        Task removed = tasks.delete(idx);
        storage.save(tasks.getAllTasks());
        return "Removed from the records:\n  " + removed + "\nTasks remaining: " + tasks.getSize();
    }

    private String handleFind(String input) throws AthenaException {
        String keyword = Parser.parseFindKeyword(input);
        ArrayList<Task> found = tasks.findTasks(keyword);
        if (found.isEmpty()) {
            return "No matching tasks found for: " + keyword;
        }
        return formatSearchList(found);
    }

    private String formatSearchList(ArrayList<Task> found) {
        StringBuilder result = new StringBuilder("Matching tasks found:\n");
        for (int i = 0; i < found.size(); i++) {
            result.append(i + 1).append(".").append(found.get(i));
            if (i < found.size() - 1) {
                result.append("\n");
            }
        }
        return result.toString();
    }
}
