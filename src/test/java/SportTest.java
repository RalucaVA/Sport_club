import com.example.fxdemos.Sport;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class SportTest {
    @Test
    void testValidConstructor() {
        try {
            Sport sport = new Sport("Football", "outdoor", "blue", 1);
            assertEquals("Football", sport.getName());
            assertEquals("outdoor", sport.getPlace());
            assertEquals("blue", sport.getEquipment());
        } catch (IllegalArgumentException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }
}
