package com.bqsummer.interception;

import com.bqsummer.interception.enums.MouseFlags;
import com.bqsummer.interception.enums.MouseState;

public class MouseHook {

    private final short NONE_ROLLING = 0;

    private final short DEFAULT_ROLLING = 120;

    private final int RELEASE_DELAY = 50;

    private final int DEFAULT_MOUSE_MOVE_SPEED = 15;

    public boolean setMouseState(MouseState state, short rolling) {
        InterceptionKeyStroke stroke = InterceptionKeyStroke.stateStroke((short)state.getValue(), rolling);
        return InterceptionHolder.getInterception().interception_send(InterceptionHolder.getContext(), 12, stroke, 1) == 1;
    }

    public boolean setMouseState(MouseState state) {
        InterceptionKeyStroke stroke = InterceptionKeyStroke.stateStroke((short)state.getValue(), NONE_ROLLING);
        return InterceptionHolder.getInterception().interception_send(InterceptionHolder.getContext(), 12, stroke, 1) == 1;
    }

    private boolean buttonClick(MouseState firstState, MouseState secondState, int releaseDelay) throws InterruptedException {
        if (this.setMouseState(firstState)) {
            Thread.sleep(releaseDelay);
            return this.setMouseState(secondState);
        }
        return false;
    }

    public boolean leftButtonClick(int releaseDelay) throws InterruptedException {
        return this.buttonClick(MouseState.LEFT_BUTTON_DOWN, MouseState.LEFT_BUTTON_UP, releaseDelay);
    }

    public boolean leftButtonClick() throws InterruptedException{
        return this.buttonClick(MouseState.LEFT_BUTTON_DOWN, MouseState.LEFT_BUTTON_UP, RELEASE_DELAY);
    }

    public boolean middleButtonClick(int releaseDelay) throws InterruptedException {
        return this.buttonClick(MouseState.MIDDLE_BUTTON_DOWN, MouseState.MIDDLE_BUTTON_UP, releaseDelay);
    }

    public boolean middleButtonClick() throws InterruptedException {
        return this.buttonClick(MouseState.MIDDLE_BUTTON_DOWN, MouseState.MIDDLE_BUTTON_UP, RELEASE_DELAY);
    }

    public boolean rightButtonClick(int releaseDelay) throws InterruptedException {
        return this.buttonClick(MouseState.RIGHT_BUTTON_DOWN, MouseState.RIGHT_BUTTON_UP, releaseDelay);
    }

    public boolean scrollDown(short rolling) {
        return this.setMouseState(MouseState.SCROLL_VERTICAL, (short)(-rolling));
    }

    public boolean scrollDown() {
        return this.setMouseState(MouseState.SCROLL_VERTICAL, (short)(-DEFAULT_ROLLING));
    }

    public boolean scrollUp(short rolling) {
        return this.setMouseState(MouseState.SCROLL_VERTICAL, rolling);
    }

    public boolean scrollUp() {
        return this.setMouseState(MouseState.SCROLL_VERTICAL, DEFAULT_ROLLING);
    }

    public boolean setCursorPosition(int x, int y) {
        InterceptionKeyStroke stroke = InterceptionKeyStroke.moveStroke(x * 65536 / (2560 -1), y * 65535 / (1440 -1), (short) MouseFlags.MOVE_ABSOLUTE.getValue());
        return InterceptionHolder.getInterception().interception_send(InterceptionHolder.getContext(), 12, stroke, 1) == 1;
    }

    public boolean moveCursorBy(int dX, int dY) {
        System.out.println("moveCursorBy :" + dY);
        MouseStroke stroke = MouseStroke.moveStroke(dX, dY, (short) MouseFlags.MOVE_RELATIVE.getValue());
        return InterceptionHolder.getInterception().interception_send(InterceptionHolder.getContext(), 12, stroke, 1) == 1;
    }
}
