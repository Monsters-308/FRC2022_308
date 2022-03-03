package frc.robot.subsystems;

import java.util.Random;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.LEDConstants;

public class LEDSubsystem extends SubsystemBase {
    public enum LEDState {
        NONE,
        RAINBOW,
        FRENZY,
        RED,
        GREEN,
        BLUE,
        PURPLE,
        RED_PULSE,
        RED_STREAK,
        GREEN_STREAK,
        BLUE_STREAK,
        RED_BLINK,
        GREEN_BLINK,
        BLUE_BLINK,
        PURPLE_BLINK
    }

    private AddressableLED m_led;
    private AddressableLEDBuffer m_ledBuffer;
    // Store what the last hue of the first pixel is
    private int m_rainbowFirstPixelHue;
    private int redPulseBrightness = 0;
    private int redStreakLED = 0;
    private int greenStreakLED = 0;
    private int blueStreakLED = 0;
    private int numLoops = 0;
    private LEDState m_led_mode_state = LEDState.RAINBOW; // used for loop modes of the led's
    private int led_loop_count = 0; // used for loop modes of the led's

    public LEDSubsystem() {
        // Must be a PWM header, not MXP or DIO
        m_led = new AddressableLED(LEDConstants.kLEDPWMPort);

        // Reuse buffer
        // Default to a length of 60, start empty output
        // Length is expensive to set, so only set it once, then just update data
        m_ledBuffer = new AddressableLEDBuffer(LEDConstants.kLEDCount);
        m_led.setLength(m_ledBuffer.getLength());

        // Set the data
        m_led.setData(m_ledBuffer);
        m_led.start();
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        // Fill the buffer with a rainbow

        // here we will create a state system to have the external command system
        // opperate new states
        switch (m_led_mode_state) {
            case RAINBOW:
                rainbow();
                break;
            case FRENZY:
                Random rand = new Random();
                if (led_loop_count++ % 10 == 0) {
                    frenzy(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));

                }
                break;
            case RED:
                red();
                break;
            case GREEN:
                green();
                break;
            case BLUE:
                blue();
                break;
            case PURPLE:
                purple();
                break;
            case RED_PULSE:
                redPulse();
                break;
            case RED_STREAK:
                redStreak();
                break;
            case GREEN_STREAK:
                greenStreak();
                break;
            case BLUE_STREAK:
                blueStreak();
                break;
            case RED_BLINK:
                if (led_loop_count++ % 20 == 0) {
                    red();
                } else if (led_loop_count % 10 == 0) {
                    clearAll();
                }
                break;
            case GREEN_BLINK:
                if (led_loop_count++ % 20 == 0) {
                    green();
                } else if (led_loop_count % 10 == 0) {
                    clearAll();
                }
                break;
            case BLUE_BLINK:
                if (led_loop_count++ % 20 == 0) {
                    blue();
                } else if (led_loop_count % 10 == 0) {
                    clearAll();
                }
                break;
            case PURPLE_BLINK:
                if (led_loop_count++ % 20 == 0) {
                    purple();
                } else if (led_loop_count % 10 == 0) {
                    clearAll();
                }
                break;
            case NONE:
                break;
        }
    }

    public void setLEDState(LEDState ledState) {
        m_led_mode_state = ledState;
    }

    public void rainbow() {
        // For every pixel
        for (var i = 0; i < m_ledBuffer.getLength(); i++) {
            // Calculate the hue - hue is easier for rainbows because the color
            // shape is a circle so only one value needs to precess
            final var hue = (m_rainbowFirstPixelHue + (i * 180 / m_ledBuffer.getLength())) % 180;
            // Set the value
            m_ledBuffer.setHSV(i, hue, 255, 128);
        }
        // Increase by to make the rainbow "move"
        m_rainbowFirstPixelHue += 3;
        // Check bounds
        m_rainbowFirstPixelHue %= 180;

        m_led.setData(m_ledBuffer);
    }

    public void frenzy(int rcolor, int gcolor, int bcolor) {
        // For every pixel
        for (var i = 0; i < m_ledBuffer.getLength(); i++) {
            m_ledBuffer.setRGB(i, rcolor, gcolor, bcolor);
        }

        m_led.setData(m_ledBuffer);
    }

    public void red() {
        for (var i = 0; i < m_ledBuffer.getLength(); i++) {
            // Sets the specified LED to the RGB values for red
            m_ledBuffer.setRGB(i, 255, 0, 0);
        }

        m_led.setData(m_ledBuffer);
    }

    public void blue() {
        for (var i = 0; i < m_ledBuffer.getLength(); i++) {
            // Sets the specified LED to the RGB values for red
            m_ledBuffer.setRGB(i, 0, 0, 255);
        }

        m_led.setData(m_ledBuffer);
    }

    public void green() {
        for (var i = 0; i < m_ledBuffer.getLength(); i++) {
            // Sets the specified LED to the RGB values for red
            m_ledBuffer.setRGB(i, 0, 255, 0);
        }

        m_led.setData(m_ledBuffer);
    }

    public void purple() {
        for (var i = 0; i < m_ledBuffer.getLength(); i++) {
            // Sets the specified LED to the RGB values for purple
            m_ledBuffer.setRGB(i, 148, 0, 211);
        }
        m_led.setData(m_ledBuffer);
    }

    public void redPulse() {
        for (var i = 0; i < m_ledBuffer.getLength(); i++) {
            // Sets the specified LED to the RGB values for blue
            m_ledBuffer.setRGB(i, redPulseBrightness, 0, 0);
        }

        // increase brightness
        redPulseBrightness += 5;

        // Check bounds
        redPulseBrightness %= 255;

        m_led.setData(m_ledBuffer);
    }

    public void redStreak() {
        for (var i = 0; i < m_ledBuffer.getLength(); i++) {
            // Sets the specified LED to the RGB values for blue
            m_ledBuffer.setRGB(i, 255, 0, 0);
        }

        // turns one led off
        m_ledBuffer.setRGB(redStreakLED, 0, 0, 0);

        // increase brightness
        if (numLoops % 3 == 0) {
            redStreakLED += 1;
            // Check bounds
            redStreakLED %= m_ledBuffer.getLength();
        }

        m_led.setData(m_ledBuffer);

        numLoops += 1;

    }

    public void greenStreak() {
        for (var i = 0; i < m_ledBuffer.getLength(); i++) {
            // Sets the specified LED to the RGB values for blue
            m_ledBuffer.setRGB(i, 0, 255, 0);
        }

        // turns one led off
        m_ledBuffer.setRGB(greenStreakLED, 0, 0, 0);

        // increase brightness
        if (numLoops % 3 == 0) {
            greenStreakLED += 1;
            // Check bounds
            greenStreakLED %= m_ledBuffer.getLength();
        }

        m_led.setData(m_ledBuffer);

        numLoops += 1;

    }

    public void blueStreak() {
        for (var i = 0; i < m_ledBuffer.getLength(); i++) {
            // Sets the specified LED to the RGB values for blue
            m_ledBuffer.setRGB(i, 0, 0, 255);
        }

        // turns one led off
        m_ledBuffer.setRGB(blueStreakLED, 0, 0, 0);

        // increase brightness
        if (numLoops % 3 == 0) {
            blueStreakLED += 1;
            // Check bounds
            blueStreakLED %= m_ledBuffer.getLength();
        }

        m_led.setData(m_ledBuffer);

        numLoops += 1;

    }

    public void clearAll() {
        for (var i = 0; i < m_ledBuffer.getLength(); i++) {
            // Sets the specified LED to the RGB values for purple
            m_ledBuffer.setRGB(i, 0, 0, 0);
        }
        m_led.setData(m_ledBuffer);

    }

    public void clearRange(int startIdx, int endIdx) {
        int startLED = startIdx < endIdx ? startIdx : endIdx;
        int endLED = startIdx < endIdx ? endIdx : startIdx;
        if (startLED < 0 || startLED >= LEDConstants.kLEDCount) {
            startLED = 0;
        }
        if (endLED < 0 || endLED >= LEDConstants.kLEDCount) {
            endLED = LEDConstants.kLEDCount;
        }

        for (var i = startLED; i < endLED; i++) {
            // Sets the specified LED to the RGB values for purple
            m_ledBuffer.setRGB(i, 0, 0, 0);
        }
        m_led.setData(m_ledBuffer);

    }

    public void setRedRange(int startIdx, int endIdx) {
        int startLED = startIdx < endIdx ? startIdx : endIdx;
        int endLED = startIdx < endIdx ? endIdx : startIdx;
        if (startLED < 0 || startLED >= LEDConstants.kLEDCount) {
            startLED = 0;
        }
        if (endLED < 0 || endLED >= LEDConstants.kLEDCount) {
            endLED = LEDConstants.kLEDCount;
        }

        for (var i = startLED; i < endLED; i++) {
            // Sets the specified LED to the RGB values for purple
            m_ledBuffer.setRGB(i, 255, 0, 0);
        }
        m_led.setData(m_ledBuffer);

    }

    public void setGreenRange(int startIdx, int endIdx) {
        int startLED = startIdx < endIdx ? startIdx : endIdx;
        int endLED = startIdx < endIdx ? endIdx : startIdx;
        if (startLED < 0 || startLED >= LEDConstants.kLEDCount) {
            startLED = 0;
        }
        if (endLED < 0 || endLED >= LEDConstants.kLEDCount) {
            endLED = LEDConstants.kLEDCount;
        }

        for (var i = startLED; i < endLED; i++) {
            // Sets the specified LED to the RGB values for purple
            m_ledBuffer.setRGB(i, 0, 255, 0);
        }
        m_led.setData(m_ledBuffer);

    }

    public void setBlueRange(int startIdx, int endIdx) {
        int startLED = startIdx < endIdx ? startIdx : endIdx;
        int endLED = startIdx < endIdx ? endIdx : startIdx;
        if (startLED < 0 || startLED >= LEDConstants.kLEDCount) {
            startLED = 0;
        }
        if (endLED < 0 || endLED >= LEDConstants.kLEDCount) {
            endLED = LEDConstants.kLEDCount;
        }

        for (var i = startLED; i < endLED; i++) {
            // Sets the specified LED to the RGB values for purple
            m_ledBuffer.setRGB(i, 0, 0, 255);
        }
        m_led.setData(m_ledBuffer);

    }

}
