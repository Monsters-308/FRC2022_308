package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class VisionSubsystem extends SubsystemBase {
    private final PowerDistribution m_powerDistribution = new PowerDistribution(16, ModuleType.kRev);
    private final NetworkTable m_shootVisionTable;
    private final NetworkTableEntry m_shootVisionYaw;
    private final NetworkTable m_intakeVisionTable;
    private final NetworkTableEntry m_intakeVisionYaw;

    public VisionSubsystem() {
        m_shootVisionTable = NetworkTableInstance.getDefault().getTable("photon-vision").getSubTable("ShootCam");
        m_shootVisionYaw = m_shootVisionTable.getEntry("targetYaw");
        m_intakeVisionTable = NetworkTableInstance.getDefault().getTable("photon-vision").getSubTable("IntakeCam");
        m_intakeVisionYaw = m_intakeVisionTable.getEntry("targetYaw");
    }

    public void ledOn() {
        m_powerDistribution.setSwitchableChannel(true);
    }

    public void ledOff() {
        m_powerDistribution.setSwitchableChannel(false);
    }

    public double getShootVisionYaw() {
        return m_shootVisionYaw.getDouble(0);
    }

    public double getIntakeVisionYaw() {
        return m_intakeVisionYaw.getDouble(0);
    }

}
