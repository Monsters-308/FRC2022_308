package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.DigitalInput;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.HangConstants;

public class HangSubsystem extends SubsystemBase {
    private final CANSparkMax m_stationaryMotorLeft = new CANSparkMax(HangConstants.kLeftNeoPort, MotorType.kBrushless);
    private final CANSparkMax m_stationaryMotorRight = new CANSparkMax(HangConstants.kRightNeoPort, MotorType.kBrushless);
    
    private final DigitalInput m_upperLeftSwitch = new DigitalInput(HangConstants.kUpperLeftSwitchPort);
    private final DigitalInput m_lowerLeftSwitch = new DigitalInput(HangConstants.kLowerLeftSwitchPort);
    private final DigitalInput m_upperRightSwitch = new DigitalInput(HangConstants.kUpperRightSwitchPort);
    private final DigitalInput m_lowerRightSwitch = new DigitalInput(HangConstants.kLowerRightSwitchPort);

    public HangSubsystem() {
        m_stationaryMotorLeft.restoreFactoryDefaults();
        m_stationaryMotorRight.restoreFactoryDefaults();

        m_stationaryMotorLeft.setSmartCurrentLimit(40);
        m_stationaryMotorRight.setSmartCurrentLimit(40);
        m_stationaryMotorLeft.setIdleMode(IdleMode.kBrake);
        m_stationaryMotorRight.setIdleMode(IdleMode.kBrake);
        m_stationaryMotorLeft.setInverted(true);
    }

    public void runLeftHang() {
        m_stationaryMotorLeft.set(HangConstants.kHangMotorSpeed);
    }

    public void runRightHang() {
        m_stationaryMotorRight.set(HangConstants.kHangMotorSpeed);

    }

    public void reverseLeftHang() {
        m_stationaryMotorLeft.set(-HangConstants.kHangMotorSpeed);
    }

    public void reverseRightHang() {
        m_stationaryMotorRight.set(-HangConstants.kHangMotorSpeed);
    }

    public void stopLeftHang() {
        m_stationaryMotorLeft.set(0);
    }

    public void stopRightHang() {
        m_stationaryMotorRight.set(0);

    }

    public boolean isLeftRaised() {
        return !m_upperLeftSwitch.get();
    }

    public boolean isLeftLowered() {
        return m_lowerLeftSwitch.get();
    }

    public boolean isRightRaised() {
        return !m_upperRightSwitch.get();
    }

    public boolean isRightLowered() {
        return !m_lowerRightSwitch.get();
    }

    @Override
    public void periodic() {
        SmartDashboard.putBoolean("LeftArmRaised", isLeftRaised());
        SmartDashboard.putBoolean("RightArmRaised", isRightRaised());
        SmartDashboard.putBoolean("LeftArmLowered", isLeftLowered());
        SmartDashboard.putBoolean("RightArmLowered", isRightLowered());
    }
}
