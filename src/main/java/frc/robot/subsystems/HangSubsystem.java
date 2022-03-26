package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.DigitalInput;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.HangConstants;

public class HangSubsystem extends SubsystemBase {
    private final CANSparkMax m_hangMotorLeft = new CANSparkMax(HangConstants.kLeftNeoPort, MotorType.kBrushless);
    private final CANSparkMax m_hangMotorRight = new CANSparkMax(HangConstants.kRightNeoPort, MotorType.kBrushless);

    private final DigitalInput m_upperLeftSwitch = new DigitalInput(HangConstants.kUpperLeftSwitchPort);
    private final DigitalInput m_lowerLeftSwitch = new DigitalInput(HangConstants.kLowerLeftSwitchPort);
    private final DigitalInput m_upperRightSwitch = new DigitalInput(HangConstants.kUpperRightSwitchPort);
    private final DigitalInput m_lowerRightSwitch = new DigitalInput(HangConstants.kLowerRightSwitchPort);

    public HangSubsystem() {
        m_hangMotorLeft.restoreFactoryDefaults();
        m_hangMotorRight.restoreFactoryDefaults();

        m_hangMotorLeft.setSmartCurrentLimit(40);
        m_hangMotorRight.setSmartCurrentLimit(40);
        m_hangMotorLeft.setIdleMode(IdleMode.kBrake);
        m_hangMotorRight.setIdleMode(IdleMode.kBrake);
        m_hangMotorLeft.setInverted(true);
    }

    public void runLeftHang() {
        m_hangMotorLeft.set(HangConstants.kHangMotorSpeed);
    }

    public void runRightHang() {
        m_hangMotorRight.set(HangConstants.kHangMotorSpeed);

    }

    public void reverseLeftHang() {
        m_hangMotorLeft.set(-HangConstants.kHangMotorSpeed);
    }

    public void reverseRightHang() {
        m_hangMotorRight.set(-HangConstants.kHangMotorSpeed);
    }

    public void stopLeftHang() {
        m_hangMotorLeft.set(0);
    }

    public void stopRightHang() {
        m_hangMotorRight.set(0);

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
