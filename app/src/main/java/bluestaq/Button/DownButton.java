package bluestaq.Button;

import java.util.ArrayList;

import bluestaq.Button.Button.ButtonState;

public class DownButton extends Button {

    public DownButton() {
        this.pickUpList = new ArrayList<>();
        this.state = ButtonState.NOT_PRESSED;
    }
}
