import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestCheckEven {
    @Test
    public void testIsEven() {
        Assertions.assertTrue(CheckEven.isEven(2));
        Assertions.assertFalse(CheckEven.isEven(3));
        Assertions.assertFalse(CheckEven.isEven(-3));
        Assertions.assertTrue(CheckEven.isEven(-2));
    }
}
