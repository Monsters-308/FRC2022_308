package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;

public class AutoIntake extends CommandBase {
    // The subsystem the command runs on
    private final IntakeSubsystem m_intakeSubsystem;
    /**
     * currently doesnt do anything except run the intake when called
     * @param subsystem The intake subsystem
     */
    public AutoIntake(IntakeSubsystem subsystem) {
        m_intakeSubsystem = subsystem;

        addRequirements(m_intakeSubsystem);
    }

    @Override
    public void initialize() {
        m_intakeSubsystem.runIntake();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
