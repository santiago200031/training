package com.robot;

import javax.swing.JFrame;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.logging.Logger;

public class WhatsAppMessageSender {

    private static final Logger LOGGER = Logger.getLogger(WhatsAppMessageSender.class.getName());
    private static final int CONTACTS_TO_SKIP = 197;
    private static final int CONTACTS_LENGTH = 223;

    private static final int QUIT_KEY = KeyEvent.VK_Q;

    private static final int EXTRA_SHORT_DELAY = 90;
    private static final int DOWN_KEY_DELAY = 100;
    private static final int SHORT_DELAY = 150;
    private static final int MESSAGE_DELAY = 500;

    public static void main(String[] args) throws InterruptedException {
        Robot robot = initializeRobot();

        JFrame frame = createJFrame();
        addQuitKeyListener(frame);

        switchToWhatsAppWindow(robot);

        LOGGER.info("Running...");

        for (int i = 0; i < CONTACTS_LENGTH - CONTACTS_TO_SKIP; i++) {
            processContact(robot, i);
        }

        LOGGER.info("Stop running...");
    }

    private static Robot initializeRobot() {
        try {
            return new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    private static JFrame createJFrame() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(200, 700);
        frame.setVisible(true);
        return frame;
    }

    private static void addQuitKeyListener(JFrame frame) {
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == QUIT_KEY) {
                    System.exit(0);
                }
            }
        });
    }

    private static void switchToWhatsAppWindow(Robot robot) throws InterruptedException {
        pressAndRelease(robot, KeyEvent.VK_ALT, KeyEvent.VK_TAB, KeyEvent.VK_TAB);
        Thread.sleep(SHORT_DELAY);
    }

    private static void processContact(Robot robot, int contactNumber) throws InterruptedException {
        openNewMessageWindow(robot);
        switchFocusToContacts(robot);
        skipContacts(robot, CONTACTS_TO_SKIP + contactNumber);
        joinChat(robot);

        LOGGER.info("Sending message to contact number " + (contactNumber + 1));
        sendMessage(robot);

        LOGGER.info("Message sent to " + (contactNumber + 1) + " contacts\n");
    }

    private static void openNewMessageWindow(Robot robot) throws InterruptedException {
        pressAndRelease(robot, KeyEvent.VK_CONTROL, KeyEvent.VK_N);
        Thread.sleep(SHORT_DELAY);
    }

    private static void switchFocusToContacts(Robot robot) throws InterruptedException {
        pressAndRelease(robot, KeyEvent.VK_TAB, KeyEvent.VK_TAB);
        Thread.sleep(SHORT_DELAY);
    }

    private static void skipContacts(Robot robot, int times) throws InterruptedException {
        LOGGER.info("Skipping " + times + " contacts");
        for (int j = 0; j < times; j++) {
            pressAndRelease(robot, KeyEvent.VK_DOWN);
            Thread.sleep(DOWN_KEY_DELAY);
        }
        Thread.sleep(DOWN_KEY_DELAY);
    }

    private static void sendMessage(Robot robot) throws InterruptedException {
        pressAndRelease(robot, KeyEvent.VK_CONTROL, KeyEvent.VK_V);
        Thread.sleep(SHORT_DELAY);

        pressAndRelease(robot, KeyEvent.VK_ENTER);
        Thread.sleep(MESSAGE_DELAY);
    }

    private static void joinChat(Robot robot) throws InterruptedException {
        pressAndRelease(robot, KeyEvent.VK_ENTER);
        Thread.sleep(SHORT_DELAY);
    }

    private static void pressAndRelease(Robot robot, int... keyCodes) throws InterruptedException {
        for (int keyCode : keyCodes) {
            robot.keyPress(keyCode);
            Thread.sleep(EXTRA_SHORT_DELAY);
        }
        for (int i = keyCodes.length - 1; i >= 0; i--) {
            robot.keyRelease(keyCodes[i]);
            Thread.sleep(EXTRA_SHORT_DELAY);
        }
    }
}
