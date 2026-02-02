import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Athena {
    private static final String FILE_PATH = "./data/athena.txt";

    public static void main(String[] args) {
        String intro = "____________________________________________________________\n" +
                "Hello! I'm Athena\n" +
                "What can I do for you?\n" +
                "____________________________________________________________\n";

        String outro = "____________________________________________________________\n" +
                "Bye. Hope to see you again soon!\n" +
                "____________________________________________________________\n";

        ArrayList<Task> inputs = loadTasks();

        System.out.println(intro);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();

            try {
                if (input.trim().isEmpty()) {
                    throw new AthenaException("Please enter a command.");
                }

                if (input.equals("bye")) {
                    break;
                }

                if (input.equals("list")) {
                    String output = "____________________________________________________________\n" +
                            "Here are the tasks in your list:\n";

                    for (int i = 0; i < inputs.size(); i++) {
                        output += (i + 1) + "." + inputs.get(i) + "\n";
                    }

                    output += "____________________________________________________________\n";

                    System.out.println(output);

                } else if (input.startsWith("mark ")) {
                    int taskNumber = Integer.parseInt(input.substring(5)) - 1;
                    Task task = inputs.get(taskNumber);
                    task.markAsDone();
                    saveTasks(inputs);

                    String output = "____________________________________________________________\n" +
                            "Nice! I've marked this task as done:\n" +
                            task + "\n" +
                            "____________________________________________________________\n";

                    System.out.println(output);

                } else if (input.startsWith("unmark ")) {
                    int taskNumber = Integer.parseInt(input.substring(7)) - 1;
                    Task task = inputs.get(taskNumber);
                    task.markAsNotDone();
                    saveTasks(inputs);

                    String output = "____________________________________________________________\n" +
                            "OK, I've marked this task as not done yet:\n" +
                            task + "\n" +
                            "____________________________________________________________\n";

                    System.out.println(output);

                } else if (input.startsWith("todo")) {
                    if (input.length() <= 5) {
                        throw new AthenaException("The description of a todo cannot be empty.");
                    }

                    String desc = input.substring(5);
                    Task task = new Todo(desc);
                    inputs.add(task);
                    saveTasks(inputs);

                    String output = "____________________________________________________________\n" +
                            "Got it. I've added this task:\n" +
                            "  " + task + "\n" +
                            "Now you have " + inputs.size() + " tasks in the list.\n" +
                            "____________________________________________________________\n";

                    System.out.println(output);

                } else if (input.startsWith("deadline ")) {
                    if (!input.contains(" /by ")) {
                        throw new AthenaException("A deadline must have a /by time.");
                    }

                    int idx = input.indexOf(" /by ");
                    String desc = input.substring(9, idx);
                    String by = input.substring(idx + 5);
                    Task task = new Deadline(desc, by);
                    inputs.add(task);
                    saveTasks(inputs);

                    String output = "____________________________________________________________\n" +
                            "Got it. I've added this task:\n" +
                            "  " + task + "\n" +
                            "Now you have " + inputs.size() + " tasks in the list.\n" +
                            "____________________________________________________________\n";

                    System.out.println(output);

                } else if (input.startsWith("event ")) {
                    int fromIdx = input.indexOf(" /from ");
                    int toIdx = input.indexOf(" /to ");
                    String desc = input.substring(6, fromIdx);
                    String from = input.substring(fromIdx + 7, toIdx);
                    String to = input.substring(toIdx + 5);
                    Task task = new Event(desc, from, to);
                    inputs.add(task);
                    saveTasks(inputs);

                    String output = "____________________________________________________________\n" +
                            "Got it. I've added this task:\n" +
                            "  " + task + "\n" +
                            "Now you have " + inputs.size() + " tasks in the list.\n" +
                            "____________________________________________________________\n";

                    System.out.println(output);

                } else if (input.startsWith("delete ")) {
                    int idx = Integer.parseInt(input.substring(7)) - 1;
                    Task removed = inputs.remove(idx);
                    saveTasks(inputs);

                    String output = "____________________________________________________________\n" +
                            "Noted. I've removed this task:\n" +
                            "  " + removed + "\n" +
                            "Now you have " + inputs.size() + " tasks in the list.\n" +
                            "____________________________________________________________\n";

                    System.out.println(output);

                } else {
                    throw new AthenaException("I don't know what that means.");
                }

            } catch (AthenaException e) {
                System.out.println("____________________________________________________________\n" +
                        e.getMessage() + "\n" +
                        "____________________________________________________________\n");
            } catch (Exception e) {
                System.out.println("____________________________________________________________\n" +
                        "An error occurred: " + e.getMessage() + "\n" +
                        "____________________________________________________________\n");
            }
        }

        scanner.close();
        System.out.println(outro);
    }

    private static void saveTasks(ArrayList<Task> tasks) {
        try {
            File f = new File(FILE_PATH);

            if (!f.getParentFile().exists()) {
                f.getParentFile().mkdirs();
            }

            FileWriter fw = new FileWriter(f);

            for (Task t : tasks) {
                fw.write(t.toSaveFormat() + System.lineSeparator());
            }

            fw.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    private static ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        File f = new File(FILE_PATH);

        if (!f.exists()) {
            return tasks;
        }

        try {
            List<String> lines = Files.readAllLines(Paths.get(FILE_PATH));

            for (String line : lines) {
                String[] p = line.split(" \\| ");
                Task t;

                switch (p[0]) {
                    case "T":
                        t = new Todo(p[2]);
                        break;
                    case "D":
                        t = new Deadline(p[2], p[3]);
                        break;
                    case "E":
                        t = new Event(p[2], p[3], p[4]);
                        break;
                    default:
                        continue;
                }

                if (p[1].equals("1")) {
                    t.markAsDone();
                }
                tasks.add(t);
            }
        } catch (IOException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Error loading tasks. Data file might be corrupted.");
        }

        return tasks;
    }
}