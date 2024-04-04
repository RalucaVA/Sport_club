import com.example.fxdemos.Child;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChildTest {

    @Test
    void testValidConstructor() {
        try {
            Child child = new Child("Ana", 12, 23, 127);
            assertEquals("Ana", child.getName());
            assertEquals(12, child.getAge());
            assertEquals(23, child.getWeight());
            assertEquals(127, child.getHeight());
        } catch (IllegalArgumentException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testCompareToEqual() {
        // same height and weight
        Child child1 = new Child("Alice", 10, 120, 30);
        Child child2 = new Child("Bob", 10, 120, 30);
        assertEquals(0, child1.compareTo(child2));
    }

    @Test
    void testCompareToDifferentHeight() {
        //different heights, but same weight
        Child child1 = new Child("Alice", 10, 120, 30);
        Child child2 = new Child("Bob", 10, 130, 30);
        assertTrue(child1.compareTo(child2) < 0); // child1 is shorter than child2
    }

    @Test
    void testCompareToDifferentWeight() {
        // different weights, but same height
        Child child1 = new Child("Alice", 10, 120, 30);
        Child child2 = new Child("Bob", 10, 120, 35);
        assertTrue(child1.compareTo(child2) < 0);
    }

    @Test
    void testCalculateBMI() {
        try{
            Child child = new Child("Ana", 12, 32, 140);
            int n= (int) (32/(1.4*1.4));
            assertTrue(n < child.calculateBMI() && child.calculateBMI()<n+1);
        } catch (IllegalArgumentException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }
}
