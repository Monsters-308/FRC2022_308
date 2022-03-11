package frc.robot.commands.drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class AutoAim extends CommandBase {
    private final DriveSubsystem m_driveSubsystem;
    private final VisionSubsystem m_visionSubsystem;
    private final double m_maxSpeed;
    private boolean m_complete;

    public AutoAim(double maxSpeed, DriveSubsystem driveSubsystem,
            VisionSubsystem visionSubsystem) {
        m_driveSubsystem = driveSubsystem;
        m_visionSubsystem = visionSubsystem;
        m_maxSpeed = maxSpeed;

        addRequirements(m_driveSubsystem, m_visionSubsystem);
    }

    @Override
    public void initialize() {
        m_complete = false;
    }

    @Override
    public void execute() {
        if (!m_visionSubsystem.hasTarget()) {
            m_complete = true;
            return;
        }

        double yaw = m_visionSubsystem.getYaw();
        double direction = yaw < 0 ? -1 : 1;

        if (Math.abs(yaw) > 5) {
            m_driveSubsystem.tankDrive(direction * m_maxSpeed, -direction * m_maxSpeed);
        } else {
            m_complete = true;
        }
    }

    @Override
    public boolean isFinished() {
        return m_complete;
    }

    @Override
    public void end(boolean interrupted) {
        m_driveSubsystem.tankDrive(0, 0);
    }
}
