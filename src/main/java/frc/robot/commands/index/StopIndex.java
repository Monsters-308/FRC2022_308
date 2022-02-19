package frc.robot.commands.index;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IndexSubsystem;

public class StopIndex extends CommandBase {
    private IndexSubsystem m_indexSubsystem;

    public StopIndex(IndexSubsystem indexSubsystem) {
        m_indexSubsystem = indexSubsystem;
        addRequirements(m_indexSubsystem);
    }

    @Override
    public void initialize() {
        m_indexSubsystem.turnOFF();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
