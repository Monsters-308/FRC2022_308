package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

public class Shooter {
    public void teleloop(boolean go, double speed, TalonFX falconMotor) {
        if (go) {
            falconMotor.set(TalonFXControlMode.PercentOutput, speed);
        } else {
            falconMotor.set(TalonFXControlMode.PercentOutput, 0.0);
        }
    }
}