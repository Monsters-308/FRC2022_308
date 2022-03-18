package frc.robot.commands.hang;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.HangSubsystem;

public class LowerLeft extends CommandBase {
    private final HangSubsystem m_hangSubsystem;

    public LowerLeft(HangSubsystem hangSubsystem) {
        m_hangSubsystem = hangSubsystem;
        addRequirements(m_hangSubsystem);
    }

    @Override
    public void initialize() {
        m_hangSubsystem.reverseLeftHang();
        //m_hangSubsystem.reverseRightHang();
    }

    @Override
    public void execute() {
        if (m_hangSubsystem.isLeftLowered()) {
            m_hangSubsystem.stopLeftHang();
        }
        // if (m_hangSubsystem.isRightLowered()) {
        //     m_hangSubsystem.stopRightHang();
        // }
    }

    @Override
    public boolean isFinished() {
        if (m_hangSubsystem.isLeftLowered()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void end(boolean interrupted) {
        m_hangSubsystem.stopLeftHang();
        //m_hangSubsystem.stopRightHang();
    }
}
