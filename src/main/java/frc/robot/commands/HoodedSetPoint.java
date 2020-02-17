/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.FlywheelSubsystem;

/**
 * An example command that uses an example subsystem.
 */

public class HoodedSetPoint extends CommandBase
{
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField", "FieldCanBeLocal"})
    private final FlywheelSubsystem flywheelSubsystem;

    /**
     * Creates a new ExampleCommand.
     *
     * @param flywheelSubsystem The subsystem used by this command.
     */
    public double degrees = 0;
    public double degreeError = 0;
    public double lastDegree = 0;
    private double targetDegree;
    private double DEADBAND = 0.05;
    public HoodedSetPoint(FlywheelSubsystem flywheelSubsystem)
    {
        this.flywheelSubsystem = flywheelSubsystem;
        addRequirements(flywheelSubsystem);
        if (!SmartDashboard.containsKey("snowBlower")) {
          SmartDashboard.putNumber("snowBlower", SmartDashboard.getNumber("snowBlower", 0));
        }
        if (!SmartDashboard.containsKey("hoodDegree")) {
          SmartDashboard.putNumber("hoodDegree", SmartDashboard.getNumber("hoodDegree", 0));
        }
        if (!SmartDashboard.containsKey("hoodSetPoint")) {
          SmartDashboard.putNumber("hoodSetPoint", SmartDashboard.getNumber("hoodSetPoint", 0));
      }
    }

    @Override
    public void initialize() {
      targetDegree = SmartDashboard.getNumber("hoodSetPoint", 0);
    }
   
    @Override
    public void execute() {
      targetDegree = SmartDashboard.getNumber("hoodSetPoint", 0);
      targetDegree = !(SmartDashboard.getNumber("hoodSetPoint", 0) > 55) ? SmartDashboard.getNumber("hoodSetPoint", 0) : 55;
      degrees = flywheelSubsystem.getHoodAngle();

      if (degrees <= 5) {
        SmartDashboard.putNumber("hoodDegree", 5);
        flywheelSubsystem.snowBlowerMotor.setSelectedSensorPosition(0);    
      }
      else if (degrees >= 55) {
        SmartDashboard.putNumber("hoodDegree", 55);
      }
      else {
        SmartDashboard.putNumber("hoodDegree", degrees);
      }

      if (degrees < targetDegree - DEADBAND|| degrees > targetDegree+ DEADBAND) {
        if (degrees <= targetDegree) {
          if (degrees <= (targetDegree - 2.5)) {
            flywheelSubsystem.setSnowBlowerMotor(-1);
          }
          else {
            flywheelSubsystem.setSnowBlowerMotor(-0.25);
          }
        }
          
        else if (degrees >= targetDegree) {
          if (degrees >= (targetDegree + 2.5)) {
            flywheelSubsystem.setSnowBlowerMotor(1);
          }
          else {
            flywheelSubsystem.setSnowBlowerMotor(0.25);
          }
        }
      }
    }

    @Override
    public void end(boolean interrupted) {
      flywheelSubsystem.setSnowBlowerMotor(0);
    }

    
    @Override
    public boolean isFinished() {
      if (degrees >= targetDegree - DEADBAND && degrees <= targetDegree + DEADBAND) {
        return true;
      }
      else {  
        return false;
      }
    }


}
