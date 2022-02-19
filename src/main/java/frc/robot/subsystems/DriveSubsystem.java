package frc.robot.subsystems;

// import edu.wpi.first.networktables.NetworkTable;
// import edu.wpi.first.networktables.NetworkTableEntry;
// import edu.wpi.first.networktables.NetworkTableInstance;
// import edu.wpi.first.wpilibj.ADXRS450_Gyro;
// import edu.wpi.first.wpilibj.Solenoid;
// import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import frc.robot.Constants;
import static frc.robot.Constants.DriveConstants;

import com.revrobotics.CANSparkMax;
// import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class DriveSubsystem extends SubsystemBase {
    // The motors on the left side of the drive.
    public final CANSparkMax m_leftFront = new CANSparkMax(DriveConstants.kLeftMotor1Port, MotorType.kBrushless);
    public final CANSparkMax m_leftRear = new CANSparkMax(DriveConstants.kLeftMotor2Port, MotorType.kBrushless);
    // The motors on the right side of the drive.
    private final CANSparkMax m_rightFront = new CANSparkMax(DriveConstants.kRightMotor1Port, MotorType.kBrushless);
    private final CANSparkMax m_rightRear = new CANSparkMax(DriveConstants.kRightMotor2Port, MotorType.kBrushless);

    private final MotorControllerGroup m_leftMotors = new MotorControllerGroup(m_leftFront, m_leftRear);

    private final MotorControllerGroup m_rightMotors = new MotorControllerGroup(m_rightFront, m_rightRear);

    // The robot's drive
    private final DifferentialDrive m_drive = new DifferentialDrive(m_leftMotors, m_rightMotors);

    /**
     * Creates a new DriveSubsystem.
     */
    public DriveSubsystem() {
        m_leftFront.restoreFactoryDefaults();
        m_leftRear.restoreFactoryDefaults();
        m_rightFront.restoreFactoryDefaults();
        m_rightRear.restoreFactoryDefaults();
        m_leftFront.setSmartCurrentLimit(40, 40);
        m_leftRear.setSmartCurrentLimit(40, 40);
        m_rightFront.setSmartCurrentLimit(40, 40);
        m_rightRear.setSmartCurrentLimit(40, 40);
        m_leftRear.follow(m_leftFront);
        m_rightRear.follow(m_rightFront);
    }

    /**
     * Drives the robot using arcade controls.
     *
     * @param fwd the commanded forward movement
     * @param rot the commanded rotation
     */
    public void arcadeDrive(double fwd, double rot) {
        m_drive.arcadeDrive(fwd, rot);
    }

    public void tankDrive(double left, double right) {
        m_drive.tankDrive(left, right);
    }

    /**
     * Sets the max output of the drive. Useful for scaling the drive to drive more
     * slowly.
     *
     * @param maxOutput the maximum output to which the drive will be constrained
     */
    public void setMaxOutput(double maxOutput) {
        m_drive.setMaxOutput(maxOutput);
    }

    @Override
    public void periodic() {
        // e
    }

}