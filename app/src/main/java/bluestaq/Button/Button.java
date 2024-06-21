package bluestaq.Button;

import java.util.List;

import bluestaq.Person;

/* 
 * Abstact class definition for a button, this only pertains to the up and down buttons outside the elevator.
 * 
 */
public abstract class Button {

    public enum ButtonState {
        PRESSED,
        NOT_PRESSED
    }

    protected ButtonState state;
    protected List<Person> pickUpList;

    public ButtonState getState() {
        return this.state;
    }

    /*
     * When the people who pushed the button are picked up, reset button and return the list of passengers 
     * so they can be transfereed from waiting on the button into the elevator.
     */
    public List<Person> pickUpComplete() {
        this.state = ButtonState.NOT_PRESSED;
        return pickUpList;
    }

    public void clearPickUpList() {
        pickUpList.clear();
    }

    public void press(Person pickup) {
        this.state = ButtonState.PRESSED;
        pickUpList.add(pickup);
    }

}
