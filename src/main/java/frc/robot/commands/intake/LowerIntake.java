package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;

public class LowerIntake extends CommandBase {
    private final IntakeSubsystem m_intakeSubsystem;

    public LowerIntake(IntakeSubsystem intakeSubsystem) {
        m_intakeSubsystem = intakeSubsystem;

        addRequirements(m_intakeSubsystem);
    }

    @Override
    public void initialize() {
        m_intakeSubsystem.reverseWinch();
    }

    @Override
    public boolean isFinished() {
        return m_intakeSubsystem.isRaised();
    }

    @Override
    public void end(boolean interrupted) {
        m_intakeSubsystem.stopWinch();
    }
}
