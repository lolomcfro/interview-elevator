package bluestaq;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import bluestaq.Button.Button;
import bluestaq.Floor.Floor;

public class FloorTest {
    /*
     * Check initialization, no one is waiting on the floor yet.
     */
    @Test
    void floorInitTest()
    {
        Floor testFloor = new Floor(0);
        assertFalse(testFloor.isWaitingDown());
        assertFalse(testFloor.isWaitingUp());
    }

    /*
     * Once someone pushes the UpButton and is pickedUp, they should be returned in the pickupList.
     */
    @Test
    void pressUpButtonTest()
    {
        Floor testFloor = new Floor(5);
        Person testPerson = new Person(10);
        testFloor.pressUpButton(testPerson);
        List<Person> pickups = testFloor.pickUpGoingUp();
        assertTrue(pickups.contains(testPerson));
    }

    /*
     * Once someone pushes the DownButton and is pickedUp, they should be returned in the pickupList.
     */
    @Test
    void pressDownButtonTest()
    {
        Floor testFloor = new Floor(10);
        Person testPerson = new Person(5);
        testFloor.pressDownButton(testPerson);
        List<Person> pickups = testFloor.pickUpGoingDown();
        assertTrue(pickups.contains(testPerson));
    }
}