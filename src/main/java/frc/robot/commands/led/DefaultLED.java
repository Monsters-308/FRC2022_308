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
        m_ledSubsystem.setLEDState(LEDState.RED);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
