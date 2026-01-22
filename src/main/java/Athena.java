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

        ArrayList<String> inputs = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();

            if (input.equals("bye")) {
                break;
            }

            if (input.equals("list")) {
                String output = "____________________________________________________________\n";

                for (int i = 0; i < inputs.size(); i++) {
                    output += (i + 1) + ". " + inputs.get(i) + "\n";
                }

                output += "____________________________________________________________\n";

                System.out.println(output);
            } else {
                inputs.add(input);

                String output = "____________________________________________________________\n" +
                        "added: " + input + "\n" +
                        "____________________________________________________________\n";

                System.out.println(output);
            }
        }

        System.out.println(outro);
    }
}