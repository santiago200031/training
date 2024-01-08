package com.robot;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Program {
    static Robot rob;
    static int NUMBER_OF_STORIES = 0;
    static int BG_COLOR = -15066598;
    static float TIME_DELAY = 0.5f;

    static int ALT = KeyEvent.VK_ALT;
    static int TAB = KeyEvent.VK_TAB;
    static int RIGHT = KeyEvent.VK_RIGHT;

    public static void main(String[] args) {
        rob = getRobot();
        changeWindow(rob);
        do {
            waitFor(TIME_DELAY);
            pressAndRelease(rob, RIGHT);
            incrementStoryCounter();
            printNumberOfStories();
        } while (!finished(getColorScreen(rob)));
    }

    private static void incrementStoryCounter() {
        NUMBER_OF_STORIES++;
    }

    private static void printNumberOfStories() {
        System.out.println("Story number " + NUMBER_OF_STORIES);
    }

    private static Color getColorScreen(Robot rob) {
        return rob.getPixelColor(1000, 100);
    }


    private static void pressAndRelease(Robot robot, int whichKey) {
        robot.keyPress(whichKey);
        robot.keyRelease(whichKey);
    }

    private static void waitFor(float seconds) {
        try {
            Thread.sleep((long) (seconds * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static boolean finished(Color bgColor) {
        return bgColor.getRGB() != BG_COLOR;
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
