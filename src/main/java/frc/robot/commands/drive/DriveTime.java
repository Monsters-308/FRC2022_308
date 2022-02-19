package frc.robot.commands.drive;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

// Drives the robot at the specified speed for a particular amount of time
public class DriveTime extends CommandBase {
    private final int m_time;
    private final double m_speed;
    private final DriveSubsystem m_driveSubsystem;
    private Timer m_timer = new Timer();

    public DriveTime(int seconds, double speed, DriveSubsystem driveSubsystem) {
        m_time = seconds;
        m_speed = speed;
        m_driveSubsystem = driveSubsystem;
        addRequirements(driveSubsystem);
    }

    @Override
    public void initialize() {
        m_timer.start();
    }

    @Override
    public void execute() {
        m_driveSubsystem.tankDrive(m_speed, m_speed);
    }

    @Override
    public boolean isFinished() {
        return m_timer.hasElapsed(m_time);
    }

    @Override
    public void end(boolean interrupt) {
        m_timer.stop();
        m_driveSubsystem.tankDrive(0, 0);
    }
}
