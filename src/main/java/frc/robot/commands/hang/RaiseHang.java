package frc.robot.commands.hang;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.HangSubsystem;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.LEDSubsystem.LEDState;

public class RaiseHang extends CommandBase {
    private final HangSubsystem m_hangSubsystem;
    private final LEDSubsystem m_ledSubsystem;

    public RaiseHang(HangSubsystem hangSubsystem, LEDSubsystem ledSubsystem) {
        m_hangSubsystem = hangSubsystem;
        m_ledSubsystem = ledSubsystem;

        addRequirements(m_hangSubsystem, m_ledSubsystem);
    }

    @Override
    public void initialize() {
        m_hangSubsystem.runLeftHang();
        m_hangSubsystem.runRightHang();
    }

    @Override
    public void execute() {
        if (m_hangSubsystem.isLeftRaised()) {
            m_hangSubsystem.stopLeftHang();
        }
        if (m_hangSubsystem.isRightRaised()) {
            m_hangSubsystem.stopRightHang();
        }
    }

    @Override
    public boolean isFinished() {
        if (m_hangSubsystem.isLeftRaised() && m_hangSubsystem.isRightRaised()) {
            m_ledSubsystem.setLEDState(new Color(0, 255, 0), LEDState.SOLID);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void end(boolean interrupted) {
        m_hangSubsystem.stopLeftHang();
        m_hangSubsystem.stopRightHang();
    }
}
