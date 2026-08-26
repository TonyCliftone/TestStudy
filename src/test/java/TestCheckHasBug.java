import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestCheckHasBug {
    @Test
    public void checkHasBugTrue() {
        String[] messages = {"Bug", "Bag", "Berry"};
        assertTrue(CheckHasBug.hasBug(messages));
    }

    @Test
    public void checkHasBugFalse() {
        String[] messages = {"Byg", "Bag", "Berry"};
        assertFalse(CheckHasBug.hasBug(messages));
    }
}
