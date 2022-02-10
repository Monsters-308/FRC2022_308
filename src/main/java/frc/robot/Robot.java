// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
// Author : FIRST Robotics team Monsters 308

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import frc.robot.Auton.Forward;

/**

 * if necessary, view the TimedRobot documentation. If you change the
 * name of this class or
 * the package after creating this project, you must also update the manifest
 * file in the resource
 * directory.eeee
 */
public class Robot extends TimedRobot {
    private final PWMSparkMax leftDrive1 = new PWMSparkMax(0);
    private final PWMSparkMax leftDrive2 = new PWMSparkMax(1);
    private final PWMSparkMax rightDrive1 = new PWMSparkMax(2);
    private final PWMSparkMax rightDrive2 = new PWMSparkMax(3);
    private final MotorControllerGroup LMotors = new MotorControllerGroup(leftDrive2, leftDrive1);
    private final MotorControllerGroup RMotors = new MotorControllerGroup(rightDrive2, rightDrive1);
    public final DifferentialDrive robotDrive = new DifferentialDrive(LMotors, RMotors);
    XboxController m_driverController = new XboxController(0);

    /**
     * This function is run when the robot is first started up and should be used
     * for any
     * initialization code.
     */
    @Override
    public void robotInit() {
        // We need to invert one side of the drivetrain so that positive voltages
        // result in both sides moving forward. Depending on how your robot's
        // gearbox is constructed, you might have to invert the left side instead.
        // RMotors.setInverted(true);
    }

    Forward auton;//DEPENDANT ON AUTON USED---------------------------------------------------███ 1 ███
    /** This function is run once each time the robot enters autonomous mode. */
    @Override
    public void autonomousInit() {
        auton=new Forward(0.5,1.2);//DEPENDANT ON AUTON USED---------------------------------███ 1 ███
    }

    /** This function is called periodically during autonomous. */
    @Override
    public void autonomousPeriodic() {
        auton.loop(robotDrive);//PARAMATERS DEPENDANT ON AUTON USED--------------------------███ 1 ███
    }

    /**
     * This function is called once each time the robot enters teleoperated mode.
     */
    @Override
    public void teleopInit() {
    }

    /** This function is called periodically during teleoperated mode. */
    @Override
    public void teleopPeriodic() {
        robotDrive.tankDrive(m_driverController.getLeftY(), m_driverController.getRightY());
    }

    /** This function is called once each time the robot enters test mode. */
    @Override
    public void testInit() {
    }

    /** This function is called periodically during test mode. */
    @Override
    public void testPeriodic() {
    }
}
