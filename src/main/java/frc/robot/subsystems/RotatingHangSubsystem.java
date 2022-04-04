package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.HangConstants;
import frc.robot.Constants.PneumaticsConstants;

public class RotatingHangSubsystem extends SubsystemBase {
    private final WPI_TalonSRX m_rotatingMotorLeft = new WPI_TalonSRX(HangConstants.kLeftRotatingArmPort);
    private final WPI_TalonSRX m_rotatingMotorRight = new WPI_TalonSRX(HangConstants.kRightRotatingArmPort);
    private final Solenoid m_hangPiston = new Solenoid(PneumaticsConstants.kControlModulePort,
            PneumaticsModuleType.CTREPCM, PneumaticsConstants.kHangPistonPort);

    public RotatingHangSubsystem() {
        m_rotatingMotorLeft.setNeutralMode(NeutralMode.Brake);
        m_rotatingMotorRight.setNeutralMode(NeutralMode.Brake);
        m_rotatingMotorRight.setInverted(true);
    }

    public void runLeftHang() {
        m_rotatingMotorLeft.set(TalonSRXControlMode.PercentOutput, 0.8*HangConstants.kRotHangMotorSpeed);
    }

    public void stopLeftHang() {
        m_rotatingMotorLeft.set(TalonSRXControlMode.PercentOutput, 0);
    }

    public void reverseLeftHang() {
        m_rotatingMotorLeft.set(TalonSRXControlMode.PercentOutput, -0.8*HangConstants.kRotHangMotorSpeed);
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
}
