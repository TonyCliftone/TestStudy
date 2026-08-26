import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestCheckPositive {
    @Test
    public void checkIsPositive() {
        assertTrue(CheckPositive.isPositive(3));
        assertTrue(CheckPositive.isPositive(2));
        assertTrue(CheckPositive.isPositive(1));
        assertTrue(CheckPositive.isPositive(0));
        assertFalse(CheckPositive.isPositive(-1));
        assertFalse(CheckPositive.isPositive(-2));
        assertFalse(CheckPositive.isPositive(-3));
    }
}
