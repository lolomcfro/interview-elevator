package bluestaq;

import java.util.ArrayList;
import java.util.List;

import bluestaq.Elevator.ElevatorState;
import bluestaq.Floor.Floor;

public class Elevator {

    enum ElevatorState {
        GOING_UP,
        GOING_DOWN,
        IDLE,
        BROKEN
    }

    private int currentFloorIndex;
    private List<Floor> floors;
    private List<Floor> pickUps;
    private ElevatorState state;
    private List<Person> passengers;

    /**
     * Contructor Intialize to the bottom floor and in the WAITING state.
     */
    public Elevator(List<Floor> floors) {
        this.currentFloorIndex = 0;
        this.floors = floors;
        this.state = ElevatorState.IDLE;
        this.pickUps = new ArrayList<>();
        this.passengers = new ArrayList<>();
    }

    /**
     * Make our elevator move, and make sure it doesn't go outside its bounds.
     */
    public void step() {
        switch (this.state) {
            case GOING_UP:
                if (this.floors.get(this.currentFloorIndex).getFloorNum() < floors.size()) {
                    this.currentFloorIndex++;
                }
                break;
            case GOING_DOWN:
                if (this.floors.get(this.currentFloorIndex).getFloorNum() > 0) {
                    this.currentFloorIndex--;
                }
                break;
            default:
        }

        checkPassengers();
    }

    /*
    * After every change in position of the elevator, remove every passenger who's destination was this floor.
    * Then, pickup every passenger waiting at the floor.
    * If no one is waiting, and no one is on the elevator, move to IDLE state.
    */
    private void checkPassengers() {
        this.passengers.removeIf(x -> x.getDestination() == this.floors.get(this.currentFloorIndex).getFloorNum());

        if (this.state == ElevatorState.GOING_UP && pickUps.contains(this.floors.get(this.currentFloorIndex))) {
            this.passengers.addAll(this.floors.get(this.currentFloorIndex).pickUpGoingUp());
            this.pickUps.remove(this.floors.get(this.currentFloorIndex));
        } else if (this.state == ElevatorState.GOING_DOWN && pickUps.contains(this.floors.get(this.currentFloorIndex))) {
            this.passengers.addAll(this.floors.get(this.currentFloorIndex).pickUpGoingDown());
            this.pickUps.remove(this.floors.get(this.currentFloorIndex));
        }

        if (pickUps.isEmpty() && passengers.isEmpty()) {
            this.state = ElevatorState.IDLE;
        }
    }

    public void addPickUp(Floor floor) {
        this.pickUps.add(floor);
    }

    public void addPassenger(List<Person> passengers) {
        this.passengers.addAll(passengers);
    }

    public List<Person> getPassengers() {
        return passengers;
    }

    public ElevatorState getState() {
        return state;
    }

    public void setState(ElevatorState state) {
        this.state = state;
    }

    public Floor getCurrentFloor() {
        return this.floors.get(this.currentFloorIndex);
    }
}
