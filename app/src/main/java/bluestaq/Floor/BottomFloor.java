package bluestaq.Floor;

import bluestaq.Person;

public class BottomFloor extends Floor {

    public BottomFloor(int floorNum) {
        super(floorNum);
    }

    /*
     * Pressing the down button can't occur from the bottom floor.
     */
    public void pressDownButton(Person person) {
        this.waitingDown = false;
    }
}
