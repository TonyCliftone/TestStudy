import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCheckBlastOff {
    @Test
    public void checkWhenBlastOff() {
        assertEquals("5 4 3 2 1 Поехали!", CheckBlastOff.blastOff(5));
        assertEquals("1 Поехали!", CheckBlastOff.blastOff(1));
    }
}
