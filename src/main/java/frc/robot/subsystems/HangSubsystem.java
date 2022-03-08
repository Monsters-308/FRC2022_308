package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.HangConstants;

public class HangSubsystem extends SubsystemBase {
    private final TalonSRX m_hangMotorLeft = new TalonSRX(HangConstants.kLeftMotorPort);
    private final TalonSRX m_hangMotorRight = new TalonSRX(HangConstants.kRightMotorPort);

    private final DigitalInput m_leftLimitSwitch = new DigitalInput(HangConstants.kLeftSwitchPort);
    private final DigitalInput m_rightLimitSwitch = new DigitalInput(HangConstants.kRightSwitchPort);

    public HangSubsystem() {
        m_hangMotorLeft.setNeutralMode(NeutralMode.Brake);
        m_hangMotorRight.setNeutralMode(NeutralMode.Brake);
        m_hangMotorLeft.setInverted(true);
    }

    public void runHang() {
        m_hangMotorLeft.set(ControlMode.PercentOutput, HangConstants.kHangMotorSpeed);
        m_hangMotorRight.set(ControlMode.PercentOutput, HangConstants.kHangMotorSpeed);
    }

    public void reverseHang() {
        m_hangMotorLeft.set(ControlMode.PercentOutput, -HangConstants.kHangMotorSpeed);
        m_hangMotorRight.set(ControlMode.PercentOutput, -HangConstants.kHangMotorSpeed);
    }

    public void stopHang() {
        m_hangMotorLeft.set(ControlMode.PercentOutput, 0);
        m_hangMotorRight.set(ControlMode.PercentOutput, 0);
    }

    public boolean isLeftRaised() {
        return m_leftLimitSwitch.get();
    }

    public boolean isRightRaised() {
        return m_rightLimitSwitch.get();
    }

    @Override
    public void periodic() {
        SmartDashboard.putBoolean("LeftHangArm", isLeftRaised());
        SmartDashboard.putBoolean("RightHangArm", isRightRaised());
    }
}
