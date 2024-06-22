package bluestaq;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashSet;
import java.util.Set;
import bluestaq.Floor.BottomFloor;
import bluestaq.Floor.Floor;
import bluestaq.Floor.TopFloor;

/**
 * This class will dictate the size of the building (how many floors), as well
 * as specify how many elevators the building has.
 */
public class Building {

    private final List<Elevator> elevatorList;
    private List<Floor> floorList;
    Set<Floor> goingUp;
    Set<Floor> goingDown;

    public Building(int numFloors, int numElevators) {
        checkNumFloors(numFloors);
        checkNumElevators(numElevators);
        elevatorList = new ArrayList<>();
        floorList = new ArrayList<>();
        goingUp = new HashSet<>();
        goingDown = new HashSet<>();

        floorList.add(new BottomFloor(0));
        for (int j = 1; j < numFloors - 1; j++) {
            floorList.add(new Floor(j));
        }
        floorList.add(new TopFloor(numFloors));

        for (int i = 0; i < numElevators; i++) {
            elevatorList.add(new Elevator(floorList));
        }
    }

    /*
     * Read all buttons on all floors, make lists of floors that have people waiting to go up and down respectively.
     */
    public void readButtons() {
        this.goingUp.addAll(this.floorList.stream().filter(x -> x.isWaitingUp() == true).toList());
        this.goingDown.addAll(this.floorList.stream().filter(x -> x.isWaitingDown() == true).toList());
    }

    public void dispatchElevator() {
        Object [] goingUpArray = this.goingUp.toArray();

        for (Object goingUpArray1 : goingUpArray) {
            //Find how far away all IDLE elevators are from the floor the button was pushed on
            Floor floor = (Floor) goingUpArray1;
            Map<Integer, Elevator> distanceMapUp = elevatorList.stream().
                    filter(x -> x.getState() == Elevator.ElevatorState.IDLE).
                    collect(Collectors.toMap(x -> Math.abs(x.getCurrentFloor().getFloorNum() - floor.getFloorNum()), x -> x, (first, second) -> first));
            //Find how far away all elevators are that are going up and less than the target floor
            distanceMapUp.putAll(elevatorList.stream().
                    filter(x -> x.getState() == Elevator.ElevatorState.GOING_UP).
                    filter(x -> x.getCurrentFloor().getFloorNum() < floor.getFloorNum()).
                    collect(Collectors.toMap(x -> Math.abs(x.getCurrentFloor().getFloorNum() - floor.getFloorNum()), x -> x, (first, second) -> first)));
            //Pick the elevator that is closest to the pressed button, and add the floor as a destination
            if (!distanceMapUp.isEmpty()){
                Elevator closestElevator = distanceMapUp.get(Collections.min(distanceMapUp.keySet()));
                closestElevator.addPickUp(floor);
                this.goingUp.remove(floor);
                closestElevator.setState(Elevator.ElevatorState.GOING_UP);
            }
        }
        Object [] goingDownArray = this.goingDown.toArray();
        for (Object goingDownArray1 : goingDownArray) {
            Floor floor = (Floor) goingDownArray1;
            //Find how far away all IDLE elevators are from the floor the button was pushed on
            Map<Integer, Elevator> distanceMapDown = elevatorList.stream().
                    filter(x -> x.getState() == Elevator.ElevatorState.IDLE).
                    collect(Collectors.toMap(x -> Math.abs(x.getCurrentFloor().getFloorNum() - floor.getFloorNum()), x -> x, (first, second) -> first));
            //Find how far away all elevators are that are going down and more than the target floor
            distanceMapDown.putAll(elevatorList.stream().
                    filter(x -> x.getState() == Elevator.ElevatorState.GOING_DOWN).
                    filter(x -> x.getCurrentFloor().getFloorNum() > floor.getFloorNum()).
                    collect(Collectors.toMap(x -> Math.abs(x.getCurrentFloor().getFloorNum() - floor.getFloorNum()), x -> x, (first, second) -> first)));
            //Pick the elevator that is closest to the pressed button, and add the floor as a destination
            if (!distanceMapDown.isEmpty()){
                Elevator closestElevator = distanceMapDown.get(Collections.min(distanceMapDown.keySet()));
                closestElevator.addPickUp(floor);
                this.goingUp.remove(floor);
                closestElevator.setState(Elevator.ElevatorState.GOING_DOWN);
            }
        }
    }

    /*
     * Simulates elevators stepping up or down so there is movement in the system.
     */
    public void step() {
        elevatorList.forEach(x -> x.step());
    }
    private void checkNumFloors(int numFloors) {
        if (numFloors <= 0) {
            throw new IllegalArgumentException("Number of floors must be greater than 0");
        }
    }

    private void checkNumElevators(int numElevators) {
        if (numElevators <= 0) {
            throw new IllegalArgumentException("Number of elevators must be greater than 0");
        }
    }

    public List<Elevator> getElevatorList() {
        return elevatorList;
    }

    public List<Floor> getFloorList() {
        return floorList;
    }

    public Set<Floor> getGoingUpList(){
        return this.goingUp;
    }
    public Set<Floor> getGoingDownList(){
        return this.goingDown;
    }
}
