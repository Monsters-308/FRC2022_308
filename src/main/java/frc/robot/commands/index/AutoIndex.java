package frc.robot.commands.index;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

public class AutoIndex extends CommandBase {
    private final IndexSubsystem m_indexSubsystem;
    private final IntakeSubsystem m_intakeSubsystem;
    public boolean m_complete = false;

    /**
     * when initialized, this will run the intexer and intake. when both index sensors return true, it will stop intake/index.
     * @param indexSubsystem the indexsubsystem
     * @param intakeSubsystem the intakesubsystem
     */
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
        
        m_indexSubsystem.stopIndex(); //these lines of code could potentially cause issues with motor jittering. it may be benificial to remove them entirely
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
