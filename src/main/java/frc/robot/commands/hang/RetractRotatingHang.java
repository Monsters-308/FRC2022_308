package frc.robot.commands.hang;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.RotatingHangSubsystem;

public class RetractRotatingHang extends CommandBase {
    private final RotatingHangSubsystem m_hangSubsystem;

    public RetractRotatingHang(RotatingHangSubsystem hangSubsystem) {
        m_hangSubsystem = hangSubsystem;
        addRequirements(m_hangSubsystem);
    }

    @Override
    public void initialize() {
        m_hangSubsystem.setDeployed(false);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
