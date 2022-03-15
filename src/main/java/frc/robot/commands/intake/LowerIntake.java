package frc.robot.commands.intake;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;

public class LowerIntake extends CommandBase {
    private final IntakeSubsystem m_intakeSubsystem;
    private final Timer m_timer=new Timer();
    public LowerIntake(IntakeSubsystem intakeSubsystem) {
        m_intakeSubsystem = intakeSubsystem;
        addRequirements(m_intakeSubsystem);
    }

    @Override
    public void initialize() {
        if (!m_intakeSubsystem.isLowered()) {
            m_intakeSubsystem.reverseWinch();
        }
        m_timer.reset();
        m_timer.start();
    }

    @Override
    public boolean isFinished() {
        return (m_intakeSubsystem.isLowered() || m_timer.hasElapsed(2));
    }

    @Override
    public void end(boolean interrupted) {
        m_intakeSubsystem.stopWinch();
        m_timer.stop();
    }
}
