package Main;

import java.awt.Robot;
import java.awt.AWTException;
import java.awt.event.KeyEvent;

public class Main {
    static Robot rob;
    static int TIMES_TO_PRESS = 300;
    static float TIME_DELAY = 0.4f;
    static int ALT = KeyEvent.VK_ALT;
    static int TAB = KeyEvent.VK_TAB;
    static int RIGHT = KeyEvent.VK_RIGHT;

    public static void main(String[] args) {
        rob = getRobot();
        changeWindow(rob);
        do {
            waitFor(TIME_DELAY);
            typeRightKey(rob);
            TIMES_TO_PRESS--;
        } while (!finished(TIMES_TO_PRESS));
    }

    private static void waitFor(float seconds) {
        try {
            Thread.sleep((long) (seconds * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static boolean finished(int time) {
        return time == 0;
    }

    private static void typeRightKey(Robot robot) {
        pressRightKey(robot);
    }

    private static void pressRightKey(Robot robot) {
        robot.keyPress(RIGHT);
        robot.keyRelease(RIGHT);
    }

    private static void changeWindow(Robot robot) {
        pressAltAndTab(robot);
    }

    private static void pressAltAndTab(Robot robot) {
        robot.keyPress(ALT);
        robot.keyPress(TAB);
        robot.keyRelease(ALT);
        robot.keyRelease(TAB);
    }

    private static Robot getRobot() {
        try {
            return new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
            return null;
        }
    }

}
