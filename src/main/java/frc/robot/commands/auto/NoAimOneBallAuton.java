package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drive.DriveDistance;
import frc.robot.commands.shooter.AutoShooter;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class NoAimOneBallAuton extends SequentialCommandGroup {
    public NoAimOneBallAuton(DriveSubsystem drive, ShooterSubsystem shooter, IndexSubsystem index, LEDSubsystem led) {
        addCommands(new SequentialCommandGroup(
                new DriveDistance(50, -0.45, drive),
                new AutoShooter(index, shooter, led)));
    }
}
