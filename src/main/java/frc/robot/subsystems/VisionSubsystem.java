package frc.robot.subsystems;

import java.util.List;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonTrackedTarget;

//import edu.wpi.first.networktables.NetworkTable;
//import edu.wpi.first.networktables.NetworkTableEntry;
//import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class VisionSubsystem extends SubsystemBase {
    private final PowerDistribution m_powerDistribution = new PowerDistribution(16, ModuleType.kRev);
    // private final NetworkTable m_visionTable;
    // private final NetworkTableEntry m_visionYaw;
    // private final NetworkTableEntry m_hasTarget;
    // private final NetworkTableEntry m_driverMode;
    PhotonCamera shootCam;

    public VisionSubsystem() {
        shootCam = new PhotonCamera("ShooterCam");
        // m_visionTable = NetworkTableInstance.getDefault().getTable("photonvision").getSubTable("ShooterCam");
        // m_visionYaw = m_visionTable.getEntry("targetYaw");
        // m_hasTarget = m_visionTable.getEntry("hasTarget");
        // m_driverMode = m_visionTable.getEntry("driverMode");


        //SmartDashboard.putString("KEYS", m_visionTable.getKeys().toString());
    }

    public void ledOn() {
        m_powerDistribution.setSwitchableChannel(true);
    }

    public void ledOff() {
        m_powerDistribution.setSwitchableChannel(false);
    }

    public boolean hasTarget() {
        return shootCam.getLatestResult().hasTargets();
    }

    public double getYaw() {
        List<PhotonTrackedTarget> targets=shootCam.getLatestResult().getTargets();
        if(targets.size()>0){
            return targets.get(0).getYaw();
        }
        return 0.0;
        
        //return m_visionYaw.getDouble(0);
    }

    public double getAverageYaw(){
        List<PhotonTrackedTarget> targets = shootCam.getLatestResult().getTargets();
        int x=0;
        double total=0.0;
        while(x<targets.size()){
            total=total+targets.get(x).getYaw();
            x=x+1;
        }
        if (targets.size() > 0) {
            return total/targets.size();
        }
        return 0.0;
    }

    public int numberOfTargets(){
        return shootCam.getLatestResult().getTargets().size();
    }

    public void enableDriverMode() {
        shootCam.setDriverMode(true);
        //m_driverMode.setBoolean(true);
    }

    public void disableDriverMode() {
        shootCam.setDriverMode(false);
        //m_driverMode.setBoolean(false);
    }

}
