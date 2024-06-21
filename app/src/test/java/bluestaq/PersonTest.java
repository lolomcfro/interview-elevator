package bluestaq;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import bluestaq.Button.Button;
import bluestaq.Floor.Floor;

public class PersonTest {

    /*
     * A person pressing the elevator on floor 3, and wants go get to 5.
     */
    @Test
    void goUpTest(){
        Floor testFloor = new Floor(3);
        Person testPerson = new Person(5);

        testPerson.pushButton(testFloor);

        assertEquals(Button.ButtonState.PRESSED, testFloor.getUpButton().getState());
        assertTrue(testFloor.isWaitingUp());
    }

    /*
     * A person pressing the elevator on floor 5, and wants go get to 3.
     */
    @Test
    void goDownTest(){
        Floor testFloor = new Floor(5);
        Person testPerson = new Person(3);

        testPerson.pushButton(testFloor);

        assertEquals(Button.ButtonState.PRESSED, testFloor.getDownButton().getState());
        assertTrue(testFloor.isWaitingDown());
    }

    /*
     * Ignore button push if destination == floor
     */
    @Test
    void goNowhereTest(){
        Floor testFloor = new Floor(5);
        Person testPerson = new Person(5);

        testPerson.pushButton(testFloor);

        assertEquals(Button.ButtonState.NOT_PRESSED, testFloor.getUpButton().getState());
        assertEquals(Button.ButtonState.NOT_PRESSED, testFloor.getDownButton().getState());
        assertFalse(testFloor.isWaitingDown());
        assertFalse(testFloor.isWaitingUp());
    }
}