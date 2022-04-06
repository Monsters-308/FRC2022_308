package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.HangConstants;

public class HangSubsystem extends SubsystemBase {
    private final WPI_TalonSRX m_stationaryMotorLeft = new WPI_TalonSRX(HangConstants.kLeftStaticArmPort);
    private final WPI_TalonSRX m_stationaryMotorRight = new WPI_TalonSRX(HangConstants.kRightStaticArmPort);
    
    private final DigitalInput m_upperLeftSwitch = new DigitalInput(HangConstants.kUpperLeftSwitchPort);
    private final DigitalInput m_lowerLeftSwitch = new DigitalInput(HangConstants.kLowerLeftSwitchPort);
    private final DigitalInput m_upperRightSwitch = new DigitalInput(HangConstants.kUpperRightSwitchPort);
    private final DigitalInput m_lowerRightSwitch = new DigitalInput(HangConstants.kLowerRightSwitchPort);

    public HangSubsystem() {
        m_stationaryMotorLeft.setNeutralMode(NeutralMode.Brake);
        m_stationaryMotorRight.setNeutralMode(NeutralMode.Brake);
        m_stationaryMotorLeft.setInverted(true);
    }

    public void runLeftHang() {
        m_stationaryMotorLeft.set(TalonSRXControlMode.PercentOutput, HangConstants.kHangMotorSpeed);
    }

    public void runRightHang() {
        m_stationaryMotorRight.set(TalonSRXControlMode.PercentOutput, HangConstants.kHangMotorSpeed);

    }

    public void reverseLeftHang() {
        m_stationaryMotorLeft.set(TalonSRXControlMode.PercentOutput, -HangConstants.kHangMotorSpeed);
    }

    public void reverseRightHang() {
        m_stationaryMotorRight.set(TalonSRXControlMode.PercentOutput, -HangConstants.kHangMotorSpeed);
    }

    public void stopLeftHang() {
        m_stationaryMotorLeft.set(TalonSRXControlMode.PercentOutput, 0);
    }

    public void stopRightHang() {
        m_stationaryMotorRight.set(TalonSRXControlMode.PercentOutput, 0);

    }

    public boolean isLeftRaised() {
        return !m_upperLeftSwitch.get();
    }

    public boolean isLeftLowered() {
        return !m_lowerLeftSwitch.get();
    }

    public boolean isRightRaised() {
        return !m_upperRightSwitch.get();
    }

    public boolean isRightLowered() {
        return m_lowerRightSwitch.get();
    }

    @Override
    public void periodic() {
        SmartDashboard.putBoolean("LeftArmRaised", isLeftRaised());
        SmartDashboard.putBoolean("RightArmRaised", isRightRaised());
        SmartDashboard.putBoolean("LeftArmLowered", isLeftLowered());
        SmartDashboard.putBoolean("RightArmLowered", isRightLowered());
    }
}
