/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.TurnToTarget;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

  private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);
  private final Drivetrain drivetrain = new Drivetrain();
  private final Joystick joystick = new Joystick(0);
  private final Peariscope peariscope = new Peariscope(drivetrain);
  private final TurnToTarget turnToTarget = new TurnToTarget(peariscope, drivetrain);


  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    configureDefaultCommands();
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
      new JoystickButton(joystick, 10).whileHeld(new RunCommand(
      () -> {
        peariscope.setPeariscopeOn();
        peariscope.runBangBangPeariscope();
        }, peariscope)).whenReleased(drivetrain::driveTrainStop);

      new JoystickButton(joystick, 11).whileHeld(new RunCommand (
        () -> {
          drivetrain.arcadeDrive(0.15,0);
        }, drivetrain)).whenReleased(drivetrain::driveTrainStop);

      new JoystickButton(joystick, 9).whileHeld(turnToTarget).whenReleased(drivetrain::driveTrainStop, drivetrain);
  }

  private void configureDefaultCommands() {
    drivetrain.setDefaultCommand(new RunCommand (
      () -> { 
        drivetrain.arcadeDrive(-joystick.getY(),joystick.getZ());
      }, drivetrain));
  }
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }
}
