import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class TestCheckReverse {
    @Test
    public void testReverse() {
        String[] unReverse = {"One", "Two", "Three"};
        String[] doReverse = {"Three", "Two", "One"};
        assertArrayEquals(doReverse,CheckReverse.reverse(unReverse));
    }
}
