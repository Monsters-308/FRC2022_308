package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.DriveConstants;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
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

    private RelativeEncoder m_leftEncoder = m_leftFront.getEncoder();
    private RelativeEncoder m_rightEncoder = m_rightFront.getEncoder();

    private ADXRS450_Gyro m_gyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);

    private final NetworkTable m_visionTable;
    private final NetworkTableEntry m_visionYaw;

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
        m_leftFront.setInverted(true);
        m_leftFront.setIdleMode(IdleMode.kBrake);
        m_leftRear.setIdleMode(IdleMode.kBrake);
        m_rightFront.setIdleMode(IdleMode.kBrake);
        m_rightRear.setIdleMode(IdleMode.kBrake);
        m_gyro.calibrate();

        m_visionTable = NetworkTableInstance.getDefault().getTable("photon-vision").getSubTable("ShootCam");
        m_visionYaw = m_visionTable.getEntry("yaw");
    }

    public void arcadeDrive(double fwd, double rot) {
        m_drive.arcadeDrive(fwd, rot); // the worst robot drive ever
    }

    public void tankDrive(double left, double right) {
        m_drive.tankDrive(left, right);
    }

    public void setMaxOutput(double maxOutput) {
        m_drive.setMaxOutput(maxOutput);
    }

    public double getAverageEncoderDistanceInches() {
        double rpm = (m_leftEncoder.getPosition() + m_rightEncoder.getPosition()) / 2.0;
        return DriveConstants.kEncoderConversionFactor * rpm;
    }

    public void resetEncoders() {
        m_leftEncoder.setPosition(0);
        m_rightEncoder.setPosition(0);
    }

    public void setBrakeMode(IdleMode idle) {
        m_leftFront.setIdleMode(idle);
        m_leftRear.setIdleMode(idle);
        m_rightFront.setIdleMode(idle);
        m_rightRear.setIdleMode(idle);
    }

    public double getGyroHeading() {
        return m_gyro.getAngle();
    }

    public double getGyroRate() {
        return m_gyro.getRate();
    }

    public void resetGyro() {
        m_gyro.reset();
    }

    public double getVisionYaw() {
        return m_visionYaw.getDouble(0);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("LeftEncoder", m_leftEncoder.getPosition());
        SmartDashboard.putNumber("LeftVelocity", m_leftEncoder.getVelocity());
        SmartDashboard.putNumber("RightEncoder", m_rightEncoder.getPosition());
        SmartDashboard.putNumber("RightVelocity", m_rightEncoder.getVelocity());

        SmartDashboard.putNumber("LeftSpeed", m_leftFront.get());
        SmartDashboard.putNumber("RightSpeed", m_rightFront.get());

        SmartDashboard.putNumber("GyroHeading", getGyroHeading());
        SmartDashboard.putNumber("GyroRate", getGyroRate());

        SmartDashboard.putNumber("VisionYaw", getVisionYaw());
    }

}