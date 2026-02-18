package athena;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Deals with loading tasks from the file and saving tasks in the file.
 */
public class Storage {
    private String filePath;

    /**
     * Constructs a Storage object with a specific file path.
     *
     * @param filePath The path to the file where data is stored and loaded.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads the task list from the hard disk.
     *
     * @return An ArrayList of tasks loaded from the file.
     * @throws AthenaException If the file exists but is corrupted.
     */
    public ArrayList<Task> load() throws AthenaException {
        ArrayList<Task> tasks = new ArrayList<>();
        File f = new File(filePath);
        if (!f.exists()) {
            return tasks;
        }

        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line : lines) {
                String[] p = line.split(" \\| ");
                Task t;
                if (p[0].equals("T")) {
                    t = new Todo(p[2]);
                } else if (p[0].equals("D")) {
                    t = new Deadline(p[2], p[3]);
                } else if (p[0].equals("E")) {
                    t = new Event(p[2], p[3], p[4]);
                } else {
                    continue;
                }
                if (p[1].equals("1")) {
                    t.markAsDone();
                }
                tasks.add(t);
            }
        } catch (Exception e) {
            throw new AthenaException("Loading error: File corrupted.");
        }
        return tasks;
    }

    /**
     * Saves the current list of tasks to the hard disk.
     *
     * @param tasks The list of tasks to be saved.
     */
    public void save(ArrayList<Task> tasks) {
        try {
            File f = new File(filePath);
            if (!f.getParentFile().exists()) {
                f.getParentFile().mkdirs();
            }
            FileWriter fw = new FileWriter(f);
            for (Task t : tasks) {
                fw.write(t.toSaveFormat() + System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks.");
        }
    }
}
