package frc.robot.commands.subsystems.drive;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class DefaultDrive extends CommandBase {
    private DriveSubsystem m_driveSubsystem;
    private DoubleSupplier m_left;
    private DoubleSupplier m_right;

    public DefaultDrive(DriveSubsystem driveSubsystem, DoubleSupplier left, DoubleSupplier right) {
        m_driveSubsystem = driveSubsystem;
        m_left = left;
        m_right = right;
        addRequirements(m_driveSubsystem);
    }

    @Override
    public void execute() {
        m_driveSubsystem.tankDrive(m_left.getAsDouble(), m_right.getAsDouble());
    }
}
