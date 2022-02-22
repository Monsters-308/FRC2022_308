package frc.robot.commands.index;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

public class AutoIndex extends CommandBase {
    public enum BallStage {
        EMPTY,
        BALL_PRESENT,
        BALL_INDEXED,
        FULL
    }

    private final IndexSubsystem m_indexSubsystem;
    private final IntakeSubsystem m_intakeSubsystem;
    public BallStage m_ballStage = BallStage.EMPTY;
    public boolean m_complete = false;

    public AutoIndex(IndexSubsystem indexSubsystem, IntakeSubsystem intakeSubsystem) {
        m_indexSubsystem = indexSubsystem;
        m_intakeSubsystem = intakeSubsystem;

        addRequirements(m_indexSubsystem, m_intakeSubsystem);
    }

    @Override
    public void initialize() {
        if (m_indexSubsystem.isUpperBallPresent() && m_indexSubsystem.isLowerBallPresent()) {
            m_ballStage = BallStage.FULL;
            m_indexSubsystem.turnOFF();
            m_intakeSubsystem.stopIntake();
        } else if (m_indexSubsystem.isUpperBallPresent() && !m_indexSubsystem.isLowerBallPresent()) {
            m_ballStage = BallStage.BALL_INDEXED;
        } else if (!m_indexSubsystem.isUpperBallPresent() && m_indexSubsystem.isLowerBallPresent()) {
            m_ballStage = BallStage.BALL_PRESENT;
        } else {
            m_ballStage = BallStage.EMPTY;
        }
    }

    @Override
    public void execute() {
        switch (m_ballStage) {
            case EMPTY:
                if (m_indexSubsystem.isLowerBallPresent()) {
                    m_ballStage = BallStage.BALL_PRESENT;
                }
                m_intakeSubsystem.forwardIntake();
                m_indexSubsystem.turnON();
                break;
            case BALL_PRESENT:
                if (m_indexSubsystem.isUpperBallPresent()) {
                    m_ballStage = BallStage.BALL_INDEXED;
                } else {
                    m_indexSubsystem.turnON();
                    m_intakeSubsystem.forwardIntake();
                }
                break;
            case BALL_INDEXED:
                if (m_indexSubsystem.isLowerBallPresent() && m_indexSubsystem.isUpperBallPresent()) {
                    m_ballStage = BallStage.FULL;
                    m_indexSubsystem.turnOFF();
                    m_intakeSubsystem.stopIntake();
                } else {
                    m_intakeSubsystem.forwardIntake();
                }
                break;
            case FULL:
                m_intakeSubsystem.stopIntake();
                m_indexSubsystem.turnOFF();
                m_complete = true;
                break;

        }
    }

    @Override
    public boolean isFinished() {
        return m_complete;
    }
}
