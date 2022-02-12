// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
// Author : FIRST Robotics team Monsters 308

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
//import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.auton.Forward;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
//import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

/**
 * 
 * if necessary, view the TimedRobot documentation. If you change the
 * name of this class or
 * the package after creating this project, you must also update the manifest
 * file in the resource
 * directory.eeee
 */
public class Robot extends TimedRobot {
    private final CANSparkMax leftDrive1 = new CANSparkMax(2, MotorType.kBrushless);
    private final CANSparkMax leftDrive2 = new CANSparkMax(3, MotorType.kBrushless);
    private final CANSparkMax rightDrive1 = new CANSparkMax(1, MotorType.kBrushless);
    private final CANSparkMax rightDrive2 = new CANSparkMax(4, MotorType.kBrushless);
    // private final MotorControllerGroup LMotors = new
    // MotorControllerGroup(leftDrive1, leftDrive2);
    // private final MotorControllerGroup RMotors = new
    // MotorControllerGroup(rightDrive1, rightDrive2);
    private final TalonSRX intake1 = new TalonSRX(5);// update the thingy maybe?
    private final TalonFX shooter1 = new TalonFX(6); // update this too probalby?
    private final TalonSRX indexer1 = new TalonSRX(6); // update this too probalby?
    public final DifferentialDrive robotDrive = new DifferentialDrive(leftDrive1, rightDrive1);
    XboxController driverController = new XboxController(0);
    XboxController coDriverController = new XboxController(1);

    private Intake intake;
    private Shooter shooter;
    private Indexer indexer;

    /**
     * This function is run when the robot is first started up and should be used
     * for any
     * initialization code.
     */
    @Override
    public void robotInit() {
        leftDrive2.follow(leftDrive1);
        rightDrive2.follow(rightDrive1);
        shooter = new Shooter();
        intake = new Intake(0.5);
        indexer = new Indexer(0.5);
        // We need to invert one side of the drivetrain so that positive voltages
        // result in both sides moving forward. Depending on how your robot's
        // gearbox is constructed, you might have to invert the left side instead.
        // rightDrive1.setInverted(true);
    }

    Forward auton;// DEPENDANT ON AUTON USED---------------------------------------------------███
                  // 1 ███

    /** This function is run once each time the robot enters autonomous mode. */
    @Override
    public void autonomousInit() {
        auton = new Forward(0.5, 1.2);// DEPENDANT ON AUTON USED---------------------------------███ 1 ███
    }

    /** This function is called periodically during autonomous. */
    @Override
    public void autonomousPeriodic() {
        auton.loop(robotDrive);// PARAMATERS DEPENDANT ON AUTON USED--------------------------███ 1 ███
    }

    /**
     * This function is called once each time the robot enters teleoperated mode.
     */

    @Override
    public void teleopInit() {

    }

    // controlls: left and right stick controll tank drive, codriver A controlls
    // intake, codriver B controlls falcon
    /** This function is called periodically during teleoperated mode. */
    @Override
    public void teleopPeriodic() {
        robotDrive.tankDrive(driverController.getLeftY(), driverController.getRightY());
        intake.teleloop(coDriverController.getAButton(), intake1);
        shooter.teleloop(coDriverController.getBButton(), 0.5, shooter1);
        indexer.teleloop(indexer1, coDriverController.getYButton());
        SmartDashboard.putNumber("LY joy", driverController.getLeftY());

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
