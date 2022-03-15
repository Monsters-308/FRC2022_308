package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class VisionSubsystem extends SubsystemBase {
    private final PowerDistribution m_powerDistribution = new PowerDistribution(16, ModuleType.kRev);
    private final NetworkTable m_visionTable;
    private final NetworkTableEntry m_visionYaw;
    private final NetworkTableEntry m_hasTarget;
    private final NetworkTableEntry m_driverMode;

    public VisionSubsystem() {
        m_visionTable = NetworkTableInstance.getDefault().getTable("photonvision").getSubTable("ShooterCam");
        m_visionYaw = m_visionTable.getEntry("targetYaw");
        m_hasTarget = m_visionTable.getEntry("hasTarget");
        m_driverMode = m_visionTable.getEntry("driverMode");
    }

    public void ledOn() {
        m_powerDistribution.setSwitchableChannel(true);
    }

    public void ledOff() {
        m_powerDistribution.setSwitchableChannel(false);
    }

    public boolean hasTarget() {
        return m_hasTarget.getBoolean(false);
    }

    public double getYaw() {
        return m_visionYaw.getDouble(0);
    }

    public void enableDriverMode() {
        m_driverMode.setBoolean(true);
    }

    public void disableDriverMode() {
        m_driverMode.setBoolean(false);
    }

}
