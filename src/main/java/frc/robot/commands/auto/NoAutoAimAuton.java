package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drive.DriveDistance;
import frc.robot.commands.drive.DriveTurn2;
import frc.robot.commands.index.AutoIndex;
import frc.robot.commands.intake.LowerIntake;
import frc.robot.commands.led.DefaultLED;
import frc.robot.commands.shooter.AutoShooter;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class NoAutoAimAuton extends SequentialCommandGroup {
    public NoAutoAimAuton(DriveSubsystem drive, IntakeSubsystem intake, ShooterSubsystem shooter, IndexSubsystem index,
            LEDSubsystem leads, double degrees) {
        addCommands(
                new SequentialCommandGroup(
                        new LowerIntake(intake),
                        new ParallelCommandGroup(
                                new DriveDistance(70, 0.45, drive),
                                new AutoIndex(index, intake, leads)),
                        new DriveDistance(40, -0.45, drive),
                        new DriveTurn2(degrees, 0.4, 0.02, drive),
                        new AutoShooter(index, shooter, leads),
                        new DefaultLED(leads)));

    }
}
