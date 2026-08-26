import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCheckAccess {
    @Test
    public void checkAccessGreaterOrLess() {
        assertEquals("Allowed", CheckAccess.checkAccess(19));
        assertEquals("Allowed", CheckAccess.checkAccess(20));
        assertEquals("Denied", CheckAccess.checkAccess(17));
        assertEquals("Denied", CheckAccess.checkAccess(16));
        assertEquals("Denied", CheckAccess.checkAccess(-5));
    }
}
