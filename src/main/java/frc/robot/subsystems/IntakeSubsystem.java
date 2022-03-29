package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.PneumaticsConstants;

import static frc.robot.Constants.IntakeConstants;

public class IntakeSubsystem extends SubsystemBase {
    private final WPI_TalonSRX m_intakeMotor = new WPI_TalonSRX(IntakeConstants.kIntakeMotorPort);
    private final Solenoid m_lowerPiston = new Solenoid(PneumaticsConstants.kControlModulePort, PneumaticsModuleType.CTREPCM, PneumaticsConstants.kIntakePistonPort);

    public IntakeSubsystem() {
        m_intakeMotor.setNeutralMode(NeutralMode.Coast);
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

    public void setLowered(boolean lowered) {
        m_lowerPiston.set(lowered);
    }
}
