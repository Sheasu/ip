package athena;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<Task> load() throws AthenaException {
        ArrayList<Task> tasks = new ArrayList<>();
        File f = new File(filePath);
        if (!f.exists()) {
            return tasks;
        }

        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));

            assert lines != null : "File reading returned a null list of lines";

            for (String line : lines) {
                Task t = parseLineToTask(line);
                if (t != null) {
                    tasks.add(t);
                }
            }
        } catch (Exception e) {
            throw new AthenaException("The scrolls of data are corrupted. Starting anew.");
        }
        return tasks;
    }

    private Task parseLineToTask(String line) {
        String[] p = line.split(" \\| ");

        if (p.length < 3) {
            return null;
        }

        assert p.length >= 3 : "Logic error: p.length check failed to catch a short line";

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
            return null;
        }

        if (p[1].equals("1")) {
            t.markAsDone();
        }
        return t;
    }

    public void save(ArrayList<Task> tasks) {
        try {
            File f = new File(filePath);
            if (f.getParentFile() != null && !f.getParentFile().exists()) {
                f.getParentFile().mkdirs();
            }

            FileWriter fw = new FileWriter(f);
            for (Task t : tasks) {
                fw.write(t.toSaveFormat() + System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            //
        }
    }
}