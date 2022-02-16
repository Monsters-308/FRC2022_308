package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;

public class ShooterSubsystem extends SubsystemBase {
    private final WPI_TalonFX m_shooterMotor = new WPI_TalonFX(ShooterConstants.kShooterMotorCANPort);
    private TalonFXConfiguration m_configs = new TalonFXConfiguration();

    public ShooterSubsystem() {
        m_shooterMotor.configFactoryDefault();
        m_configs.openloopRamp = 1.0;
        m_configs.statorCurrLimit = new StatorCurrentLimitConfiguration(true, 50.0, 55.0, 1.0);
        // m_configs.primaryPID.selectedFeedbackSensor =
        // FeedbackDevice.IntegratedSensor;
        m_configs.nominalOutputForward = 0.0;
        m_configs.nominalOutputReverse = 0.0;
        m_configs.peakOutputForward = 1.0;
        m_configs.peakOutputReverse = 0.0; // dont allow reverse

        m_shooterMotor.configAllSettings(m_configs, 20);
        // I don't know what these do but they seem important
        /*
         * m_shooterMotor.config_kF(0, Constants.ShooterConstants.kF, 20);
         * m_shooterMotor.config_kD(0, Constants.ShooterConstants.kD, 20);
         * m_shooterMotor.config_kP(0, Constants.ShooterConstants.kP, 20);
         * m_shooterMotor.config_kI(0, Constants.ShooterConstants.kI, 20);
         * m_shooterMotor.config_IntegralZone(0, Constants.ShooterConstants.kIzone, 20);
         */
        m_shooterMotor.setNeutralMode(NeutralMode.Coast);
        m_shooterMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 20);
    }

    @Override
    public void periodic() {

    }
}
