package frc.robot.commands.index;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.IntakeConstants;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

public class AutoIndex extends CommandBase {
    private final IndexSubsystem m_indexSubsystem;
    private final IntakeSubsystem m_intakeSubsystem;
    public boolean m_complete = false;
    
    public enum IndexStage {
        NONE, INTAKEBALL, LOWBALL, FULL
    }
    IndexStage m_indexStage=IndexStage.NONE;
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
        // if (!(m_indexSubsystem.isUpperBallPresent() && m_indexSubsystem.isLowerBallPresent())) {
        //     m_indexSubsystem.runIndex();
        //     m_intakeSubsystem.runIntake();
        // }
        if(m_indexSubsystem.isUpperBallPresent()){
            m_indexStage=IndexStage.FULL;
        }
        else if(m_indexSubsystem.isLowerBallPresent()){
            m_indexStage=IndexStage.LOWBALL;
        }
        else{
            m_indexStage=IndexStage.NONE;
        }
    }

    @Override
    public void end(boolean interrupted) {
        
        m_indexSubsystem.stopIndex(); 
        m_intakeSubsystem.stopIntake();
    }

    @Override
    public boolean isFinished() {
        switch(m_indexStage){
            case NONE:
                m_indexSubsystem.runIndex();
                m_intakeSubsystem.runIntake();
                if(m_indexSubsystem.isUpperBallPresent()){
                    m_indexStage=IndexStage.FULL;
                }
                else if(m_indexSubsystem.isLowerBallPresent()){
                    m_indexStage=IndexStage.LOWBALL;
                }
                else if(m_intakeSubsystem.isBallPresent()){
                    m_indexStage=IndexStage.INTAKEBALL;
                }

                break;
            case INTAKEBALL:
                m_indexSubsystem.runIndex();
                m_intakeSubsystem.runIntake();
                if (m_indexSubsystem.isUpperBallPresent()) {
                    m_indexStage = IndexStage.FULL;
                }
                else if(m_intakeSubsystem.isBallPresent()){
                    m_indexStage=IndexStage.INTAKEBALL;
                }
                else if(m_indexSubsystem.isLowerBallPresent()){
                    m_indexStage=IndexStage.LOWBALL;
                }
            case LOWBALL:
                m_intakeSubsystem.runIntake();
                m_indexSubsystem.stopIndex();
                if (m_indexSubsystem.isUpperBallPresent()) {
                    m_indexStage = IndexStage.FULL;
                } else if (m_intakeSubsystem.isBallPresent()) {
                    m_indexStage = IndexStage.INTAKEBALL;
                } else if (!m_indexSubsystem.isLowerBallPresent()) {
                    m_indexStage = IndexStage.NONE;
                }
                break;
            case FULL:
                m_indexSubsystem.stopIndex();
                m_intakeSubsystem.stopIntake();
                return true;
        }
        return false; //by default return false

        // if (m_indexSubsystem.isUpperBallPresent() && m_indexSubsystem.isLowerBallPresent()) { //old code
        //     return true;
        // } else {
        //     return false;
        // }
    }
}
