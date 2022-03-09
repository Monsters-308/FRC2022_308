package frc.robot.commands.hang;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.HangSubsystem;

public class StopHang extends CommandBase {
    private final HangSubsystem m_hangSubsystem;

    public StopHang(HangSubsystem hangSubsystem) {
        m_hangSubsystem = hangSubsystem;

        addRequirements(m_hangSubsystem);
    }

    @Override
    public void initialize() {
        m_hangSubsystem.stopLeftHang();
        m_hangSubsystem.stopRightHang();
    }

    @Override
    public boolean isFinished() {
        return true;
    }

}
