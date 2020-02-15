/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.Set;

import com.sun.jdi.connect.Transport;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.HoodedCommandBackward;
import frc.robot.commands.HoodedCommandForward;
import frc.robot.commands.WheelFortune;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.FlywheelSubsystem;
import frc.robot.subsystems.FortuneSubsystem;
import frc.robot.subsystems.TransportSubsystem;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer
{
    Joystick joystick = new Joystick(0);
    // The robot's subsystems and commands are defined here...
    private final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();

    // public final FlywheelSubsystem flywheelSubsystem = FlywheelSubsystem.getInstance();
    // private final TransportSubsystem transportSubsystem = TransportSubsystem.getInstance();
    private final FortuneSubsystem fortuneSubsystem = FortuneSubsystem.getInstance();

    private final WheelFortune autonomousCommand = new WheelFortune(fortuneSubsystem);
    // private final HoodedCommandBackward hoodBackCommand = new HoodedCommandBackward(flywheelSubsystem);
    // private final HoodedCommandForward hoodForwCommand = new HoodedCommandForward(flywheelSubsystem);

    /**
     * The container for the robot.  Contains subsystems, OI devices, and commands.
     */

    public RobotContainer()
    {
        SmartDashboard.putData(new InstantCommand(
            // () -> flywheelSubsystem.hoodEncoder.setPosition(0)
        ));
        // Configure the button bindings
        configureButtonBindings();
    }

    /**
     * Use this method to define your button->command mappings.  Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick Joystick} or {@link XboxController}), and then passing it to a
     * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton JoystickButton}.
     */
    private void configureButtonBindings() {
    
       /* new JoystickButton(joystick, 10).whileHeld(new RunCommand(
            flywheelSubsystem::runPID, flywheelSubsystem))
            .whenReleased(flywheelSubsystem::stop);
//        new Button() {
//            @Override
//            public boolean get() {
//                return Math.abs(joystick.getY()) > .3;
//            }
//        }.whileActiveContinuous(new RunCommand(flywheelSubsystem::run, flywheelSubsystem)).whenInactive(new InstantCommand(() -> {}, flywheelSubsystem));
        new JoystickButton(joystick,12).whileHeld(new RunCommand(
            transportSubsystem::runTransport, transportSubsystem))
            .whenReleased(transportSubsystem::stop, transportSubsystem);

        new JoystickButton(joystick, 7).whileHeld(hoodBackCommand).whenReleased(flywheelSubsystem::stop, flywheelSubsystem);

        new JoystickButton(joystick, 8).whileHeld(hoodForwCommand).whenReleased(flywheelSubsystem::stop, flywheelSubsystem);

        new JoystickButton(joystick, 9).whileHeld(new RunCommand(
                () -> {
            if (!SmartDashboard.containsKey("accelerator")) {
                SmartDashboard.putNumber("accelerator", 0);
            }
            flywheelSubsystem.setAcceleratorWheel(SmartDashboard.getNumber("accelerator", 0));
            }, flywheelSubsystem
            )).whenReleased(flywheelSubsystem::stop); */
    }


    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand()
    {
        // An ExampleCommand will run in autonomous
        return autonomousCommand;
    }
}
