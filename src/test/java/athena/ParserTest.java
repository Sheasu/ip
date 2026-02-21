package athena;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests the logic of the Parser class to ensure command components are extracted correctly.
 */
public class ParserTest {

    /**
     * Tests that todo descriptions are correctly trimmed of extra whitespace.
     */
    @Test
    public void parseTodoDescription_extraSpaces_trimmedSuccess() throws AthenaException {
        String input = "todo   read book  ";
        assertEquals("read book", Parser.parseTodoDescription(input));
    }

    /**
     * Tests that parseDeadline throws an exception when the mandatory /by keyword is missing.
     */
    @Test
    public void parseDeadline_missingByKeyword_exceptionThrown() {
        assertThrows(AthenaException.class, () -> Parser.parseDeadline("deadline return book /at tonight"));
    }

    /**
     * Tests that parseEvent correctly splits the description, start time, and end time.
     */
    @Test
    public void parseEvent_validInput_segmentsExtractedCorrectly() throws AthenaException {
        String input = "event Spartan Games /from Friday 8pm /to Sunday 10pm";
        String[] parts = Parser.parseEvent(input);

        assertEquals("Spartan Games", parts[0]);
        assertEquals("Friday 8pm", parts[1]);
        assertEquals("Sunday 10pm", parts[2]);
    }

    /**
     * Tests that parseEvent throws an exception when the /to keyword is missing.
     */
    @Test
    public void parseEvent_missingToKeyword_exceptionThrown() {
        assertThrows(AthenaException.class, () -> Parser.parseEvent("event Party /from 8pm"));
    }
}
