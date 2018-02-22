/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.techhounds;

import com.techhounds.arm.ToggleArm;
import com.techhounds.drivetrain.ArcadeDrive;
import com.techhounds.drivetrain.ToggleTransmission;
import com.techhounds.hook.GamepadHookControl;
import com.techhounds.intake.GamepadIntakeControl;
import com.techhounds.powerpack.GamepadClimberControl;
import com.techhounds.powerpack.GamepadElevatorControl;
import com.techhounds.powerpack.SetElevatorPosition;
import com.techhounds.powerpack.SetElevatorPosition.ElevatorPosition;
import com.techhounds.tilt.SetTiltPosition;
import com.techhounds.tilt.SetTiltPosition.TiltPosition;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	public static final XboxController driver = new XboxController(0);
	public static final XboxController operator = new XboxController(1);

	public OI() {}

	/**
	 * Binds triggers/buttons to commands for the driver
	 */
	public static void setupDriver() {

		// Drivetrain default command is ArcadeDrive
		Robot.drivetrain.setDefaultCommand(new ArcadeDrive(driver, 1, 4));

		// Intake Default is DriverIntakeControl
		Robot.intake.setDefaultCommand(new GamepadIntakeControl(driver, 5, 6));

		Button RB = new JoystickButton(driver, 6);
		RB.whenPressed(new ToggleTransmission());
//		RB.whenReleased(new ToggleTransmission()); // TODO
	}

	/**
	 * Binds triggers/buttons to commands for the operator
	 */
	public static void setupOperator() {

		// Hook default command
		Robot.hook.setDefaultCommand(new GamepadHookControl(operator, 5, 6));

		Button bA = new JoystickButton(operator, 1);
		// Set elevator to DOWN position
		bA.whenPressed(new SetElevatorPosition(ElevatorPosition.COLLECT));

		Button bB = new JoystickButton(operator, 2);
		// Set elevator to SWITCH position
		bB.whenPressed(new SetElevatorPosition(ElevatorPosition.SWITCH));

		Button bX = new JoystickButton(operator, 3);
		// Actuate collector arms
		bX.whenPressed(new ToggleArm());

		Button bY = new JoystickButton(operator, 4);
		// Set elevator to SCALE position
		bY.whenPressed(new SetElevatorPosition(ElevatorPosition.SCALE));

		// TODO: these whileHeld might not work right
		Button LB = new JoystickButton(operator, 5);
		// OperatorClimberControl (joystick)
		LB.whileHeld(new GamepadClimberControl(operator, 1));

		Button RB = new JoystickButton(operator, 6);
		// OperatorElevatorControl (joystick)
		RB.whileHeld(new GamepadElevatorControl(operator, 3));

//		Button select = new JoystickButton(operator, 7);
//		Button start = new JoystickButton(operator, 8);

		Button arrowUp = getPOVButton(operator, 0);
		// Set Tilt to UP
		arrowUp.whenPressed(new SetTiltPosition(TiltPosition.UP));

		Button arrowRight = getPOVButton(operator, 90);
		// Set Tilt to MIDDLE
		arrowRight.whenPressed(new SetTiltPosition(TiltPosition.MIDDLE));

		Button arrowDown = getPOVButton(operator, 180);
		// Set Tilt to DOWN
		arrowDown.whenPressed(new SetTiltPosition(TiltPosition.DOWN));

	}

	/**
	 * Helper method to treat the POV inputs as buttons.
	 * 
	 * @param controller
	 * @param angle
	 * @return
	 */
	private static Button getPOVButton(GenericHID controller, int angle) {
		return new Button() {
			@Override
			public boolean get() {
				return controller.getPOV() == angle;
			}
		};
	}
}
