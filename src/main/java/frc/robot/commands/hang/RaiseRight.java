package frc.robot.commands.hang;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.HangSubsystem;

public class RaiseRight extends CommandBase {
    private final HangSubsystem m_hangSubsystem;

    public RaiseRight(HangSubsystem hangSubsystem) {
        m_hangSubsystem = hangSubsystem;

        addRequirements(m_hangSubsystem);
    }

    @Override
    public void initialize() {
        //m_hangSubsystem.runLeftHang();
        m_hangSubsystem.runRightHang();
    }

    @Override
    public void execute() {
        // if (m_hangSubsystem.isLeftRaised()) {
        //     m_hangSubsystem.stopLeftHang();
        // }
        if (m_hangSubsystem.isRightRaised()) {
            m_hangSubsystem.stopRightHang();
        }
    }

    @Override
    public boolean isFinished() {
        if (m_hangSubsystem.isRightRaised()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void end(boolean interrupted) {
        //m_hangSubsystem.stopLeftHang();
        m_hangSubsystem.stopRightHang();
    }
}
