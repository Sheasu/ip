package athena;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles the loading and saving of task data to the local disk.
 * Ensures the Spartan records persist across different sessions.
 */
public class Storage {
    private static final String DELIMITER = " \\| ";
    private static final String SAVE_DELIMITER = " | ";
    private final String filePath;

    /**
     * Initializes the Storage handler with a specific file path.
     *
     * @param filePath The location of the data file.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the file system.
     * If the file does not exist, returns an empty list.
     *
     * @return An ArrayList of tasks retrieved from the scrolls.
     * @throws AthenaException If the file format is corrupted.
     */
    public ArrayList<Task> load() throws AthenaException {
        File f = new File(filePath);
        if (!f.exists()) {
            return new ArrayList<>();
        }

        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            return parseLinesToTasks(lines);
        } catch (Exception e) {
            throw new AthenaException("The scrolls of data are corrupted. Starting anew.");
        }
    }

    private ArrayList<Task> parseLinesToTasks(List<String> lines) {
        ArrayList<Task> tasks = new ArrayList<>();
        for (String line : lines) {
            Task t = parseLineToTask(line);
            if (t != null) {
                tasks.add(t);
            }
        }
        return tasks;
    }

    private Task parseLineToTask(String line) {
        String[] parts = line.split(DELIMITER);
        if (parts.length < 3) {
            return null;
        }

        Task task = createTaskByType(parts);
        if (task != null && parts[1].equals("1")) {
            task.markAsDone();
        }
        return task;
    }

    private Task createTaskByType(String[] parts) {
        switch (parts[0]) {
        case "T":
            return new Todo(parts[2]);
        case "D":
            return new Deadline(parts[2], parts[3]);
        case "E":
            return new Event(parts[2], parts[3], parts[4]);
        default:
            return null;
        }
    }

    /**
     * Saves the current phalanx of tasks into the persistent text file.
     * Creates the necessary directories if they are missing.
     *
     * @param tasks The list of tasks to be archived.
     */
    public void save(ArrayList<Task> tasks) {
        File f = new File(filePath);
        prepareDirectory(f);

        try (FileWriter fw = new FileWriter(f)) {
            for (Task t : tasks) {
                fw.write(t.toSaveFormat() + System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println("Warning: Could not save tasks to file.");
        }
    }

    private void prepareDirectory(File f) {
        File parent = f.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
    }
}
