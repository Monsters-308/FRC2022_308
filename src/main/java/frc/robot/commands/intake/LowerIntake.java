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
        m_intakeSubsystem.setLowered(true);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
