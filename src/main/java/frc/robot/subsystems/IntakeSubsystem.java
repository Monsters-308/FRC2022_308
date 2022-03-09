package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.IntakeConstants;

public class IntakeSubsystem extends SubsystemBase {
    private final WPI_TalonSRX m_intakeMotor = new WPI_TalonSRX(IntakeConstants.kIntakeMotorPort);
    private final WPI_TalonSRX m_winchMotor = new WPI_TalonSRX(IntakeConstants.kWinchMotorPort);
    private final DigitalInput m_loweredLimitSwitch = new DigitalInput(IntakeConstants.kLoweredSwitchPort);
    private final DigitalInput m_raisedLimitSwitch = new DigitalInput(IntakeConstants.kRaisedSwitchPort);

    public IntakeSubsystem() {
        m_winchMotor.setInverted(true);
        m_intakeMotor.setNeutralMode(NeutralMode.Coast);
        m_winchMotor.setNeutralMode(NeutralMode.Coast);
    }

    /**
     * Forward Intake.
     */
    public void runIntake() {
        m_intakeMotor.set(ControlMode.PercentOutput, IntakeConstants.kIntakeMotorSpeed);
    }

    /**
     * Reverse Intake.
     */
    public void reverseIntake() {
        m_intakeMotor.set(ControlMode.PercentOutput, -IntakeConstants.kIntakeMotorSpeed);
    }

    /**
     * Stop Intake.
     */
    public void stopIntake() {
        m_intakeMotor.set(ControlMode.PercentOutput, 0);
    }

    public void runWinch() {
        m_winchMotor.set(ControlMode.PercentOutput, IntakeConstants.kWinchMotorSpeed);
    }

    public void reverseWinch() {
        m_winchMotor.set(ControlMode.PercentOutput, -IntakeConstants.kWinchMotorSpeed);
    }

    public void stopWinch() {
        m_winchMotor.set(ControlMode.PercentOutput, 0);
    }

    public boolean isLowered() {
        return !m_loweredLimitSwitch.get();
    }

    public boolean isRaised() {
        return !m_raisedLimitSwitch.get();
    }

    @Override
    public void periodic() {
        SmartDashboard.putBoolean("RaisedSwitch", isRaised());
        SmartDashboard.putBoolean("LoweredSwitch", isLowered());
    }
}
