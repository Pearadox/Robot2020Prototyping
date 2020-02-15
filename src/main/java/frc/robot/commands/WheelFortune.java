/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.subsystems.FortuneSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class WheelFortune extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField", "FieldCanBeLocal"})
  /**
   * Creates a new WheelFortune.
   */
  private double fortune;
  FortuneSubsystem fortuneSubsystem;
  public WheelFortune(FortuneSubsystem fortuneSubsystem) {
    this.fortuneSubsystem = fortuneSubsystem;
    addRequirements(fortuneSubsystem);
    if (!SmartDashboard.containsKey("fortuneRotations")) {
      SmartDashboard.putNumber("fortuneRotations", SmartDashboard.getNumber("fortuneRotations", 0));
    }
    FortuneSubsystem.fortuneEncoder.setPosition(0);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    SmartDashboard.putNumber("fortuneEncoder", 0);
    FortuneSubsystem.fortuneEncoder.setPosition(0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    SmartDashboard.putNumber("fortuneEncoder", FortuneSubsystem.getFortuneEncoder());
    FortuneSubsystem.setMotor1(0.75);
    fortune = SmartDashboard.getNumber("fortuneEncoder", 0);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (fortune > 450) {
      FortuneSubsystem.setMotor1(0);
      return true;
    }
    else {
      return false;
    }
  }
}
