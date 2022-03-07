package frc.robot.commands.hang;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.HangSubsystem;

public class RaiseHang extends CommandBase {
    private final HangSubsystem m_hangSubsystem;

    public RaiseHang(HangSubsystem hangSubsystem) {
        m_hangSubsystem = hangSubsystem;

        addRequirements(m_hangSubsystem);
    }

    @Override
    public void initialize() {
        m_hangSubsystem.runHang();
    }

    @Override
    public boolean isFinished() {
        if (m_hangSubsystem.isLeftRaised() || m_hangSubsystem.isRightRaised()) {
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
