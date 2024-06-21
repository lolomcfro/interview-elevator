package bluestaq;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import bluestaq.Elevator.ElevatorState;
import bluestaq.Floor.BottomFloor;
import bluestaq.Floor.Floor;
import bluestaq.Floor.TopFloor;

public class ElevatorTest {
    private List<Floor> floorList;
    @BeforeEach
    void setUp(){
        floorList = new ArrayList<>();
        floorList.add(new BottomFloor(0));
        floorList.add(new Floor(1));
        floorList.add(new TopFloor(2));
    }
    /*
     * Test that all new elevators begin at the ground floor with an IDLE state.
     */
    @Test
    void initTest(){
        Elevator testElevator = new Elevator(floorList);
        assertEquals(Elevator.ElevatorState.IDLE, testElevator.getState());
        assertEquals(0, testElevator.getCurrentFloor().getFloorNum());
    }

    /*
     * Test that when a passenger is picked up, they are added to the passenger list.
     */
    @Test
    void pickupTest(){
        Person testPerson = new Person(5);
        Elevator testElevator = new Elevator(floorList);
        testElevator.addPassenger(Collections.singletonList(testPerson));

        assertTrue(testElevator.getPassengers().contains(testPerson));
    }

    /**
     * If the elevator is at a passengers stop, remove them from the passenger list.
     */
    @Test
    void dropOffTest(){
        Person testPerson = new Person(1);
        Elevator testElevator = new Elevator(floorList);
        testElevator.addPassenger(Collections.singletonList(testPerson));
        testElevator.setState(ElevatorState.GOING_UP);
        testElevator.step();

        assertFalse(testElevator.getPassengers().contains(testPerson));
    }


    
}