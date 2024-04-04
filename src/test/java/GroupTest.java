import com.example.fxdemos.Child;
import com.example.fxdemos.Group;
import com.example.fxdemos.Sport;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

public class GroupTest {

    @Test
    void testValidConstructor() {
        try {
            Child child1 = new Child("Alice", 10, 120, 30);
            Child child2 = new Child("Bob",9, 110, 25);
            Child child3 = new Child("Charlie",10, 130, 35);
            Child child4 = new Child("David",9, 115, 28);

            ArrayList<Child> childrenList = new ArrayList<>();
            childrenList.add(child1);
            childrenList.add(child2);
            childrenList.add(child3);
            childrenList.add(child4);

            Group group = new Group("Tiny Tigers", 300, 16, new Sport("Football", "outdoor", "blue", 1), childrenList);
            assertEquals("Tiny Tigers", group.getName());
            assertEquals(16, group.getHour());
            assertEquals(300, group.getPrice());
            assertEquals("Football", group.getSport().getName());
            assertEquals(4, childrenList.size());

        } catch (IllegalArgumentException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }
    @Test
    void testReadGroupFromDB() {
        try {
            ArrayList<Group> groupList = Group.readGroupsFromDB();
            assertFalse(groupList.isEmpty());
        } catch (IllegalArgumentException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testReadGroupChildrenListFromDB() {
        try {
            int groupId = 1;
            ArrayList<Child> childList = Group.readGroupChildrenListFromDB(groupId);
            assertFalse(childList.isEmpty());
        } catch (IllegalArgumentException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testTotalCost() {
        try {
            Child child1 = new Child("Alice", 10, 120, 30);
            Child child2 = new Child("Bob", 9, 110, 25);
            Child child3 = new Child("Charlie", 10, 130, 35);
            Child child4 = new Child("David", 9, 115, 28);

            ArrayList<Child> childrenList = new ArrayList<>();
            childrenList.add(child1);
            childrenList.add(child2);
            childrenList.add(child3);
            childrenList.add(child4);

            Group group = new Group("Tiny Tigers", 300, 16, new Sport("Football", "outdoor", "blue", 1), childrenList);
            assertEquals(1200, group.totalCost());
        } catch (IllegalArgumentException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }
}
