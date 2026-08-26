import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCheckFindMax {
    @Test
    public void testFindMax() {
        int[] arr = {1, 2, 3, 4, 5};
        assertEquals(5, CheckFindMax.findMax(arr));
    }

    @Test
    public void testFindMaxTwo() {
        int[] arr = {10, 22, 33, 444, 5};
        assertEquals(444, CheckFindMax.findMax(arr));
    }
}
