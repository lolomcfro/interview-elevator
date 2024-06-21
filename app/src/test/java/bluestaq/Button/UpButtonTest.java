package bluestaq.Button;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import bluestaq.Person;

public class UpButtonTest {
    /*
     * Make sure the button is initialized to NOT_PRESSED
     */
    @Test 
    void UpButtonInitTest(){
        UpButton testButton = new UpButton();
        assertEquals(Button.ButtonState.NOT_PRESSED, testButton.getState());
    }

    /*
     * If a person is pressed a button, they should be on the button's pickup list.
     */
    @Test
    void pickUpTest(){
        Person testPerson = new Person(5);
        UpButton testButton = new UpButton();
        testButton.press(testPerson);
        List<Person> pickUpList = testButton.pickUpComplete();
        assertTrue(pickUpList.contains(testPerson));
    }
}
