package athena;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ParserTest {

    @Test
    public void parseTodoDescription_extraSpaces_trimmedSuccess() throws AthenaException {
        assertEquals("read book", Parser.parseTodoDescription("todo   read book  "));
    }

    @Test
    public void parseDeadline_missingByKeyword_exceptionThrown() {
        assertThrows(AthenaException.class, () -> {
            Parser.parseDeadline("deadline return book /at tonight");
        });
    }

    @Test
    public void parseEvent_outOfOrder_exceptionThrown() {
        assertThrows(AthenaException.class, () -> {
            Parser.parseEvent("event party /to 10pm");
        });
    }
}
