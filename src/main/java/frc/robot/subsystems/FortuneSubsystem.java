/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class FortuneSubsystem extends SubsystemBase {
  public static CANSparkMax motor1 = new CANSparkMax(3, MotorType.kBrushless);

  public static CANEncoder fortuneEncoder = new CANEncoder(motor1);
  private double fortuneRotations = getFortuneEncoder();

  /**
   * Creates a new FortuneSubsystem.
   */
  private final static FortuneSubsystem INSTANCE = new FortuneSubsystem();

  private FortuneSubsystem() {

  }

  public static double getFortuneEncoder() {
    return fortuneEncoder.getPosition();
  } 
  
  public static void setMotor1(double speed) {
    motor1.setVoltage(speed * 12);
}
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public static FortuneSubsystem getInstance() {
	  return INSTANCE;
  }
}
