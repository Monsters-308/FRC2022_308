package frc.robot.commands.hang;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.RotatingHangSubsystem;

public class RotHangUpDist extends CommandBase {
    private final RotatingHangSubsystem m_hangSubsystem;
    private final double m_distance;
    private double m_startingDistLeft;
    private double m_startingDistRight;

    // Input a positive distance in encoder counts
    public RotHangUpDist(RotatingHangSubsystem hangSubsystem, double distance) {
        m_hangSubsystem = hangSubsystem;
        m_distance = distance;
        addRequirements(m_hangSubsystem);
    }

    @Override
    public void initialize() {
        m_startingDistLeft = m_hangSubsystem.getLeftEncoderDistance();
        m_startingDistRight = m_hangSubsystem.getRightEncoderDistance();
        m_hangSubsystem.runLeftHang();
        m_hangSubsystem.runRightHang();
    }

    @Override
    public boolean isFinished() {
        double leftDist = m_hangSubsystem.getLeftEncoderDistance() - m_startingDistLeft;
        double rightDist = m_hangSubsystem.getRightEncoderDistance() - m_startingDistRight;

        if (leftDist > m_distance) {
            m_hangSubsystem.stopLeftHang();
        }
        if (rightDist > m_distance) {
            m_hangSubsystem.stopRightHang();
        }
        if (leftDist > m_distance && rightDist > m_distance) {
            return true;
        }
        return false;
    }
}
