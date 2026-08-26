import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCheckGrade {
    @Test
    public void checkGetGrade() {
        assertEquals("A", CheckGrade.getGrade(88));
        assertEquals("B", CheckGrade.getGrade(66));
        assertEquals("C", CheckGrade.getGrade(44));
        assertEquals("D", CheckGrade.getGrade(21));
        assertEquals("E", CheckGrade.getGrade(0));
        assertEquals("Error", CheckGrade.getGrade(-44));
        assertEquals("Error", CheckGrade.getGrade(144));
    }
}
