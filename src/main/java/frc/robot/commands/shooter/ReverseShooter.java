package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterSubsystem;

public class ReverseShooter extends CommandBase {
    private final ShooterSubsystem m_shooterSubsystem;

    public ReverseShooter(ShooterSubsystem shooterSubsystem) {
        m_shooterSubsystem = shooterSubsystem;

        addRequirements(m_shooterSubsystem);
    }

    @Override
    public void initialize() {
        m_shooterSubsystem.reverseShooter();
        m_shooterSubsystem.reverseHelper();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
