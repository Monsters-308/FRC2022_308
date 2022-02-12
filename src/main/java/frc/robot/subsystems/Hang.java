package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Hang {
    private double speed;

    public Hang(double speed) {
        this.speed = speed;
    }

    public void teleloop(boolean goUp, boolean goDown, TalonSRX motor) {
        if (goUp) {
            motor.set(ControlMode.PercentOutput, speed);
        } else if (goDown) {
            motor.set(ControlMode.PercentOutput, -speed);
        } else {
            motor.set(ControlMode.PercentOutput, 0);
        }
    }
}
