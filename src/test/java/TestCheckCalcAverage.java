import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCheckCalcAverage {
    @Test
    public void testRightCalcAverage() {
        List<Integer> list = Arrays.asList(1, 2, 3);
        assertEquals(2.0, CheckCalcAverage.calcAverage(list));
    }

    @Test
    public void testEmptyCalcAverage() {
        List<Integer> list = Collections.emptyList();
        assertEquals(0, CheckCalcAverage.calcAverage(list));
    }
}
