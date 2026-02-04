package athena;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TaskListTest {

    @Test
    public void getTask_invalidIndex_exceptionThrown() {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("test"));

        assertThrows(IndexOutOfBoundsException.class, () -> {
            tasks.getTask(5);
        });
    }

    @Test
    public void getSize_afterOperations_correctCount() {
        TaskList tasks = new TaskList();
        assertEquals(0, tasks.getSize());

        tasks.add(new Todo("1"));
        tasks.add(new Todo("2"));
        tasks.delete(0);

        assertEquals(1, tasks.getSize());
    }
}