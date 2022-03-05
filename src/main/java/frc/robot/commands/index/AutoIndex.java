package frc.robot.commands.index;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.LEDSubsystem.LEDState;

public class AutoIndex extends CommandBase {
    public enum IndexStage {
        NONE,
        INTAKEBALL,
        LOWBALL,
        FULL
    }

    private final IndexSubsystem m_indexSubsystem;
    private final IntakeSubsystem m_intakeSubsystem;
    private final LEDSubsystem m_ledSubsystem;
    public boolean m_complete = false;

    IndexStage m_indexStage = IndexStage.NONE;

    /**
     * when initialized, this will run the intexer and intake. when both index
     * sensors return true, it will stop intake/index.
     * 
     * @param indexSubsystem  the Index subsystem
     * @param intakeSubsystem the Intake subsystem
     * @param ledSubsystem the LED subsystem
     */
    public AutoIndex(IndexSubsystem indexSubsystem, IntakeSubsystem intakeSubsystem, LEDSubsystem ledSubsystem) {
        m_indexSubsystem = indexSubsystem;
        m_intakeSubsystem = intakeSubsystem;
        m_ledSubsystem = ledSubsystem;
        addRequirements(m_indexSubsystem, m_intakeSubsystem, m_ledSubsystem);
    }

    @Override
    public void initialize() {
        // if (!(m_indexSubsystem.isUpperBallPresent() &&
        // m_indexSubsystem.isLowerBallPresent())) {
        // m_indexSubsystem.runIndex();
        // m_intakeSubsystem.runIntake();
        // }
        m_complete = false;
        if (m_indexSubsystem.isUpperBallPresent()) {
            m_indexStage = IndexStage.FULL;
        } else if (m_indexSubsystem.isLowerBallPresent()) {
            m_indexStage = IndexStage.LOWBALL;
        } else {
            m_indexStage = IndexStage.NONE;
        }
    }

    @Override
    public void execute() {
        SmartDashboard.putString("AutoIndexState", m_indexStage.toString());
        switch (m_indexStage) {
            case NONE:
                m_indexSubsystem.runIndex();
                m_intakeSubsystem.runIntake();
                if (m_indexSubsystem.isUpperBallPresent()) {
                    m_indexStage = IndexStage.FULL;
                } else if (m_indexSubsystem.isLowerBallPresent()) {
                    m_indexStage = IndexStage.LOWBALL;
                }
                break;
            case LOWBALL:
                m_ledSubsystem.setLEDState(LEDState.PURPLE);
                m_intakeSubsystem.runIntake();
                m_indexSubsystem.stopIndex();
                if (m_indexSubsystem.isUpperBallPresent()) {
                    m_indexStage = IndexStage.FULL;
                } else if (m_indexSubsystem.isIntakeBallPresent()) {
                    m_indexStage = IndexStage.INTAKEBALL;
                } else if (!m_indexSubsystem.isLowerBallPresent()) {
                    m_indexStage = IndexStage.NONE;
                }
                break;
            case INTAKEBALL:
                m_indexSubsystem.runIndex();
                m_intakeSubsystem.runIntake();
                if (m_indexSubsystem.isUpperBallPresent()) {
                    m_indexStage = IndexStage.FULL;
                }
                break;
            case FULL:
                m_ledSubsystem.setLEDState(LEDState.GREEN);
                m_complete = true;
        }
    }

    @Override
    public boolean isFinished() {
        return m_complete;
    }

    @Override
    public void end(boolean interrupted) {

        m_indexSubsystem.stopIndex();
        m_intakeSubsystem.stopIntake();
    }
}
