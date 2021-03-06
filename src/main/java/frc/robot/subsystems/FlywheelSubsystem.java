package frc.robot.subsystems;


import java.nio.channels.Channel;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.AlternateEncoderType;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.EncoderType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalSource;
public class FlywheelSubsystem extends SubsystemBase {

// Any variables/fields used in the constructor must appear before the "INSTANCE" variable
// so that they are initialized before the constructor is called.
    public CANSparkMax motor1 = new CANSparkMax(3, MotorType.kBrushless);
    public CANSparkMax motor2 = new CANSparkMax(7, MotorType.kBrushless);

    private CANEncoder encoder1 = new CANEncoder(motor1);
    private CANEncoder encoder2 = new CANEncoder(motor2);


    public CANSparkMax acceleratorWheel = new CANSparkMax(2, MotorType.kBrushless);
    private CANEncoder encoder3 = new CANEncoder(acceleratorWheel);

    private CANSparkMax snowBlowerMotor= new CANSparkMax(5, MotorType.kBrushed);
    public CANEncoder hoodEncoder = new CANEncoder(snowBlowerMotor, EncoderType.kQuadrature, 8192);


    private double targetRPM = 0;
    private double feedFoward = 0.0023;

    private double hoodDegrees = getHoodEncoder() * (362 / 18);
    private double lastError = 0.0d;
    private double totalError = 0.0d;
    private double snowBlowerPercent = 0.1;
    private double hoodAngle = 0;
    /**
     * The Singleton instance of this FlywheelSubsystem. External classes should
     * use the {@link #getInstance()} method to get the instance.
     */
    private final static FlywheelSubsystem INSTANCE = new FlywheelSubsystem();

    /**
     * Creates a new instance of this FlywheelSubsystem.
     * This constructor is private since this class is a Singleton. External classes
     * should use the {@link #getInstance()} method to get the instance.
     */
    private FlywheelSubsystem() {
        // TODO: Set the default command, if any, for this subsystem by calling setDefaultCommand(command)
        //       in the constructor or in the robot coordination class, such as RobotContainer.
        //       Also, you can call addChild(name, sendableChild) to associate sendables with the subsystem
        //       such as SpeedControllers, Encoders, DigitalInputs, etc.
        motor1.setIdleMode(CANSparkMax.IdleMode.kCoast);
        motor2.setIdleMode(CANSparkMax.IdleMode.kCoast);
        acceleratorWheel.setIdleMode(CANSparkMax.IdleMode.kCoast);
        snowBlowerMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        motor1.setSmartCurrentLimit(80);
        motor2.setSmartCurrentLimit(80);
        // hoodEncoder.setPositionConversionFactor(1);
        SmartDashboard.putNumber("accelerator", 0);
    }

    public void setHoodAngle(double hoodAngle) {
        this.hoodAngle = hoodAngle;
    }

    public double getHoodAngle() {
        return hoodAngle;
    }
    public void setFlywheelSpeed(double rpm) {
        targetRPM = rpm;
    }

    public double getHoodEncoder() {
        return -hoodEncoder.getPosition();
    }
    
    public void setAcceleratorWheel(double setSpeed) {
       acceleratorWheel.set(setSpeed);
    }
    public void stop() {
        motor1.set(0);
        motor2.set(0);
        acceleratorWheel.set(0);
        snowBlowerMotor.set(0);
    }

    public double getRPM() {
        return (encoder1.getVelocity() + encoder2.getVelocity()) / 2;
    }

    public double getAccelRPM() {
        return encoder3.getVelocity();
    }

    public void setSnowBlowerMotor(double speed) {
        snowBlowerMotor.setVoltage(speed * 12);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("target RPM", SmartDashboard.getNumber("target RPM", 2400));
        SmartDashboard.putNumber("current RPM", getRPM());
        SmartDashboard.putNumber("accelerator", SmartDashboard.getNumber("accelerator", 0.0d)); 
        SmartDashboard.putNumber("feedfoward", SmartDashboard.getNumber("feedfoward", feedFoward)); 
        SmartDashboard.putNumber("bangbang Control", 0.0d);
        SmartDashboard.putNumber("kP", SmartDashboard.getNumber("kP", 0));
        SmartDashboard.putNumber("kD", SmartDashboard.getNumber("kD", 0));
        SmartDashboard.putNumber("current1NEO", motor1.getOutputCurrent());
        SmartDashboard.putNumber("current2NEO", motor2.getOutputCurrent());
        if (getHoodEncoder() < 0) {
            SmartDashboard.putNumber("hoodEncoder", 0);
        }
        if (getHoodEncoder() > 4) {
            SmartDashboard.putNumber("hoodEncoder", 4);
        }
        if (hoodDegrees > 80) {
            SmartDashboard.putNumber("hoodDegrees", 80);
        }
        if (hoodDegrees < 0) {
            SmartDashboard.putNumber("hoodDegrees", 0);
        }
    }

    /*
    public void runBangBang() {
        SmartDashboard.putNumber("target RPM", SmartDashboard.getNumber("target RPM", 2400)); 
        SmartDashboard.putNumber("current RPM", getRPM());
        SmartDashboard.putNumber("accelerator", SmartDashboard.getNumber("accelerator", 0.0d));
        SmartDashboard.putNumber("bangbang Control", 0);

        targetRPM = SmartDashboard.getNumber("target RPM", 0.0d);
        double currentRPM = SmartDashboard.getNumber("current RPM", 0.0d);
        acceleratorWheel.set(SmartDashboard.getNumber("accelerator", 0.0d));

        if (currentRPM < targetRPM) {
            motor1.set(SmartDashboard.getNumber("bangbang Control", 0.0d));
            motor2.set(SmartDashboard.getNumber("bangbang Control", 0.0d));
        }
        else {
            motor1.set(0);
            motor2.set(0);
        }
    }*/

    public void runPID() {
        SmartDashboard.putNumber("target RPM", SmartDashboard.getNumber("target RPM", 2400)); //2750
        SmartDashboard.putNumber("current RPM", getRPM());
        SmartDashboard.putNumber("feedfoward", SmartDashboard.getNumber("feedfoward", feedFoward)); //0.002300
        SmartDashboard.putNumber("kP", SmartDashboard.getNumber("kP", 0)); //0.00180
        SmartDashboard.putNumber("kD", SmartDashboard.getNumber("kD", 0)); //0.004
        SmartDashboard.putNumber("accelerator", SmartDashboard.getNumber("accelerator", 0.0d)); //0.5
    

        //the recovery time in between shots with these values is only 0.6 seconds.
        targetRPM = SmartDashboard.getNumber("target RPM", 0.0d);
        double currentRPM = SmartDashboard.getNumber("current RPM", 0.0d);
        double feedfoward = SmartDashboard.getNumber("feedfoward", 0.0d);
        double kP = SmartDashboard.getNumber("kP", 0.0d);
        double kD = SmartDashboard.getNumber("kD", 0.0d);
        
        acceleratorWheel.set(SmartDashboard.getNumber("accelerator", 0.0d));
        motor1.setVoltage(feedfoward * targetRPM + kP * (targetRPM - currentRPM) + kD * ((targetRPM - currentRPM) - lastError));
        motor2.setVoltage(feedfoward * targetRPM + kP * (targetRPM - currentRPM) + kD * ((targetRPM - currentRPM) - lastError));
        lastError = (targetRPM - currentRPM);
    }

    /**
     * Returns the Singleton instance of this FlywheelSubsystem. This static method
     * should be used -- {@code FlywheelSubsystem.getInstance();} -- by external
     * classes, rather than the constructor to get the instance of this class.
     */
    public static FlywheelSubsystem getInstance() {
        return INSTANCE;
    }

}

