package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;

public class StopIntake extends CommandBase {
    private IntakeSubsystem m_intakeSubsystem;

    public StopIntake(IntakeSubsystem intakeSubsystem) {
        m_intakeSubsystem = intakeSubsystem;
        addRequirements(m_intakeSubsystem);
    }

    @Override
    public void initialize() {
        m_intakeSubsystem.stopIntake();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
