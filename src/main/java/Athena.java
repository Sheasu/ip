import java.util.Scanner;

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

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();

            if (input.equals("bye")) {
                break;
            }

            String output = "____________________________________________________________\n" +
                    input + "\n" +
                    "____________________________________________________________\n";

            System.out.println(output);
        }

        System.out.println(outro);
    }
}