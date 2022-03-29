package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.ShooterConstants;

public class ShooterSubsystem extends SubsystemBase {
    private final WPI_TalonFX m_shooterMotor = new WPI_TalonFX(ShooterConstants.kShooterMotorCANPort);
    private final WPI_TalonSRX m_helperMotor = new WPI_TalonSRX(ShooterConstants.kHelperMotorCANPort);
    private final WPI_TalonSRX m_backspinMotor = new WPI_TalonSRX(ShooterConstants.kBackspinMotorPort);
    private TalonFXConfiguration m_configs = new TalonFXConfiguration();

    public ShooterSubsystem() {
        m_shooterMotor.configFactoryDefault();
        m_configs.openloopRamp = 1.0;
        m_configs.statorCurrLimit = new StatorCurrentLimitConfiguration(true, 40.0, 45.0, 1.0);
        m_configs.primaryPID.selectedFeedbackSensor = FeedbackDevice.IntegratedSensor;
        m_configs.nominalOutputForward = 0.0;
        m_configs.nominalOutputReverse = 0.0;
        m_configs.peakOutputForward = 1.0;
        m_configs.peakOutputReverse = -1.0; // dont allow reverse

        m_shooterMotor.configAllSettings(m_configs, 20);
        m_shooterMotor.setInverted(true);
        m_shooterMotor.config_kF(0, ShooterConstants.kF, 20);
        m_shooterMotor.config_kD(0, ShooterConstants.kD, 20);
        m_shooterMotor.config_kP(0, ShooterConstants.kP, 20);
        m_shooterMotor.config_kI(0, ShooterConstants.kI, 20);
        m_shooterMotor.config_IntegralZone(0, ShooterConstants.kIzone, 20);
        m_shooterMotor.setNeutralMode(NeutralMode.Coast);
        m_shooterMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 20);

        m_helperMotor.setNeutralMode(NeutralMode.Brake);
        m_backspinMotor.setNeutralMode(NeutralMode.Coast);
    }

    public void runShooter() {
        m_shooterMotor.set(TalonFXControlMode.PercentOutput, ShooterConstants.kShooterSpeed);
    }

    public void runShooterSlow() {
        m_shooterMotor.set(TalonFXControlMode.PercentOutput, ShooterConstants.kShooterLowGoalSpeed);
    }

    public void stopShooter() {
        m_shooterMotor.set(TalonFXControlMode.PercentOutput, 0);
    }

    public void reverseShooter() {
        m_shooterMotor.set(TalonFXControlMode.PercentOutput, ShooterConstants.kShooterReverseSpeed);
    }

    public void runHelper() {
        m_helperMotor.set(TalonSRXControlMode.PercentOutput, ShooterConstants.kHelperMotorSpeed);
    }

    public void stopHelper() {
        m_helperMotor.set(TalonSRXControlMode.PercentOutput, 0);
    }

    public void reverseHelper() {
        m_helperMotor.set(TalonSRXControlMode.PercentOutput, -ShooterConstants.kHelperMotorSpeed);
    }

    public void runBackspin() {
        m_backspinMotor.set(TalonSRXControlMode.PercentOutput, ShooterConstants.kBackspinMotorSpeed);
    }

    public void stopBackspin() {
        m_backspinMotor.set(TalonSRXControlMode.PercentOutput, 0);
    }

    public void reverseBackspin() {
        m_backspinMotor.set(TalonSRXControlMode.PercentOutput, -ShooterConstants.kBackspinMotorSpeed);
    }

    public double getShooterVelocity() {
        double velocity_RotPer100ms = m_shooterMotor.getSelectedSensorVelocity() / ShooterConstants.kUnitsPerRotation;
        double velocity_RotPerMin = velocity_RotPer100ms * 10.0 * 60.0;
        return velocity_RotPerMin;
    }

    public void speedControlShooter(double targetVelocity) {
        double targetVelocity_SensorUnits = targetVelocity * ShooterConstants.kUnitsPerRotation / 600.0;
        m_shooterMotor.set(TalonFXControlMode.Velocity, targetVelocity_SensorUnits);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("ShooterCurrent", m_shooterMotor.getStatorCurrent());
        SmartDashboard.putNumber("ShooterTemp", m_shooterMotor.getTemperature());
        SmartDashboard.putNumber("ShooterVelocity", getShooterVelocity());
        SmartDashboard.putNumber("ShooterPercent", m_shooterMotor.getMotorOutputPercent());
    }
}
