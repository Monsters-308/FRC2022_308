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
        public static final int kLeftMotor1Port = 2;
        public static final int kLeftMotor2Port = 3;
        public static final int kRightMotor1Port = 1;
        public static final int kRightMotor2Port = 4;
    }

    public static final class ShooterConstants {
        public static final int kShooterMotorCANPort = 47; // change this when we know the actual port

        public static final double kF = 0.05;
        public static final double kD = 5.0;
        public static final double kI = 0.001;
        public static final double kP = 0.1;
        public static final int kIzone = 300;
    }

    public static final class IntakeConstants {
        public static final int kIntakeMotorPort = 47; // change this when we know the actual port
        public static final int kRetractMotorPort = 47; // change this when we know the actual port

        public static final double kIntakeMotorSpeed = 0.7;
    }

    public static final class IndexConstants {
        public static final int kIndexMotorPort = 47; // change this when we know the actual port

        public static final double kIndexMotorSpeed = 0.7;
    }
    public static final class controllerstuff{
        public static final int controllerDrivePort=0;
        public static final int controllerCoPort=1;//probably
    }
}
