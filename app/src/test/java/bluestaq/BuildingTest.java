package bluestaq;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bluestaq.Elevator.ElevatorState;

public class BuildingTest {

    int numFloors = 5;
    int numElevators = 2;

    @Test
    void badInitTest() {
        assertThrows(IllegalArgumentException.class,
                () -> {
                    new Building(0, 0);
                });
    }

    @Test
    void initTest() {

        Building testBuilding = new Building(numFloors, numElevators);
        assertEquals(numFloors, testBuilding.getFloorList().size());
        assertEquals(numElevators, testBuilding.getElevatorList().size());
    }

    /**
     * A person is pressing the button on the 1st floor, and wants to go the 3rd.
     * floor.
     * This should result in an elevator being sent up.
     */
    @Test
    void elevatorDispatchTest() {
        Building testBuilding = new Building(numFloors, numElevators);
        Person testPerson = new Person(3);
        testPerson.pushButton(testBuilding.getFloorList().get(1));
        testBuilding.readButtons();
        testBuilding.dispatchElevator();

        //Verify that an elevator has started going up.
        assertTrue(testBuilding.getElevatorList().stream().anyMatch(x -> x.getState() == ElevatorState.GOING_UP));
    }

    /*
     * Person presses up from Floor #1.
     * Ensure elevator has been sent up.
     * Since all elevators start on Floor #0, after 1 step, a passenger should have been picked up. Elevator passenger list should not be empty anymore.
     * Once the elevator has arrived on Floor #3, it should be set to idle and there should no longer be any passengers.
     */
    @Test
    void singlePersonTest() {
        Building testBuilding = new Building(numFloors, numElevators);
        Person testPerson = new Person(3);
        testPerson.pushButton(testBuilding.getFloorList().get(1));
        testBuilding.readButtons();
        testBuilding.dispatchElevator();
        assertTrue(testBuilding.getElevatorList().stream().anyMatch(x -> x.getState() == ElevatorState.GOING_UP));
        //On floor #1
        testBuilding.step();
        assertTrue(testBuilding.getElevatorList().stream().anyMatch(x -> x.getState() == ElevatorState.GOING_UP));
        Optional <Elevator> elevator = testBuilding.getElevatorList().stream().filter(x -> x.getState() == ElevatorState.GOING_UP).findFirst();
        assertTrue(elevator.isPresent());
        assertFalse(elevator.get().getPassengers().isEmpty());
        //On floor #2
        testBuilding.step();
        //On floor #3
        testBuilding.step();
        assertTrue(testBuilding.getElevatorList().stream().allMatch(x -> x.getState() == ElevatorState.IDLE));
        assertTrue(elevator.get().getPassengers().isEmpty());
    }
}
