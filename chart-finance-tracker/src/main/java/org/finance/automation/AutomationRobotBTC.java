package org.finance.automation;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.Getter;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import static org.finance.automation.AutomationRobotBTC.ComponentsCoordinates.BACK_BUTTON;

@ApplicationScoped
public class AutomationRobotBTC implements TradeActions {

    private final String BTC_TO_BUY = "2";
    private final String BTC_TO_SELL = "0.00009";
    private static final int SMALL_DELAY = 70;

    private Robot robot;

    @Override
    public void doBuy() {
        performAction(ComponentsCoordinates.BUY_BUTTON, BTC_TO_BUY);
    }

    @Override
    public void doSell() {
        performAction(ComponentsCoordinates.SELL_BUTTON, BTC_TO_SELL);
    }


    public void doPreparationHandleAction() {

    }

    private void performAction(ComponentsCoordinates actionButton,
                               String amount) {

        Robot robot = getRobotInstance();

        moveMouseToAndClick(robot, actionButton.getCoordinate());
        doSleep();

        moveMouseToAndClick(robot, ComponentsCoordinates.WRITE_FIELD.getCoordinate());
        doSleep();

        writeValue(robot, amount);
        doSleep();

        moveMouseToAndClick(robot, BACK_BUTTON.getCoordinate());
    }

    private void moveMouseToAndClick(Robot robot, Coordinate coordinate) {
        robot.mouseMove(coordinate.x, coordinate.y);
        doSleep();
        doLeftClick(robot);
        doSleep();
    }

    private void writeValue(Robot robot, String amount) {
        String valueAsString = String.valueOf(amount);

        for (char c : valueAsString.toCharArray()) {
            int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
            robot.keyPress(keyCode);
            robot.keyRelease(keyCode);
        }
    }

    private void doSleep() {
        try {
            Thread.sleep(AutomationRobotBTC.SMALL_DELAY);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void doLeftClick(Robot robot) {
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    private Robot getRobotInstance() {
        if (robot == null) {
            try {
                robot = new Robot();
            } catch (AWTException e) {
                throw new RuntimeException(e);
            }
        }
        return robot;
    }


    @Getter
    enum ComponentsCoordinates {
        BACK_BUTTON(new Coordinate(100, 100)),
        BUY_BUTTON(new Coordinate(125, 275)),
        SELL_BUTTON(new Coordinate(250, 275)),
        WRITE_FIELD(new Coordinate(500, 440));

        private final Coordinate coordinate;

        ComponentsCoordinates(Coordinate coordinate) {
            this.coordinate = coordinate;
        }
    }

    @Getter
    private static class Coordinate {
        private final int x;
        private final int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
