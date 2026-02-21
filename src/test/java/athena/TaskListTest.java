package athena;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

/**
 * Tests the TaskList class to ensure the in-memory collection of tasks remains consistent.
 */
public class TaskListTest {

    /**
     * Tests that the size of the list updates correctly after an addition followed by a deletion.
     */
    @Test
    public void getSize_addAndDelete_correctCountReturned() {
        TaskList taskList = new TaskList();
        taskList.add(new Todo("First task"));
        taskList.add(new Todo("Second task"));
        taskList.delete(0);
        assertEquals(1, taskList.getSize());
    }

    /**
     * Tests the find functionality to ensure it returns tasks containing the keyword.
     * This checks the internal filtering logic of the TaskList.
     */
    @Test
    public void findTasks_matchingKeyword_returnsFilteredList() {
        TaskList taskList = new TaskList();
        taskList.add(new Todo("Read the scrolls"));
        taskList.add(new Todo("Clean the spear"));

        ArrayList<Task> found = taskList.findTasks("scrolls");

        assertEquals(1, found.size());
        assertTrue(found.get(0).toString().contains("Read the scrolls"));
    }
}
