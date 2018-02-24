package com.techhounds.hook;

import com.techhounds.Robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Command;

/**
 * TODO refactor to take controller & axis arguments
 */
public class GamepadHookControl extends Command {
	
	private final XboxController controller;
	private final int forwardAxis;
	private final int reverseAxis;

    public GamepadHookControl(XboxController controller, int forward, int reverse) {
    	requires(Robot.hook);
    	this.controller = controller;
    	this.forwardAxis = forward;
    	this.reverseAxis = reverse;
    }

    protected void initialize() {}

    protected void execute() {
    	double forward = Math.pow(controller.getRawAxis(forwardAxis), 2);
    	double reverse = Math.pow(controller.getRawAxis(reverseAxis), 2);
    	
    	Robot.hook.setPower(forward - reverse);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {}

    protected void interrupted() {}
}