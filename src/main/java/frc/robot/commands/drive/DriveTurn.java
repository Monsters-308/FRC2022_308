package frc.robot.commands.drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class DriveTurn extends CommandBase {
    private final DriveSubsystem m_driveSubsystem;
    private final double m_targetHeading;
    private final double m_speed;
    private final double m_maxHeadingError = 0.1;

    public DriveTurn(double heading, double speed, DriveSubsystem driveSubsystem) {
        m_targetHeading = heading;
        m_speed = speed;
        m_driveSubsystem = driveSubsystem;
        addRequirements(driveSubsystem);
    }

    @Override
    public void initialize() {
        m_driveSubsystem.resetGyro();
    }

    @Override
    public void execute() {
        m_driveSubsystem.arcadeDrive(0, m_speed);
    }

    @Override
    public void end(boolean interrupted) {
        m_driveSubsystem.arcadeDrive(0, 0);
    }

    @Override
    public boolean isFinished() {
        if (Math.abs(m_driveSubsystem.getGyroHeading() - m_targetHeading) < m_maxHeadingError) {
            return true;
        } else {
            return false;
        }
    }
}
