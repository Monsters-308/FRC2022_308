package frc.robot.commands.hang;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.HangSubsystem;

public class LowerHang extends CommandBase {
    private final HangSubsystem m_hangSubsystem;

    public LowerHang(HangSubsystem hangSubsystem) {
        m_hangSubsystem = hangSubsystem;

        addRequirements(m_hangSubsystem);
    }

    @Override
    public void initialize() {
        m_hangSubsystem.reverseHang();
    }

    @Override
    public boolean isFinished() {
        if (m_hangSubsystem.isLeftLowered() || m_hangSubsystem.isRightLowered()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void end(boolean interrupted) {
        m_hangSubsystem.stopHang();
    }
}
