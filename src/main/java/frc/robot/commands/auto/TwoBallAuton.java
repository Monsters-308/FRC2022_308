package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drive.AutoAim;
import frc.robot.commands.drive.DriveDistance;
import frc.robot.commands.index.AutoIndex;
import frc.robot.commands.intake.LowerIntake;
import frc.robot.commands.drive.DriveTurn2;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.VisionSubsystem;
import frc.robot.commands.shooter.AutoShooter;
import frc.robot.commands.vision.DriverMode;
import frc.robot.commands.vision.LimeLight;
import frc.robot.commands.led.DefaultLED;

public class TwoBallAuton extends SequentialCommandGroup {
    public TwoBallAuton(DriveSubsystem drive, IntakeSubsystem intake, ShooterSubsystem shooter, IndexSubsystem index,
            LEDSubsystem leads, VisionSubsystem vision) {
        addCommands(
                new SequentialCommandGroup(
                        new DriverMode(vision, false),
                        new LimeLight(vision, true),
                        new LowerIntake(intake),
                        new ParallelCommandGroup(
                                new DriveDistance(75, 0.45, drive),
                                new AutoIndex(index, intake, leads)),
                        new DriveTurn2(175, 0.4, 0.02, drive),
                        new DriveDistance(25, 0.45, drive),
                        new AutoAim(0.35, drive, vision),
                        new AutoShooter(index, shooter, leads),
                        new DefaultLED(leads),
                        new DriverMode(vision, true),
                        new LimeLight(vision, false)
                        ));
                        

    }
}
