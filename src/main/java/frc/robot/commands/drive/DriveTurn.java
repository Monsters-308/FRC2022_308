package frc.robot.commands.drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class DriveTurn extends CommandBase {
    private final DriveSubsystem m_driveSubsystem;
    private final double m_targetHeading;
    private final double m_speed;
    private final double m_accel;
    private double m_currentSpeed = 0;
    private final double m_initialHeading;
    private double percentOff;
    private double gyroHead;

    /**
     * this command will turn the robot smoothly
     * 
     * @param heading        how far (in degrees) that you want to turn, leave this
     *                       positive even if you want to turn in the opposite
     *                       direction
     * @param maxSpeed       the max speed that you want the robot to turn (-1 to
     *                       1). change
     *                       this to negative if you want to turn in the opposite
     *                       direction. speed < 0.6 reccommended.
     * @param accel          how fast the robot accellerates when it turns
     * @param driveSubsystem the drivesubsystem
     */
    public DriveTurn(double heading, double maxSpeed, double accel, DriveSubsystem driveSubsystem) {
        m_targetHeading = heading;
        m_initialHeading = driveSubsystem.getGyroHeading();
        m_speed = maxSpeed;
        m_accel=accel;
        m_driveSubsystem = driveSubsystem;
        addRequirements(driveSubsystem);
    }

    @Override
    public void initialize() {
        // init
    }

    @Override
    public void execute() {

        // if (m_driveSubsystem.getGyroHeading() < m_targetHeading) {
        // m_driveSubsystem.tankDrive(left, right);
        // } else {
        // m_driveSubsystem.tankDrive(left, right);
        // }
    }

    @Override
    public void end(boolean interrupted) {
        m_driveSubsystem.tankDrive(0, 0);
    }

    @Override
    public boolean isFinished() {
        gyroHead = m_driveSubsystem.getGyroHeading();
        percentOff = Math.abs(gyroHead - m_initialHeading)/m_targetHeading;
        if (Math.abs(gyroHead - m_initialHeading) < m_targetHeading) {
            if (percentOff < 0.5) {
                if (m_currentSpeed < m_speed) {
                    m_currentSpeed = m_currentSpeed + m_accel; // ramp up turn speed
                }
            } else {
                if (m_currentSpeed > 0.25) { // minimum speed
                    m_currentSpeed = m_currentSpeed - m_accel; // ramp down turn speed
                }
            }
        } else {
            return true;
        }
        if (m_speed < 0) {
            m_driveSubsystem.tankDrive(m_currentSpeed * -1, m_currentSpeed); // turn left sometimes
        } else {
            m_driveSubsystem.tankDrive(m_currentSpeed, m_currentSpeed * -1); // turn right occasionally
        }
        return false;
        // if (Math.abs(m_driveSubsystem.getGyroHeading() - m_targetHeading) <
        // m_maxHeadingError) {
        // return true;
        // } else {
        // return false;
        // }
    }
}
