package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.HangConstants;
import frc.robot.Constants.PneumaticsConstants;

public class RotatingHangSubsystem extends SubsystemBase {
    private final WPI_TalonSRX m_rotatingMotorLeft = new WPI_TalonSRX(HangConstants.kLeftRotatingArmPort);
    private final WPI_TalonSRX m_rotatingMotorRight = new WPI_TalonSRX(HangConstants.kRightRotatingArmPort);
    private final Solenoid m_hangPiston = new Solenoid(PneumaticsConstants.kControlModulePort,
            PneumaticsModuleType.CTREPCM, PneumaticsConstants.kHangPistonPort);
    private static final ShuffleboardTab tab = Shuffleboard.getTab("Hang Arm Encoders");


    public RotatingHangSubsystem() {
        m_rotatingMotorLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        m_rotatingMotorRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        m_rotatingMotorLeft.setSensorPhase(false);
        m_rotatingMotorRight.setSensorPhase(false);
        m_rotatingMotorLeft.setNeutralMode(NeutralMode.Brake);
        m_rotatingMotorRight.setNeutralMode(NeutralMode.Brake);
        m_rotatingMotorRight.setInverted(true);

        tab.addNumber("LeftEncoderCount", m_rotatingMotorLeft::getSelectedSensorPosition);
        tab.addNumber("RightEncoderCount", m_rotatingMotorRight::getSelectedSensorPosition);
        tab.addNumber("LeftEncoderAmps", m_rotatingMotorLeft::getSupplyCurrent);
        tab.addNumber("RightEncoderAmps", m_rotatingMotorRight::getSupplyCurrent);
       }

    public void runLeftHang() {
        m_rotatingMotorLeft.set(TalonSRXControlMode.PercentOutput, HangConstants.kRotHangMotorSpeed);
    }

    public void stopLeftHang() {
        m_rotatingMotorLeft.set(TalonSRXControlMode.PercentOutput, 0);
    }

    public void reverseLeftHang() {
        m_rotatingMotorLeft.set(TalonSRXControlMode.PercentOutput, -HangConstants.kRotHangMotorSpeed);
    }

    public void runRightHang() {
        m_rotatingMotorRight.set(TalonSRXControlMode.PercentOutput, HangConstants.kRotHangMotorSpeed);
    }

    public void stopRightHang() {
        m_rotatingMotorRight.set(TalonSRXControlMode.PercentOutput, 0);
    }

    public void reverseRightHang() {
        m_rotatingMotorRight.set(TalonSRXControlMode.PercentOutput, -HangConstants.kRotHangMotorSpeed);
    }

    public void setDeployed(boolean forward) {
        m_hangPiston.set(forward);
    }

    public void togglePiston() {
        m_hangPiston.toggle();
    }

    public double getLeftEncoderDistance() {
        return m_rotatingMotorLeft.getSelectedSensorPosition();
    }

    public double getRightEncoderDistance() {
        return m_rotatingMotorRight.getSelectedSensorPosition();
    }
    
    @Override
    public void periodic() {
    }
}
