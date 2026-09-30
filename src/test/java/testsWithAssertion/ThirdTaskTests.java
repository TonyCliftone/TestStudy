package testsWithAssertion;

import mainMethods.Utils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ThirdTaskTests {

    @Test
    @Tag("main")
    void testCheckIsEvenAssert() {
        assertTrue(Utils.isEven(4), "Число 4 должно быть чётным");
        assertFalse(Utils.isEven(5), "Число 5 должно быть нечётным");
    }

    @Test
    @Tag("main")
    void testCheckRemoveSpecificNameAssert() {
        List<String> input = Arrays.asList("Gosha", "Tosha", "Alesha", "Aboba", "Neaboba");
        List<String> expected = Arrays.asList("Gosha", "Tosha", "Aboba", "Neaboba");
        List<String> actual = Utils.removeSpecificName(input, "Alesha");
        assertEquals(expected, actual, "Список после удаления Alesha должен быть [Gosha, Tosha, Aboba, Neaboba]");
    }

    @Test
    @Tag("main")
    void testCheckCalcAverageAssert() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        double expected = 3.0;
        double actual = Utils.calcAverage(list);
        assertEquals(expected, actual, 0.0001, "Среднее арифметическое должно быть 3.0");
    }

    @Test
    @Tag("failed")
    void testFailingAssert() {
        assertEquals(5, 2 + 2, "Ожидалось 5, но 2+2=4");
    }
}
