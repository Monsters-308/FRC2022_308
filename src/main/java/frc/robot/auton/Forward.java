package frc.robot.auton;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Forward {
    private double speed;
    private double time;
    private Timer timer = new Timer();

    /**
     * Initialize a "Forward" Auton class.
     * 
     * @param speed controls the speed and direction
     * @param time  controls the amount of time that the auton can run after its
     *              creation
     */
    public Forward(double speed, double time) {
        this.speed = speed;
        this.time = time;
        timer.reset();
        timer.start();
    }

    /**
     * will loop the auton once
     * 
     * @return will return a list containing the speed for the left and right
     *         motors.
     */
    public void loop(DifferentialDrive motors) {
        if (timer.get() < time) {
            motors.tankDrive(speed, speed);
        } else {
            motors.tankDrive(0.0, 0.0);
        }
    }
}
