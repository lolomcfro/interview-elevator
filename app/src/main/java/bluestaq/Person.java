package bluestaq;

import bluestaq.Floor.Floor;

public class Person {

    private int destination;

    public Person(int destination) {
        this.destination = destination;
    }

    /*
     * This automatically decides if the user needs to go up or down and calls the respective button.
     */
    public void pushButton(Floor floor) {
        if (destination > floor.getFloorNum()) {
            floor.pressUpButton(this);
        } else if (destination < floor.getFloorNum()) {
            floor.pressDownButton(this);
        }
    }

    public int getDestination() {
        return this.destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }
}
