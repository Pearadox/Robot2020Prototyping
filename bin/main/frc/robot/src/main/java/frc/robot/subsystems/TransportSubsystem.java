package frc.robot.subsystems;


import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class TransportSubsystem extends SubsystemBase {

// Any variables/fields used in the constructor must appear before the "INSTANCE" variable
// so that they are initialized before the constructor is called.

    private CANSparkMax motor1 = new CANSparkMax(4, CANSparkMaxLowLevel.MotorType.kBrushed);
    private CANSparkMax motor2 = new CANSparkMax(6, CANSparkMaxLowLevel.MotorType.kBrushed);

    /**
     * The Singleton instance of this TransportSubsystem. External classes should
     * use the {@link #getInstance()} method to get the instance.
     */
    private final static TransportSubsystem INSTANCE = new TransportSubsystem();

    /**
     * Creates a new instance of this TransportSubsystem.
     * This constructor is private since this class is a Singleton. External classes
     * should use the {@link #getInstance()} method to get the instance.
     */
    private TransportSubsystem() {
        // TODO: Set the default command, if any, for this subsystem by calling setDefaultCommand(command)
        //       in the constructor or in the robot coordination class, such as RobotContainer.
        //       Also, you can call addChild(name, sendableChild) to associate sendables with the subsystem
        //       such as SpeedControllers, Encoders, DigitalInputs, etc.
        motor1.setIdleMode(CANSparkMax.IdleMode.kCoast);
        motor2.setIdleMode(CANSparkMax.IdleMode.kCoast);
        SmartDashboard.putNumber("transport", SmartDashboard.getNumber("transport", 0.0));
    }

    @Override
    public void periodic() {

    }

    public void runTransport() {
        motor1.set(SmartDashboard.getNumber("transport", -0.4d));
        motor2.set(-SmartDashboard.getNumber("transport", -0.4d));
//        motor1.set(-0.8);
//        motor2.set(-0.8);
    }

    public void stop(){
        motor1.set(0);
        motor2.set(0);
    }
    /**
     * Returns the Singleton instance of this TransportSubsystem. This static method
     * should be used -- {@code TransportSubsystem.getInstance();} -- by external
     * classes, rather than the constructor to get the instance of this class.
     */
    public static TransportSubsystem getInstance() {
        return INSTANCE;
    }

}

