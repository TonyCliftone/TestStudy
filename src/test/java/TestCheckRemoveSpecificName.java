import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCheckRemoveSpecificName {
    @Test
    public void testCheckRemovingNameAlesha() {
        List<String> allNames = Arrays.asList("Gosha", "Tosha", "Alesha");
        List<String> notAllNames = Arrays.asList("Gosha", "Tosha");
        assertEquals(notAllNames, CheckRemoveSpecificName.removeSpecificName(allNames, "Alesha"));
    }

    @Test
    public void testCheckRemovingNameEmpty() {
        List<String> allNames = Collections.emptyList();
        List<String> notAllNames = Collections.emptyList();
        assertEquals(notAllNames, CheckRemoveSpecificName.removeSpecificName(allNames, "Alesha"));
    }
}
