package frc.robot.commands.vision;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.VisionSubsystem;

public class LimeLight extends CommandBase {
    private final VisionSubsystem m_visionSubsystem;
    private boolean limeMode;

    public LimeLight(VisionSubsystem subsystem, boolean limeMode) {
        m_visionSubsystem = subsystem;
        this.limeMode=limeMode;
        addRequirements(m_visionSubsystem);
    }

    @Override
    public void initialize() {
        if (limeMode) {
            m_visionSubsystem.ledOn();
        } else {
            m_visionSubsystem.ledOff();
        }

    }
    @Override
    public boolean isFinished(){
        return true;
    }
}
