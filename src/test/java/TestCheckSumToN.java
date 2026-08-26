import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCheckSumToN {
    @Test
    public void checkSummaryToN() {
        assertEquals(15,CheckSumTon.sumToN(5));
        assertEquals(120,CheckSumTon.sumToN(15));
        assertEquals(0,CheckSumTon.sumToN(-3));
    }
}
