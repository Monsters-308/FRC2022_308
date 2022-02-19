package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;

public class RunIntake extends CommandBase {
    // The subsystem the command runs on
    private final IntakeSubsystem m_intakeSubsystem;

  public RunIntake(IntakeSubsystem subsystem) {
    m_intakeSubsystem = subsystem;
    addRequirements(m_intakeSubsystem);
  }

    @Override
    public void initialize() {
        m_intakeSubsystem.forwardIntake();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
