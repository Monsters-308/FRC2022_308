package frc.robot.commands.drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class DriveDistance extends CommandBase {
    private final double m_distance;
    private final double m_speed;
    private final DriveSubsystem m_driveSubsystem;
    public boolean m_complete = false;

    public DriveDistance(double inches, double speed, DriveSubsystem driveSubsystem) {
        m_distance = inches;
        m_speed = speed;
        m_driveSubsystem = driveSubsystem;
        addRequirements(m_driveSubsystem);
    }

    @Override
    public void initialize() {
        m_driveSubsystem.resetEncoders();
    }

    @Override
    public void execute() {
        if (m_driveSubsystem.getAverageEncoderDistance() >= m_distance) {
            m_driveSubsystem.tankDrive(0, 0);
            m_complete = true;
        } else {
            m_driveSubsystem.tankDrive(m_speed, m_speed);
        }
    }

    @Override
    public boolean isFinished() {
        return m_complete;
    }
}
