package frc.robot.commands.drive;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class ArcadeDrive extends CommandBase {
    private DriveSubsystem m_driveSubsystem;
    private DoubleSupplier m_fwd;
    private DoubleSupplier m_rot;

    /**
     * Tank drive that constantly sets the drive motors with the left and right
     * doublesuppliers
     * 
     * @param driveSubsystem the drivesubsystem to be controlled
     * @param left           doulbe supplier for the left motor (-1 to 1)
     * @param right          doulbe supplier for the right motor (-1 to 1)
     */
    public ArcadeDrive(DriveSubsystem driveSubsystem, DoubleSupplier fwd, DoubleSupplier rot) {
        m_driveSubsystem = driveSubsystem;
        m_fwd = fwd;
        m_rot = rot;
        addRequirements(m_driveSubsystem);
    }

    @Override
    public void execute() {
        m_driveSubsystem.arcadeDrive(m_fwd.getAsDouble(), -0.75*m_rot.getAsDouble());
    }
}
