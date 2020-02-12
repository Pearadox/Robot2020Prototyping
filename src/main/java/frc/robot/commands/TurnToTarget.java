/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Peariscope;

public class TurnToTarget extends CommandBase {
  /**
   * Creates a new TurnToTarget.
   */
  Peariscope peariscope;
  Drivetrain drivetrain;
  private double kP;
  private double kD;
  private double lastError;

  public TurnToTarget(Peariscope peariscope, Drivetrain drivetrain) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(peariscope);
    this.peariscope = peariscope;
    this.drivetrain = drivetrain;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drivetrain.zeroGyro();
    lastError = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double[] x_list_pct = peariscope.getXListPercent();
    if (x_list_pct.length == 1) {
      double x = x_list_pct[0];
      double leftOutput = 0.1 + x / 100 * kP - (x - lastError) * kD;
      double rightOutput = 0.1 + x / 100 * kP - (x - lastError) * kD;

      if (x > 15 & x < 80) {
        //We need to turn left
        SmartDashboard.putBoolean("TurnRight", true);
        drivetrain.arcadeDrive(leftOutput, rightOutput);
      } else if (x < -15 & x > -80) {
        //We need to turn right
        SmartDashboard.putBoolean("TurnLeft", true);
        drivetrain.arcadeDrive(-leftOutput, rightOutput);
      }
      SmartDashboard.putNumber("leftOutput", leftOutput);
      SmartDashboard.putNumber("rightOutput", rightOutput);
      lastError = x;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}

