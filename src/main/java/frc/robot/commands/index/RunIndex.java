package frc.robot.commands.index;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IndexSubsystem;

public class RunIndex extends CommandBase {
    private final IndexSubsystem m_indexSubsystem;

    public RunIndex(IndexSubsystem indexSubsystem) {
        m_indexSubsystem = indexSubsystem;
        addRequirements(m_indexSubsystem);
    }

    @Override
    public void initialize() {
        m_indexSubsystem.turnON();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
