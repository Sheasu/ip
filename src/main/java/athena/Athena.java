package athena;

import java.time.format.DateTimeParseException;

public class Athena {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

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

    public void run() {
        ui.showWelcome();
        boolean isExit = false;

        while (!isExit) {
            String input = ui.readCommand();
            try {
                String commandWord = Parser.getCommandWord(input);

                if (commandWord.isEmpty()) {
                    throw new AthenaException("Please enter a command.");
                }

                switch (commandWord) {
                    case "bye":
                        isExit = true;
                        break;

                    case "list":
                        StringBuilder out = new StringBuilder("Here are the tasks in your list:\n");
                        for (int i = 0; i < tasks.getSize(); i++) {
                            out.append(i + 1).append(".").append(tasks.getTask(i));
                            if (i < tasks.getSize() - 1) out.append("\n");
                        }
                        ui.showMessage(out.toString());
                        break;

                    case "mark":
                        int mIdx = Parser.parseIndex(input);
                        Task mT = tasks.getTask(mIdx);
                        mT.markAsDone();
                        storage.save(tasks.getAllTasks());
                        ui.showMessage("Nice! I've marked this task as done:\n" + mT);
                        break;

                    case "unmark":
                        int uIdx = Parser.parseIndex(input);
                        Task uT = tasks.getTask(uIdx);
                        uT.markAsNotDone();
                        storage.save(tasks.getAllTasks());
                        ui.showMessage("OK, I've marked this task as not done yet:\n" + uT);
                        break;

                    case "todo":
                        Task t = new Todo(Parser.parseTodoDescription(input));
                        tasks.add(t);
                        storage.save(tasks.getAllTasks());
                        ui.showMessage("Got it. Added:\n  " + t + "\nNow you have " + tasks.getSize() + " tasks.");
                        break;

                    case "deadline":
                        String[] dParts = Parser.parseDeadline(input);
                        Task d = new Deadline(dParts[0], dParts[1]);
                        tasks.add(d);
                        storage.save(tasks.getAllTasks());
                        ui.showMessage("Got it. Added:\n  " + d + "\nNow you have " + tasks.getSize() + " tasks.");
                        break;

                    case "event":
                        String[] eParts = Parser.parseEvent(input);
                        Task e = new Event(eParts[0], eParts[1], eParts[2]);
                        tasks.add(e);
                        storage.save(tasks.getAllTasks());
                        ui.showMessage("Got it. Added:\n  " + e + "\nNow you have " + tasks.getSize() + " tasks.");
                        break;

                    case "delete":
                        int delIdx = Parser.parseIndex(input);
                        Task removed = tasks.delete(delIdx);
                        storage.save(tasks.getAllTasks());
                        ui.showMessage("Noted. Removed:\n  " + removed + "\nNow you have " + tasks.getSize() + " tasks.");
                        break;

                    default:
                        throw new AthenaException("I don't know what that means.");
                }

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

    public static void main(String[] args) {
        new Athena("./data/athena.txt").run();
    }
}