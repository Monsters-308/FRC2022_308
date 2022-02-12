package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Indexer {
    private double speed;

    public Indexer(double speed) {
        this.speed = speed;
    }

    public void teleloop(TalonSRX motor, boolean go) {
        if (go) {
            motor.set(ControlMode.PercentOutput, speed);
        }
    }
}
