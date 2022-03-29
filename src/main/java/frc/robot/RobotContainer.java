// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import static frc.robot.Constants.IOConstants;

import java.util.Map;

import frc.robot.commands.auto.LowBall;
import frc.robot.commands.auto.NoAimOneBallAuton;
import frc.robot.commands.auto.NoAutoAimAuton;
import frc.robot.commands.auto.TwoBallAuton;
import frc.robot.commands.drive.ArcadeDrive;
import frc.robot.commands.drive.AutoAim;
import frc.robot.commands.drive.DefaultDrive;
import frc.robot.commands.drive.DriveDistance;
import frc.robot.commands.drive.DriveTurn2;
import frc.robot.commands.drive.StopDrive;
import frc.robot.commands.hang.LowerHang;
import frc.robot.commands.hang.RaiseHang;
import frc.robot.commands.hang.StopHang;
import frc.robot.commands.index.AutoIndex;
import frc.robot.commands.index.StopIndex;
import frc.robot.commands.intake.LowerIntake;
import frc.robot.commands.intake.RaiseIntake;
import frc.robot.commands.intake.StopIntake;
import frc.robot.commands.led.DefaultLED;
import frc.robot.commands.shooter.AutoLowShooter;
import frc.robot.commands.shooter.AutoShooter;
import frc.robot.commands.shooter.ReverseShooter;
import frc.robot.commands.shooter.StopShooter;
import frc.robot.commands.vision.DriverMode;
import frc.robot.commands.vision.LimeLight;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.HangSubsystem;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.VisionSubsystem;
import frc.robot.subsystems.LEDSubsystem.LEDState;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

    XboxController m_driverController = new XboxController(IOConstants.controllerDrivePort);

    XboxController m_coDriverController = new XboxController(IOConstants.controllerCoPort);

    private final DriveSubsystem m_driveSubsystem = new DriveSubsystem(m_driverController);
    private final IndexSubsystem m_indexSubsystem = new IndexSubsystem();
    private final IntakeSubsystem m_intakeSubsystem = new IntakeSubsystem();
    private final ShooterSubsystem m_shooterSubsystem = new ShooterSubsystem();
    private final LEDSubsystem m_ledSubsystem = new LEDSubsystem();
    private final HangSubsystem m_hangSubsystem = new HangSubsystem();
    private final VisionSubsystem m_visionSubsystem = new VisionSubsystem();

    SendableChooser<Command> m_autonChooser = new SendableChooser<>();

    SendableChooser<Command> m_driveChooser = new SendableChooser<>();

    NetworkTableEntry m_autonWaitTime = Shuffleboard.getTab("Autonomous").add("WaitTime", 0.0).getEntry();

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        m_visionSubsystem.ledOff();
        m_ledSubsystem.setLEDState(LEDState.RAINBOW);
        configureButtonBindings();

        m_driveChooser.setDefaultOption("Tank Drive",
                new DefaultDrive(m_driveSubsystem, m_driverController::getLeftY, m_driverController::getRightY,
                        m_driverController::getRightBumper));
        m_driveChooser.addOption("Arcade Drive",
                new ArcadeDrive(m_driveSubsystem, m_driverController::getLeftY, m_driverController::getRightX,
                        m_driverController::getRightBumper));

        Shuffleboard.getTab("Teleop").add(m_driveChooser).withSize(2, 1)
                .withProperties(Map.of("Title", "Drive Controls"));

        // m_autonChooser.addOption("TEST DriveTurn 90", new DriveTurn(90, .4, .02,
        // m_driveSubsystem));
        // m_autonChooser.addOption("TEST DriveTurn 180", new DriveTurn(180, .4, .02,
        // m_driveSubsystem));
        // m_autonChooser.addOption("TEST DriveTurn -90", new DriveTurn(90, -.4, .02,
        // m_driveSubsystem));
        // m_autonChooser.addOption("TEST DriveTime 4 sec", new DriveTime(4, .4,
        // m_driveSubsystem));
        // m_autonChooser.addOption("TEST DriveDistance 5 in", new DriveDistance(5, .45,
        // m_driveSubsystem));
        // m_autonChooser.addOption("TEST DriveDistance 10 in", new DriveDistance(10,
        // .45, m_driveSubsystem));
        m_autonChooser.addOption("LowONE", new LowBall(m_shooterSubsystem, m_indexSubsystem, m_ledSubsystem));
        m_autonChooser.addOption("NONE", null);
        m_autonChooser.addOption("Taxi Reverse", new DriveDistance(32, -0.35, m_driveSubsystem));
        m_autonChooser.addOption("TEST DriveTurn 180", new DriveTurn2(180, 0.4, 0.02, m_driveSubsystem));
        m_autonChooser.addOption("TEST DriveDistance 20 in", new DriveDistance(20, .45, m_driveSubsystem));
        m_autonChooser.addOption("One Ball Simple Auton",
                new NoAimOneBallAuton(m_driveSubsystem, m_shooterSubsystem, m_indexSubsystem, m_ledSubsystem));
        m_autonChooser.addOption("One Ball Simple Auton",
                new NoAimOneBallAuton(m_driveSubsystem, m_shooterSubsystem, m_indexSubsystem, m_ledSubsystem));
        m_autonChooser.setDefaultOption("Two Ball Gryo Auton (170)",
                new NoAutoAimAuton(m_driveSubsystem, m_intakeSubsystem,
                        m_shooterSubsystem, m_indexSubsystem, m_ledSubsystem, 170));
        m_autonChooser.addOption("Two Ball Gryo Auton (175)",
                new NoAutoAimAuton(m_driveSubsystem, m_intakeSubsystem,
                        m_shooterSubsystem, m_indexSubsystem, m_ledSubsystem, 175));
        m_autonChooser.addOption("Two Ball Gryo Auton (180)",
                new NoAutoAimAuton(m_driveSubsystem, m_intakeSubsystem,
                        m_shooterSubsystem, m_indexSubsystem, m_ledSubsystem, 180));
        m_autonChooser.addOption("Two Ball Vision Auton", new TwoBallAuton(m_driveSubsystem, m_intakeSubsystem,
                m_shooterSubsystem, m_indexSubsystem, m_ledSubsystem, m_visionSubsystem));

        // Put the chooser on the dashboard
        Shuffleboard.getTab("Autonomous").add(m_autonChooser).withSize(2, 1)
                .withProperties(Map.of("Title", "Auton Command"));
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be
     * created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing
     * it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        // controls
        // co A : run auto index
        // co B : run auto shooter
        // co Y : reverse all
        // co X : low shooter (speed not tuned)
        // co LBumper : raise intake
        // co RBumper : lower intake
        // driver Y : raise hang
        // driver A : lower hang
        // driver B : auto aim
        // SmartDashboard.putString();
        // System.out.println(Button.values()); // get ALL values of button on
        // controller (D-Pad test)

        // DRIVER CONTROLLS ------------------------------------------------------
        new JoystickButton(m_driverController, Button.kA.value)
                .whenPressed(new LowerHang(m_hangSubsystem))
                .whenReleased(new ParallelCommandGroup(new StopHang(m_hangSubsystem), new DefaultLED(m_ledSubsystem)));

        new JoystickButton(m_driverController, Button.kY.value)
                .whenPressed(new RaiseHang(m_hangSubsystem, m_ledSubsystem))
                .whenReleased(new StopHang(m_hangSubsystem));

        new JoystickButton(m_driverController, Button.kB.value)
                .whenPressed(new SequentialCommandGroup(
                        new DriverMode(m_visionSubsystem, false),
                        new LimeLight(m_visionSubsystem, true),
                        new WaitCommand(0.2),
                        new AutoAim(0.35, m_driveSubsystem, m_visionSubsystem),
                        //new DriverMode(m_visionSubsystem, true),
                        new LimeLight(m_visionSubsystem, false)))
                .whenReleased(new SequentialCommandGroup(
                        new StopDrive(m_driveSubsystem),
                        //new DriverMode(m_visionSubsystem, true),
                        new LimeLight(m_visionSubsystem, false)));
        
        new JoystickButton(m_driverController, Button.kX.value)
        .whenPressed(new LimeLight(m_visionSubsystem, true));

        // CO DRIVER CONTROLLS ------------------------------------------------------
        new JoystickButton(m_coDriverController, Button.kY.value)
                .whenPressed(new ParallelCommandGroup(
                        new InstantCommand(m_intakeSubsystem::reverseIntake, m_intakeSubsystem),
                        new InstantCommand(m_indexSubsystem::reverseIndex, m_indexSubsystem),
                        new ReverseShooter(m_shooterSubsystem)))
                .whenReleased(new ParallelCommandGroup(
                        new StopIntake(m_intakeSubsystem), new StopIndex(m_indexSubsystem),
                        new StopShooter(m_shooterSubsystem), new DefaultLED(m_ledSubsystem)));

        new JoystickButton(m_coDriverController, Button.kA.value)
                .whenPressed(new AutoIndex(m_indexSubsystem, m_intakeSubsystem, m_ledSubsystem))
                .whenReleased(
                        new ParallelCommandGroup(new StopIndex(m_indexSubsystem), new StopIntake(m_intakeSubsystem)));

        new JoystickButton(m_coDriverController, Button.kB.value)
                .whenPressed(new AutoShooter(m_indexSubsystem, m_shooterSubsystem, m_ledSubsystem))
                .whenReleased(
                        new ParallelCommandGroup(new StopIndex(m_indexSubsystem), new StopShooter(m_shooterSubsystem),
                                new DefaultLED(m_ledSubsystem)));

        new JoystickButton(m_coDriverController, Button.kLeftBumper.value)
                .whenPressed(new RaiseIntake(m_intakeSubsystem));

        new JoystickButton(m_coDriverController, Button.kRightBumper.value)
                .whenPressed(new LowerIntake(m_intakeSubsystem));

        new JoystickButton(m_coDriverController, Button.kX.value)
                .whenPressed(new AutoLowShooter(m_indexSubsystem, m_shooterSubsystem, m_ledSubsystem))
                .whenReleased(
                        new ParallelCommandGroup(new StopIndex(m_indexSubsystem), new StopShooter(m_shooterSubsystem),
                                new DefaultLED(m_ledSubsystem)));
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        double waitTime = m_autonWaitTime.getDouble(0);
        return new SequentialCommandGroup(new WaitCommand(waitTime), m_autonChooser.getSelected());
    }

    public void setDefaultDrive() {
        m_driveSubsystem.setDefaultCommand(m_driveChooser.getSelected());
    }
}
