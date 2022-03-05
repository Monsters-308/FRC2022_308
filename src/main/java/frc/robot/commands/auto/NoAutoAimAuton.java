package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drive.DriveDistance;
import frc.robot.commands.drive.DriveTurn;
import frc.robot.commands.index.AutoIndex;
import frc.robot.commands.intake.LowerIntake;
import frc.robot.commands.intake.RaiseIntake;
import frc.robot.commands.shooter.AutoShooter;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class NoAutoAimAuton extends SequentialCommandGroup {
    public NoAutoAimAuton(DriveSubsystem drive, IntakeSubsystem intake, ShooterSubsystem shooter, IndexSubsystem index,
            LEDSubsystem leads) {
        addCommands(
                new SequentialCommandGroup(
                        new LowerIntake(intake),
                        new ParallelCommandGroup(
                                new DriveDistance(35, 0.45, drive),
                                new AutoIndex(index, intake, leads)),
                        // new RaiseIntake(intake),
                        new DriveTurn(180, 0.4, 0.02, drive),
                        new DriveDistance(25, 0.45, drive),
                        new AutoShooter(index, shooter, leads)));

    }
}
