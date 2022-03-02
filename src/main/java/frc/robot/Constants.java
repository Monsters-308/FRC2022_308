// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    public static final class DriveConstants {
        public static final int kLeftMotor1Port = 1;
        public static final int kLeftMotor2Port = 4;
        public static final int kRightMotor1Port = 2;
        public static final int kRightMotor2Port = 3;
        public static final double kEncoderConversionFactor = 6.125 * Math.PI; // Wheel diameter * pi = circumference
        // Diameter may be wrong, I had to guess
        public static final double kAutonDriveSpeed = 0.5;
    }

    public static final class ShooterConstants {
        public static final int kShooterMotorCANPort = 5;
        public static final int kHelperMotorCANPort = 6;

        public static final double kF = 0.05;
        public static final double kD = 5.0;
        public static final double kI = 0.001;
        public static final double kP = 0.1;
        public static final int kIzone = 300;
        public static final int kUnitsPerRotation = 2048;

        public static final double kShooterSpeed = 0.5;
        public static final double kHelperMotorSpeed = 0.5;

        public static final double kMaxIndexTimeSec = 1;
        public static final double kRampTimeSec = 2;
        public static final double kMaxReleaseTimeSec = 1;
    }

    public static final class IntakeConstants {
        public static final int kIntakeMotorPort = 11;
        public static final int kWinchMotorPort = 8;

        public static final double kIntakeMotorSpeed = 0.5;
        public static final double kWinchMotorSpeed = 0.5;
    }

    public static final class IndexConstants {
        public static final int kIndexMotorPort = 7;
        public static final int kHighSensorPort = 2;
        public static final int kLowSensorPort = 1;
        public static final int kIntakeSensorPort = 0;

        public static final double kIndexMotorSpeed = 0.5;
    }

    public static final class IOConstants {
        public static final int controllerDrivePort = 0;
        public static final int controllerCoPort = 1;
    }
}
