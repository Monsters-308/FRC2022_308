package frc.robot.commands.vision;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.VisionSubsystem;

public class DriverMode extends CommandBase {
    private final VisionSubsystem m_visionSubsystem;
    private boolean driveMode;

    public DriverMode(VisionSubsystem subsystem, boolean driveMode) {
        m_visionSubsystem = subsystem;
        this.driveMode=driveMode;
        addRequirements(m_visionSubsystem);
    }

    @Override
    public void initialize() {
        if(driveMode){
            m_visionSubsystem.enableDriverMode();
        }
        else{
            m_visionSubsystem.disableDriverMode();
        }
        
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}