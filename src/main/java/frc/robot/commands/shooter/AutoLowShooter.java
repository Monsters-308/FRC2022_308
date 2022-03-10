package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.LEDSubsystem.LEDState;

public class AutoLowShooter extends CommandBase {
    public enum ShooterStage {
        INDEXING,
        BALL_READY,
        RAMPING_SHOOTER,
        SHOOTING,
        EMPTY
    }

    private final IndexSubsystem m_indexSubsystem;
    private final ShooterSubsystem m_shooterSubsystem;
    private final LEDSubsystem m_ledSubsystem;
    private boolean m_complete = false;
    private Timer m_timer = new Timer();
    private ShooterStage m_shooterStage = ShooterStage.BALL_READY;

    /**
     * A command that when pressed, will automatically ramp up and feed the shooter
     * with balls.
     * 
     * @param indexSubsystem   pass in the indexSubsystem
     * @param shooterSubsystem pass in the shooterSubsystem
     * @param ledSubsystem     pass in the ledSubsystem
     */
    public AutoLowShooter(IndexSubsystem indexSubsystem, ShooterSubsystem shooterSubsystem, LEDSubsystem ledSubsystem) {
        m_indexSubsystem = indexSubsystem;
        m_shooterSubsystem = shooterSubsystem;
        m_ledSubsystem = ledSubsystem;
        addRequirements(m_indexSubsystem, m_shooterSubsystem, m_ledSubsystem);
    }

    @Override
    public void initialize() {
        if (m_indexSubsystem.isUpperBallPresent()) {
            m_shooterStage = ShooterStage.BALL_READY;
        } else {
            m_shooterStage = ShooterStage.INDEXING;
        }
        m_timer.start();
        m_timer.reset();
    }

    @Override
    public void execute() {
        switch (m_shooterStage) {
            case INDEXING:
                if (m_indexSubsystem.isUpperBallPresent()) {
                    m_indexSubsystem.stopIndex();
                    m_shooterStage = ShooterStage.BALL_READY;
                } else if (m_timer.hasElapsed(ShooterConstants.kMaxIndexTimeSec)) {
                    m_shooterStage = ShooterStage.EMPTY;
                } else {
                    m_indexSubsystem.runIndex();
                }
                break;
            case BALL_READY:
                m_indexSubsystem.stopIndex();
                m_shooterSubsystem.runShooterSlow();
                // m_shooterSubsystem.speedControlShooter(ShooterConstants.kShooterSpeedRPM);
                m_shooterStage = ShooterStage.RAMPING_SHOOTER;
                break;
            case RAMPING_SHOOTER:
                m_ledSubsystem.setLEDState(LEDState.BLUE_STREAK);
                if (m_timer.hasElapsed(ShooterConstants.kRampTimeSec)) {
                    m_shooterStage = ShooterStage.SHOOTING;
                    m_timer.reset();
                }
                // if(m_shooterSubsystem.getShooterVelocity() >
                // ShooterConstants.kShooterSpeedRPM ||
                // m_timer.hasElapsed(ShooterConstants.kMaxRampTime)) {
                // m_shooterStage = ShooterStage.SHOOTING;
                // m_timer.reset();
                // }
                break;
            case SHOOTING:
                m_ledSubsystem.setLEDState(LEDState.GREEN);
                if (m_timer.hasElapsed(ShooterConstants.kMaxReleaseTimeSec)) {
                    m_indexSubsystem.stopIndex();
                    m_shooterSubsystem.stopHelper();
                    m_shooterStage = ShooterStage.INDEXING;
                    m_timer.reset();
                } else if (m_indexSubsystem.isUpperBallPresent()) {
                    m_indexSubsystem.runIndex();
                    m_shooterSubsystem.runHelper();
                    m_timer.reset();
                }
                break;
            case EMPTY:
                m_indexSubsystem.stopIndex();
                m_shooterSubsystem.stopHelper();
                m_shooterSubsystem.stopShooter();
                m_complete = true;
                break;
        }
    }

    @Override
    public boolean isFinished() {
        return m_complete;
    }

    @Override
    public void end(boolean interrupted) {
        m_indexSubsystem.stopIndex();
        m_shooterSubsystem.stopHelper();
        m_shooterSubsystem.stopShooter();
        m_timer.stop();
        m_complete = false;
    }
}
