package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drive.DriveDistance;
import frc.robot.commands.shooter.AutoLowShooter;
import frc.robot.commands.shooter.AutoShooter;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class LowBall extends SequentialCommandGroup {
    public LowBall(ShooterSubsystem shooter, IndexSubsystem index, LEDSubsystem led) {
        addCommands(new SequentialCommandGroup(

                new AutoLowShooter  (index, shooter, led)));
    }
}

