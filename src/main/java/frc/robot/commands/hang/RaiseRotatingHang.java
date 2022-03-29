package frc.robot.commands.hang;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.RotatingHangSubsystem;

public class RaiseRotatingHang extends CommandBase {
    private final RotatingHangSubsystem m_hangSubsystem;

    public RaiseRotatingHang(RotatingHangSubsystem hangSubsystem) {
        m_hangSubsystem = hangSubsystem;
        addRequirements(m_hangSubsystem);
    }

    @Override
    public void initialize() {
        m_hangSubsystem.runLeftHang();
        m_hangSubsystem.runRightHang();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
    
}
