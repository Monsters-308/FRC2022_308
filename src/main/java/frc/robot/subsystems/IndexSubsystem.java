package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.IndexConstants;

public class IndexSubsystem extends SubsystemBase {
    private TalonSRX m_indexMotor = new TalonSRX(IndexConstants.kIndexMotorPort);
    public DigitalInput sensor1;
    public IndexSubsystem() {
        sensor1 = new DigitalInput(0);
        TalonSRXConfiguration config = new TalonSRXConfiguration();
        config.peakCurrentLimit = 40; // the peak current, in amps
        config.peakCurrentDuration = 1500; // the time at the peak current before the limit triggers, in ms
        config.continuousCurrentLimit = 30; // the current to maintain if the peak limit is triggered
        m_indexMotor.configAllSettings(config); // apply the config settings; this selects the quadrature encoder

    }

    @Override
    public void periodic() {

    }

    public void turnON() {
        m_indexMotor.set(TalonSRXControlMode.PercentOutput, IndexConstants.kIndexMotorSpeed);
    }

    public void turnREVERSE() {
        m_indexMotor.set(TalonSRXControlMode.PercentOutput, -IndexConstants.kIndexMotorSpeed);
    }

    public void turnOFF() {
        m_indexMotor.set(TalonSRXControlMode.PercentOutput, 0.0);
    }
}
