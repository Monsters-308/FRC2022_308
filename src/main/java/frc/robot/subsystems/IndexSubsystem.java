package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IndexSubsystem extends SubsystemBase {
    private TalonSRX spinnything = new TalonSRX(47);

    public IndexSubsystem() {
        TalonSRXConfiguration config = new TalonSRXConfiguration();
        config.peakCurrentLimit = 40; // the peak current, in amps
        config.peakCurrentDuration = 1500; // the time at the peak current before the limit triggers, in ms
        config.continuousCurrentLimit = 30; // the current to maintain if the peak limit is triggered
        spinnything.configAllSettings(config); // apply the config settings; this selects the quadrature encoder

    }

    @Override
    public void periodic() {

    }

    public void turnON() {
        spinnything.set(TalonSRXControlMode.PercentOutput, 0.7); // runs the motor at 70% power
    }

    public void turnREVERSE() {
        spinnything.set(TalonSRXControlMode.PercentOutput, -0.7); // runs the motor at 70% power
    }

    public void turnOFF() {
        spinnything.set(TalonSRXControlMode.PercentOutput, 0.0); // runs the motor at 0% power (in case you were
                                                                 // wondering)
    }
}
