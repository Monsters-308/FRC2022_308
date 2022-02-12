package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Intake {
    private double speed=0;

    /**
     * Init the intake subsytem
     * 
     * @param speed
     */
    public Intake(double speed) {
        this.speed = speed;
    }

    public void teleloop(boolean go, TalonSRX motor) {
        if (go) {
            motor.set(ControlMode.PercentOutput, speed);
        } else {
            motor.set(ControlMode.PercentOutput, 0.0);
        }
    }
}
