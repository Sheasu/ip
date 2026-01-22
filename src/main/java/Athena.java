import java.util.Scanner;
import java.util.ArrayList;

public class Athena {
    public static void main(String[] args) {
        String intro = "____________________________________________________________\n" +
                "Hello! I'm Athena\n" +
                "What can I do for you?\n" +
                "____________________________________________________________\n";

        String outro = "____________________________________________________________\n" +
                "Bye. Hope to see you again soon!\n" +
                "____________________________________________________________\n";

        System.out.println(intro);

        ArrayList<Task> inputs = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();

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

                String output = "____________________________________________________________\n" +
                        "Nice! I've marked this task as done:\n" +
                        task + "\n" +
                        "____________________________________________________________\n";

                System.out.println(output);

            } else if (input.startsWith("unmark ")) {
                int taskNumber = Integer.parseInt(input.substring(7)) - 1;
                Task task = inputs.get(taskNumber);
                task.markAsNotDone();

                String output = "____________________________________________________________\n" +
                        "OK, I've marked this task as not done yet:\n" +
                        task + "\n" +
                        "____________________________________________________________\n";

                System.out.println(output);

            } else {
                inputs.add(new Task(input));

                String output = "____________________________________________________________\n" +
                        "added: " + input + "\n" +
                        "____________________________________________________________\n";

                System.out.println(output);
            }
        }

        System.out.println(outro);
    }
}