package frc.robot.commands.hang;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.RotatingHangSubsystem;

public class ToggleRotatingHang extends CommandBase {
    private final RotatingHangSubsystem m_hangSubsystem;

    public ToggleRotatingHang(RotatingHangSubsystem hangSubsystem) {
        m_hangSubsystem = hangSubsystem;
        addRequirements(m_hangSubsystem);
    }

    @Override
    public void initialize() {
        m_hangSubsystem.togglePiston();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
