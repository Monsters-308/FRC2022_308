package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.HangConstants;

public class HangSubsystem extends SubsystemBase {
    private final TalonSRX m_hangMotorLeft = new TalonSRX(HangConstants.kLeftMotorPort);
    private final TalonSRX m_hangMotorRight = new TalonSRX(HangConstants.kRightMotorPort);

    private final DigitalInput m_upperLeftSwitch = new DigitalInput(HangConstants.kUpperLeftSwitchPort);
    private final DigitalInput m_lowerLeftSwitch = new DigitalInput(HangConstants.kLowerLeftSwitchPort);
    private final DigitalInput m_upperRightSwitch = new DigitalInput(HangConstants.kUpperRightSwitchPort);
    private final DigitalInput m_lowerRightSwitch = new DigitalInput(HangConstants.kLowerRightSwitchPort);

    public HangSubsystem() {
        m_hangMotorLeft.setNeutralMode(NeutralMode.Brake);
        m_hangMotorRight.setNeutralMode(NeutralMode.Brake);
        m_hangMotorLeft.setInverted(true);
    }

    public void runLeftHang() {
        m_hangMotorLeft.set(ControlMode.PercentOutput, HangConstants.kHangMotorSpeed);
    }

    public void runRightHang() {
        m_hangMotorRight.set(ControlMode.PercentOutput, HangConstants.kHangMotorSpeed);

    }

    public void reverseLeftHang() {
        m_hangMotorLeft.set(ControlMode.PercentOutput, -HangConstants.kHangMotorSpeed);
    }

    public void reverseRightHang() {
        m_hangMotorRight.set(ControlMode.PercentOutput, -HangConstants.kHangMotorSpeed);
    }

    public void stopLeftHang() {
        m_hangMotorLeft.set(ControlMode.PercentOutput, 0);
    }
//e
    public void stopRightHang() {
        m_hangMotorRight.set(ControlMode.PercentOutput, 0);

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
