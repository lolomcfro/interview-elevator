package bluestaq;

public class Main {

    public static void main(String[] args) {
      Building mainBuilding = new Building(10, 3);

      while (true){
        mainBuilding.readButtons();
        mainBuilding.dispatchElevator();
        mainBuilding.step();
      }

    }
}
