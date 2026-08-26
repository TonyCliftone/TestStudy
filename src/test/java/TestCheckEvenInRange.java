import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCheckEvenInRange {
    @Test
    public void getEvenInRange() {
        assertEquals("2 4 6 8 10", CheckEvenInRange.getEvenInRange(2, 10));
        assertEquals("0 2 4 6", CheckEvenInRange.getEvenInRange(0, 6));
    }

}
