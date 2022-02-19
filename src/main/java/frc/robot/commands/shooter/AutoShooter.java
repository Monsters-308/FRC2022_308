package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class AutoShooter extends CommandBase {
    public enum ShooterStage {
        INDEXING,
        BALL_READY,
        RAMPING_SHOOTER,
        SHOOTING,
        EMPTY
    }

    public final IndexSubsystem m_indexSubsystem;
    public final ShooterSubsystem m_shooterSubsystem;
    public boolean m_complete = false;
    public Timer m_timer = new Timer();
    public ShooterStage m_shooterStage = ShooterStage.BALL_READY;

    public AutoShooter(IndexSubsystem indexSubsystem, ShooterSubsystem shooterSubsystem) {
        m_indexSubsystem = indexSubsystem;
        m_shooterSubsystem = shooterSubsystem;

        addRequirements(m_indexSubsystem, m_shooterSubsystem);
    }

    @Override
    public void initialize() {
        if (m_indexSubsystem.getUpperBall()) {
            m_shooterStage = ShooterStage.BALL_READY;
        } else {
            m_shooterStage = ShooterStage.INDEXING;
        }
        m_timer.start();
    }

    @Override
    public void execute() {
        switch (m_shooterStage) {
            case INDEXING:
                if (m_indexSubsystem.getUpperBall()) {
                    m_indexSubsystem.turnOFF();
                    m_shooterStage = ShooterStage.BALL_READY;
                } else if (m_timer.hasElapsed(ShooterConstants.kMaxIndexTimeSec)) {
                    m_shooterStage = ShooterStage.EMPTY;
                } else {
                    m_indexSubsystem.turnON();
                }
                break;
            case BALL_READY:
                m_indexSubsystem.turnOFF();
                m_shooterSubsystem.runShooter();
                m_shooterStage = ShooterStage.RAMPING_SHOOTER;
                m_timer.reset();
                break;
            case RAMPING_SHOOTER:
                if (m_timer.hasElapsed(ShooterConstants.kRampTimeSec)) {
                    m_shooterStage = ShooterStage.SHOOTING;
                    m_timer.reset();
                }
                break;
            case SHOOTING:
                if (!m_indexSubsystem.getUpperBall() && m_timer.hasElapsed(ShooterConstants.kMaxReleaseTimeSec)) {
                    m_indexSubsystem.turnOFF();
                    m_shooterSubsystem.stopHelper();
                    m_shooterStage = ShooterStage.INDEXING;
                    m_timer.reset();
                } else if (m_indexSubsystem.getUpperBall()) {
                    m_indexSubsystem.turnON();
                    m_shooterSubsystem.runHelper();
                    m_timer.reset();
                }
                break;
            case EMPTY:
                m_indexSubsystem.turnOFF();
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
}