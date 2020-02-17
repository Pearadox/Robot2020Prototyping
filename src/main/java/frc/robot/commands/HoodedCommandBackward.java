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
public class HoodedCommandBackward extends CommandBase
{
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField", "FieldCanBeLocal"})
    private final FlywheelSubsystem flywheelSubsystem;

    /**
     * Creates a new ExampleCommand.
     *
     * @param flywheelSubsystem The subsystem used by this command.
     */
    public double degrees;
    public double lastDegree = 0;
    public double degreeError;
    public HoodedCommandBackward(FlywheelSubsystem flywheelSubsystem)
    {
        this.flywheelSubsystem = flywheelSubsystem;
        addRequirements(flywheelSubsystem);
        if (!SmartDashboard.containsKey("snowBlower")) {
            SmartDashboard.putNumber("snowBlower", SmartDashboard.getNumber("snowBlower", 0));
        }
        if (!SmartDashboard.containsKey("hoodDegree")) {
            SmartDashboard.putNumber("hoodDegree", SmartDashboard.getNumber("hoodDegree", 0));
        }
    }

    @Override
    public void initialize() {
    }
   
    @Override
    public void execute() {
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
        flywheelSubsystem.setSnowBlowerMotor(SmartDashboard.getNumber("snowBlower", 0));
        degreeError = lastDegree - degrees;
        lastDegree = degrees;
    }
    
    @Override
    public boolean isFinished() {
        return false;
    }
}
