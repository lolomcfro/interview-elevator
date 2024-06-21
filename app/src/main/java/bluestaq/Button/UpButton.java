package bluestaq.Button;

import java.util.ArrayList;

import bluestaq.Button.Button.ButtonState;

public class UpButton extends Button {

    public UpButton() {
        this.pickUpList = new ArrayList<>();
        this.state = ButtonState.NOT_PRESSED;
    }
}
