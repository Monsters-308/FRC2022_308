package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drive.DriveDistance;
import frc.robot.commands.shooter.AutoShooter;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class NoGyroAuton extends SequentialCommandGroup {
    /**
     * Creates a new ComplexAuto.
     *
     * @param drive   The drive subsystem this command will run on
     * @param indexer The indexer subsystem this command will run on
     * @param shooter The shooter subsystem this command will run on
     */
    public NoGyroAuton(DriveSubsystem drive, ShooterSubsystem shooter, IndexSubsystem index, LEDSubsystem leads) {
        addCommands(
                new SequentialCommandGroup(
                        new DriveDistance(20, -0.5, drive),
                        new AutoShooter(index, shooter, leads)));
    }

}