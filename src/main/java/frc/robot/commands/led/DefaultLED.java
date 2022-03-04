package frc.robot.commands.led;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.LEDSubsystem.LEDState;

public class DefaultLED extends CommandBase {
    private LEDSubsystem m_ledSubsystem;

    public DefaultLED(LEDSubsystem ledSubsystem) {
        m_ledSubsystem = ledSubsystem;

        addRequirements(m_ledSubsystem);
    }

    @Override
    public void initialize() {
        switch (DriverStation.getAlliance()) {
            case Red:
                m_ledSubsystem.setLEDState(LEDState.RED);
            case Blue:
                m_ledSubsystem.setLEDState(LEDState.BLUE);
            case Invalid:
                m_ledSubsystem.setLEDState(LEDState.RED_BLINK);
        }
    }
}
