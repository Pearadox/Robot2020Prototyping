/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.DriveTrainConstants.*;

public class Drivetrain extends SubsystemBase {
  /**
   * Creates a new Drivetrain.
   */
  SpeedControllerGroup leftMotors;
  SpeedControllerGroup rightMotors;
  
  AHRS gyro;
  CANEncoder leftFrontEncoder;
  CANEncoder leftBackEncoder;
  CANEncoder rightFrontEncoder;
  CANEncoder rightBackEncoder;
  private final double DEADBAND = 0.1;

  DifferentialDriveOdometry odemetry;
  public Drivetrain() {
    CANSparkMax leftFrontMotor = new CANSparkMax(14, MotorType.kBrushless);
    CANSparkMax leftBackMotor = new CANSparkMax(13, MotorType.kBrushless);
    CANSparkMax rightFrontMotor = new CANSparkMax(10, MotorType.kBrushless);
    CANSparkMax rightBackMotor = new CANSparkMax(11, MotorType.kBrushless);

    leftFrontMotor.setInverted(true);
    leftBackMotor.setInverted(true);
    
    leftMotors = new SpeedControllerGroup(leftFrontMotor, leftBackMotor);
    rightMotors = new SpeedControllerGroup(rightFrontMotor, rightBackMotor);

    leftFrontEncoder = new CANEncoder(leftFrontMotor);
    leftBackEncoder = new CANEncoder(leftBackMotor);                                                                                                                                                                                                                                                                                                                                                        
    rightFrontEncoder = new CANEncoder(rightFrontMotor);
    rightBackEncoder = new CANEncoder(rightBackMotor);

    leftFrontEncoder.setPositionConversionFactor(DISTANCE_PER_PULSE);
    leftBackEncoder.setPositionConversionFactor(DISTANCE_PER_PULSE);
    rightFrontEncoder.setPositionConversionFactor(DISTANCE_PER_PULSE);
    rightBackEncoder.setPositionConversionFactor(DISTANCE_PER_PULSE);

    gyro = new AHRS(SPI.Port.kMXP);
    odemetry = new DifferentialDriveOdometry(new Rotation2d(0));
  }

  public double getLeftEncoder() {
    return ((leftBackEncoder.getPosition() + leftFrontEncoder.getPosition()) / 2);
  }

  public double getRightEncoder() {
    return ((rightBackEncoder.getPosition() + rightFrontEncoder.getPosition()) / 2);
  }

  public void setSpeed(double leftOutput, double rightOutput) {
    leftMotors.set(leftOutput*0.5);
    rightMotors.set(rightOutput*0.5);
  }

  // public void setLeftSpeed(double output) {

  // }

  public void arcadeDrive(double throttle, double twist) {
    if (Math.abs(throttle) < DEADBAND) {
      throttle = 0;
    }
    
    if (Math.abs(twist) < DEADBAND) {
      twist = 0;
    }

    double leftOutput = throttle + twist;
    double rightOutput = throttle - twist;
//    double leftOutput =   SmartDashboard.getNumber("leftOutput",0.0);
//    double rightOutput =  SmartDashboard.getNumber("rightOutput",0.0);
    setSpeed(leftOutput, rightOutput);
  }
  
  public void runPeariscope() {
  }

  public Rotation2d getGyroAngle() {
    return new Rotation2d(Math.toRadians(gyro.getYaw()));
  }

  public double getRadianGyro() {
    return Math.toRadians(gyro.getYaw());
  }

  public void zeroGyro() {
    gyro.zeroYaw();
  }

  public void driveTrainStop() {
    leftMotors.set(0);
    rightMotors.set(0);
    leftFrontEncoder.setPosition(0);
    rightBackEncoder.setPosition(0);
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    odemetry.update(getGyroAngle(), getLeftEncoder(), getRightEncoder());
    if (!SmartDashboard.containsKey("leftOutput")) {
      SmartDashboard.putNumber("leftOutput", 0);
    }
    if (!SmartDashboard.containsKey("rightOutput")){
      SmartDashboard.putNumber("rightOutput", 0);
    }
  }
}
