package frc.robot.commands.index;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

public class AutoIndex extends CommandBase {
    private final IndexSubsystem m_indexSubsystem;
    private final IntakeSubsystem m_intakeSubsystem;
    public boolean m_complete = false;

    public AutoIndex(IndexSubsystem indexSubsystem, IntakeSubsystem intakeSubsystem) {
        m_indexSubsystem = indexSubsystem;
        m_intakeSubsystem = intakeSubsystem;

        addRequirements(m_indexSubsystem, m_intakeSubsystem);
    }

    @Override
    public void initialize() {
        if (!(m_indexSubsystem.isUpperBallPresent() && m_indexSubsystem.isLowerBallPresent())) {
            m_indexSubsystem.runIndex();
            m_intakeSubsystem.runIntake();
        }
    }

    @Override
    public void end(boolean interrupted) {
        m_indexSubsystem.stopIndex();
        m_intakeSubsystem.stopIntake();
    }

    @Override
    public boolean isFinished() {
        if (m_indexSubsystem.isUpperBallPresent() && m_indexSubsystem.isLowerBallPresent()) {
            return true;
        } else {
            return false;
        }
    }
}
