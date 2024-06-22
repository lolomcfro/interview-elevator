package bluestaq;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

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
     * A person is pressing the button on the 1st floor, and wants to go the
     * 3rd. floor. This should result in an elevator being sent up.
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
        Optional<Elevator> elevator = testBuilding.getElevatorList().stream().filter(x -> x.getState() == ElevatorState.GOING_UP).findFirst();
        assertTrue(elevator.isPresent());
        assertFalse(elevator.get().getPassengers().isEmpty());
        //On floor #2
        testBuilding.step();
        //On floor #3
        testBuilding.step();
        assertTrue(testBuilding.getElevatorList().stream().allMatch(x -> x.getState() == ElevatorState.IDLE));
        assertTrue(elevator.get().getPassengers().isEmpty());
    }

    /* 
     * This is a fullscale test, releasing 5 passengers at a time with a step in between to allow the 
     * elevators to start spreating out before the next people hit their buttons. This test will run until 
     * all elevators have been set back to IDLE, after everyone has been dropped off at their respective destinations.
     */
    @Test
    void intensiveTest() {
        int inputIndex = 0;
        Building testBuilding = new Building(numFloors, numElevators);
        List<Person> inputs = inputGenerator(numFloors);
        Random floorRand = new Random();
        //Release 5 of the random passengers at a time with a step in between
        while (inputIndex < 16) {
            inputs.subList(inputIndex, inputIndex + 4).forEach(x -> x.pushButton(testBuilding.getFloorList().get(floorRand.nextInt(numFloors - 1))));
            testBuilding.readButtons();
            testBuilding.dispatchElevator();
            testBuilding.step();
            inputIndex += 5;
        }
        while (testBuilding.getElevatorList().stream().allMatch(x -> x.getState() == ElevatorState.IDLE)) {
            testBuilding.readButtons();
            testBuilding.dispatchElevator();
            testBuilding.step();
        }
        assertTrue(true);

    }

    private List<Person> inputGenerator(int numFloors) {
        List<Person> output = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < 20; i++) {
            output.add(new Person(rand.nextInt(numFloors - 1)));
        }
        return output;

    }
}
