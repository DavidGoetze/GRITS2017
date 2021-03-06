package org.usfirst.frc.team2415.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	final double DEADBAND = 0.05;
	
	final int RIGHT_VICTOR = 0;
	final int LEFT_VICTOR = 2;
	
	final int FORWARD_SOLENOID = 5;
	final int BACKWARD_SOLENOID = 3;

	public XboxController gamepad;
	public Victor leftVictor, rightVictor;
	
	public DoubleSolenoid solenoid;
	public Compressor compressor;
	
	final String defaultAuto = "Default";
	final String customAuto = "My Auto";
	String autoSelected;
	SendableChooser<String> chooser = new SendableChooser<>();
	
	

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		chooser.addDefault("Default Auto", defaultAuto);
		chooser.addObject("My Auto", customAuto);
		SmartDashboard.putData("Auto choices", chooser);
		
		compressor = new Compressor();

		gamepad = new XboxController(0);
		
		leftVictor = new Victor(LEFT_VICTOR);
		rightVictor = new Victor(RIGHT_VICTOR);

		solenoid = new DoubleSolenoid(FORWARD_SOLENOID, BACKWARD_SOLENOID);
		
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the
	 * switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		autoSelected = chooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		System.out.println("Auto selected: " + autoSelected);
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		switch (autoSelected) {
		case customAuto:
			// Put custom auto code here
			break;
		case defaultAuto:
		default:
			// Put default auto code here
			break;
		}
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
	
		double leftY = gamepad.getRawAxis(1);
		double rightX = gamepad.getRawAxis(4);

		if (rightX < DEADBAND) {
			rightX = 0;
		}
		if (leftY < DEADBAND) {
			leftY = 0;
		}
		
		double left = leftY + rightX;
		double right = leftY - rightX;

		leftVictor.set(0.5 * left);
		rightVictor.set(0.5 * right);

		if (gamepad.getAButton()) {
			solenoid.set(DoubleSolenoid.Value.kForward); // Forward
		} else {
			solenoid.set(DoubleSolenoid.Value.kReverse); // Reverse
		}
		
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
}

