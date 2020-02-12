/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.FlywheelSubsystem;

public class AcceleratorRun extends CommandBase {
  /**
   * Creates a new AccleratorRun.
   */
  FlywheelSubsystem flywheelSubsystem;
  
    private double accelFeedFoward;
    private double acceleratorRPM = 0;
    private double targetAccelRPM = 0;
    private double AkP;

  public AcceleratorRun(FlywheelSubsystem flywheelSubsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(flywheelSubsystem);
    this.flywheelSubsystem = flywheelSubsystem;
    
    SmartDashboard.putNumber("currentAccelRPM", SmartDashboard.getNumber("currentAccelRPM", flywheelSubsystem.getAccelRPM())); 
    SmartDashboard.putNumber("targetAccelRPM", SmartDashboard.getNumber("targetAccelRPM", 0));
    SmartDashboard.putNumber("AkP", 0.0d);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    accelFeedFoward = SmartDashboard.getNumber("Accelfeedfoward", 0.0d);
    AkP = SmartDashboard.getNumber("AkP", 0.0d);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    SmartDashboard.putNumber("targetAccelRPM", SmartDashboard.getNumber("targetAccelRPM", 0));
    targetAccelRPM = SmartDashboard.getNumber("targetAccelRPM", 0);
    double currentAccelRPM = SmartDashboard.getNumber("currentAccelRPM", 0.0d);  
    accelFeedFoward = SmartDashboard.getNumber("Accelfeedfoward", 0.0d);
    AkP = SmartDashboard.getNumber("AkP", 0.0d);
      
    flywheelSubsystem.acceleratorWheel.setVoltage(accelFeedFoward * targetAccelRPM + AkP * (targetAccelRPM - currentAccelRPM));
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
