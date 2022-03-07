// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

import static frc.robot.Constants.DriveConstants;
import static frc.robot.Constants.IOConstants;

import frc.robot.commands.auto.NoAutoAimAuton;
import frc.robot.commands.drive.ArcadeDrive;
import frc.robot.commands.drive.DefaultDrive;
import frc.robot.commands.drive.DriveTime;
import frc.robot.commands.hang.RaiseHang;
import frc.robot.commands.index.AutoIndex;
import frc.robot.commands.index.StopIndex;
import frc.robot.commands.intake.LowerIntake;
import frc.robot.commands.intake.RaiseIntake;
import frc.robot.commands.intake.StopIntake;
import frc.robot.commands.led.DefaultLED;
import frc.robot.commands.shooter.AutoShooter;
import frc.robot.commands.shooter.StopShooter;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.HangSubsystem;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
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
    private final DriveSubsystem m_driveSubsystem = new DriveSubsystem();
    private final IndexSubsystem m_indexSubsystem = new IndexSubsystem();
    private final IntakeSubsystem m_intakeSubsystem = new IntakeSubsystem();
    private final ShooterSubsystem m_shooterSubsystem = new ShooterSubsystem();
    private final LEDSubsystem m_ledSubsystem = new LEDSubsystem();
    private final HangSubsystem m_hangSubsystem = new HangSubsystem();

    XboxController m_driverController = new XboxController(IOConstants.controllerDrivePort);

    XboxController m_coDriverController = new XboxController(IOConstants.controllerCoPort);

    SendableChooser<Command> auton_chooser = new SendableChooser<>();

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        configureButtonBindings();

        m_driveSubsystem.setDefaultCommand(
                new DefaultDrive(
                        m_driveSubsystem,
                        m_driverController::getLeftY,
                        m_driverController::getRightY));
        // m_chooser.addOption("Move Off Line", m_simpleAuto);
        // m_chooser.addOption("Shoot Straight(right)", m_complexAutoRight);
        // m_chooser.addOption("Near Trench", m_autoNearTrench);
        // m_chooser.addOption("Far Trench", m_autoFarTrench);
        // m_chooser.addOption("Shoot Straight(left)", m_complexAutoLeft);

        // // Put the chooser on the dashboard
        // Shuffleboard.getTab("Autonomous").add(m_chooser);
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
        // co LBumper : raise intake
        // co RBumper : lower intake
        new JoystickButton(m_coDriverController, Button.kY.value)
                .whenPressed(new ParallelCommandGroup(
                        new InstantCommand(m_intakeSubsystem::reverseIntake, m_intakeSubsystem),
                        new InstantCommand(m_indexSubsystem::reverseIndex, m_indexSubsystem)))
                .whenReleased(new ParallelCommandGroup(
                        new StopIntake(m_intakeSubsystem), new StopIndex(m_indexSubsystem)));

        new JoystickButton(m_coDriverController, Button.kA.value)
                .whenPressed(new AutoIndex(m_indexSubsystem, m_intakeSubsystem, m_ledSubsystem))
                .whenReleased(
                        new ParallelCommandGroup(new StopIndex(m_indexSubsystem), new StopIntake(m_intakeSubsystem)));

        new JoystickButton(m_coDriverController, Button.kB.value)
                .whenPressed(new AutoShooter(m_indexSubsystem, m_shooterSubsystem, m_ledSubsystem))
                .whenReleased(
                        new ParallelCommandGroup(new StopIndex(m_indexSubsystem), new StopShooter(m_shooterSubsystem),
                                new DefaultLED(m_ledSubsystem)));

        new JoystickButton(m_driverController, Button.kY.value)
                .whenPressed(new RaiseHang(m_hangSubsystem))
                .whenReleased(new InstantCommand(m_hangSubsystem::stopHang, m_hangSubsystem));

        new JoystickButton(m_driverController, Button.kA.value)
                .whenPressed(new InstantCommand(m_hangSubsystem::reverseHang, m_hangSubsystem))
                .whenReleased(new InstantCommand(m_hangSubsystem::stopHang, m_hangSubsystem));

        // new JoystickButton(m_coDriverController, Button.kLeftBumper.value)
        // .whenPressed(new RaiseIntake(m_intakeSubsystem))
        // .whenReleased(new InstantCommand(m_intakeSubsystem::stopWinch,
        // m_intakeSubsystem));

        // new JoystickButton(m_coDriverController, Button.kRightBumper.value)
        // .whenPressed(new LowerIntake(m_intakeSubsystem))
        // .whenReleased(new InstantCommand(m_intakeSubsystem::stopWinch,
        // m_intakeSubsystem));

    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        return new NoAutoAimAuton(m_driveSubsystem, m_intakeSubsystem, m_shooterSubsystem, m_indexSubsystem,
                m_ledSubsystem);
    }
}
