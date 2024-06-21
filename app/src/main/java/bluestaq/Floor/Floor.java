package bluestaq.Floor;

import java.util.ArrayList;
import java.util.List;

import bluestaq.Button.DownButton;
import bluestaq.Button.UpButton;
import bluestaq.Person;

public class Floor {

    protected boolean waitingUp;
    protected boolean waitingDown;
    private int floorNum;
    private UpButton upButton;
    private DownButton downButton;

    public Floor(int floorNum) {
        this.waitingUp = false;
        this.waitingDown = false;
        this.floorNum = floorNum;
        upButton = new UpButton();
        downButton = new DownButton();
    }

    public List<Person> pickUpGoingUp() {
        this.waitingUp = false;
        List<Person> returnList = new ArrayList<>();
        for (Person person: upButton.pickUpComplete()){
            returnList.add(person);
        }
        return returnList;
    }

    public List<Person> pickUpGoingDown() {
        this.waitingDown = false;
        List<Person> returnList = new ArrayList<>();
        for (Person person: downButton.pickUpComplete()){
            returnList.add(person);
        }
        return returnList;
    }

    public void pressUpButton(Person person) {
        this.waitingUp = true;
        upButton.press(person);
    }

    public void pressDownButton(Person person) {
        this.waitingDown = true;
        downButton.press(person);
    }

    public int getFloorNum() {
        return floorNum;
    }

    public boolean isWaitingUp() {
        return waitingUp;
    }

    public boolean isWaitingDown() {
        return waitingDown;
    }

    public UpButton getUpButton(){
        return this.upButton;
    }

    public DownButton getDownButton(){
        return this.downButton;
    }
}
