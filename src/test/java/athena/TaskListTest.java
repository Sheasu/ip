package athena;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TaskListTest {

    @Test
    public void getTask_indexOutOfBounds_exceptionThrown() {
        TaskList taskList = new TaskList();
        taskList.add(new Todo("test task"));

        assertThrows(AssertionError.class, () -> {
            taskList.getTask(5);
        });
    }

    @Test
    public void getSize_multipleOperations_correctCountReturned() {
        TaskList taskList = new TaskList();

        taskList.add(new Todo("First task"));
        taskList.add(new Todo("Second task"));
        taskList.delete(0);

        int expectedSize = 1;
        assertEquals(expectedSize, taskList.getSize());
    }
}
