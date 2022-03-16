package frc.robot.subsystems;

import java.util.Random;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.LEDConstants;

public class LEDSubsystem extends SubsystemBase {
    public enum LEDState {
        NONE,
        RAINBOW,
        FRENZY,
        SOLID,
        PULSE,
        STREAK,
        BLINK
    }

    private AddressableLED m_led;
    private AddressableLEDBuffer m_ledBuffer;

    private LEDState m_ledMode = LEDState.RAINBOW;
    private Color m_ledColor = Color.kRed;

    // Store what the last hue of the first pixel is
    private int m_rainbowFirstPixelHue;
    private int redPulseBrightness = 0;
    private int greenPulseBrightness = 0;
    private int bluePulseBrightness = 0;
    private int streakLED = 0;
    private int numLoops = 0;
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
        switch (m_ledMode) {
            case RAINBOW:
                rainbow();
                break;
            case FRENZY:
                Random rand = new Random();
                if (led_loop_count++ % 10 == 0) {
                    frenzy(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
                }
                break;
            case SOLID:
                solid();
                break;
            case PULSE:
                pulse();
                break;
            case STREAK:
                streak();
                break;
            case BLINK:
                if (led_loop_count++ % 20 == 0) {
                    solid();
                } else if (led_loop_count % 10 == 0) {
                    clearAll();
                }
                break;
            case NONE:
                break;
        }
    }

    public void setLEDState(LEDState mode) {
        m_ledMode = mode;
    }

    public void setLEDState(Color color) {
        m_ledColor = color;
    }

    public void setLEDState(Color color, LEDState mode) {
        m_ledColor = color;
        m_ledMode = mode;
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

    public void solid() {
        for (var i = 0; i < m_ledBuffer.getLength(); i++) {
            // Sets the specified LED to the RGB values for red
            m_ledBuffer.setRGB(i, (int) m_ledColor.red, (int) m_ledColor.green, (int) m_ledColor.blue);
        }

        m_led.setData(m_ledBuffer);
    }

    public void pulse() {
        for (var i = 0; i < m_ledBuffer.getLength(); i++) {
            // Sets the specified LED to the RGB values for blue
            m_ledBuffer.setRGB(i, redPulseBrightness, greenPulseBrightness, bluePulseBrightness);
        }

        // increase brightness
        redPulseBrightness += 5 * (m_ledColor.red / 255);
        greenPulseBrightness += 5 * (m_ledColor.green / 255);
        bluePulseBrightness += 5 * (m_ledColor.blue / 255);

        // Check bounds
        redPulseBrightness %= (int) m_ledColor.red;
        greenPulseBrightness %= (int) m_ledColor.green;
        bluePulseBrightness %= (int) m_ledColor.blue;

        m_led.setData(m_ledBuffer);
    }

    public void streak() {
        for (var i = 0; i < m_ledBuffer.getLength(); i++) {
            // Sets the specified LED to the RGB values for blue
            m_ledBuffer.setRGB(i, (int) m_ledColor.red, (int) m_ledColor.green, (int) m_ledColor.blue);
        }

        for (int i = 0; i < 4; i++) {
            m_ledBuffer.setRGB((streakLED + i) % m_ledBuffer.getLength(), 0, 0, 0);
        }

        // increase brightness
        if (numLoops % 3 == 0) {
            streakLED += 1;
            // Check bounds
            streakLED %= m_ledBuffer.getLength();
        }

        m_led.setData(m_ledBuffer);

        numLoops += 1;

    }

    public void clearAll() {
        for (var i = 0; i < m_ledBuffer.getLength(); i++) {
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
            m_ledBuffer.setRGB(i, 0, 0, 0);
        }
        m_led.setData(m_ledBuffer);

    }

    public void setRange(int startIdx, int endIdx) {
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
            m_ledBuffer.setRGB(i, (int) m_ledColor.red, (int) m_ledColor.green, (int) m_ledColor.blue);
        }
        m_led.setData(m_ledBuffer);

    }
}
