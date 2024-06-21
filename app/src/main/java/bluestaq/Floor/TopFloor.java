package bluestaq.Floor;

import bluestaq.Person;

public class TopFloor extends Floor {

    public TopFloor(int floorNum) {
        super(floorNum);
    }

    /*
     * Pressing the up button can't occur from the top floor.
     */
    public void pressUpButton(Person person) {
        this.waitingUp = false;
    }
}
