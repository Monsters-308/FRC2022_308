package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.IndexConstants;

public class IndexSubsystem extends SubsystemBase {
    private final TalonSRX m_indexMotor = new TalonSRX(IndexConstants.kIndexMotorPort);
    private final DigitalInput m_highSensor = new DigitalInput(IndexConstants.kHighSensorPort);
    private final DigitalInput m_lowSensor = new DigitalInput(IndexConstants.kLowSensorPort);

    public IndexSubsystem() {
        TalonSRXConfiguration config = new TalonSRXConfiguration();
        config.peakCurrentLimit = 40; // the peak current, in amps
        config.peakCurrentDuration = 1500; // the time at the peak current before the limit triggers, in ms
        config.continuousCurrentLimit = 30; // the current to maintain if the peak limit is triggered
        m_indexMotor.configAllSettings(config); // apply the config settings; this selects the quadrature encoder

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

    // Return true if the upper sensor detects a ball
    public boolean isUpperBallPresent() {
        return !m_highSensor.get();
    }

    // Return true if the lower senstor detects a ball
    public boolean isLowerBallPresent() {
        return !m_lowSensor.get();
    }

    @Override
    public void periodic() {

    }
}
