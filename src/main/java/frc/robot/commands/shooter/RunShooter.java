package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterSubsystem;

public class RunShooter extends CommandBase {
    private final ShooterSubsystem m_shooterSubsystem;

    public RunShooter(ShooterSubsystem shooterSubsystem) {
        m_shooterSubsystem = shooterSubsystem;
    }

    @Override
    public void initialize() {
        m_shooterSubsystem.runHelper();
        m_shooterSubsystem.runShooter();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
