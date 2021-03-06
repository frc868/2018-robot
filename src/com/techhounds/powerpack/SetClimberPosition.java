package com.techhounds.powerpack;

import com.techhounds.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetClimberPosition extends Command {

	private final double setpoint;
	
    public SetClimberPosition(double setpoint) {
    	requires(Robot.powerPack);
    	this.setpoint = setpoint;
    }

    protected void initialize() {
    	Robot.powerPack.setClimberPosition(setpoint);
    }

    protected void execute() {}

    protected boolean isFinished() {
        return Robot.powerPack.onTarget();
    }

    protected void end() {
    	// Don't need to do anything, because powerPack's default command is
    	// to hold position!
    }

    protected void interrupted() {}
}
