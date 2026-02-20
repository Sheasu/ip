package athena;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ParserTest {

    @Test
    public void parseTodoDescription_extraSpaces_trimmedSuccess() throws AthenaException {
        String input = "todo   read book  ";
        String expectedDescription = "read book";

        String actualDescription = Parser.parseTodoDescription(input);

        assertEquals(expectedDescription, actualDescription);
    }

    @Test
    public void parseDeadline_missingByKeyword_exceptionThrown() {
        String input = "deadline return book /at tonight";

        assertThrows(AthenaException.class, () -> Parser.parseDeadline(input));
    }

    @Test
    public void parseEvent_missingToKeyword_exceptionThrown() {
        String input = "event party /from 8pm";

        assertThrows(AthenaException.class, () -> Parser.parseEvent(input));
    }
}
