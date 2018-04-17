package com.techhounds.auton.util;

import com.techhounds.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DriveStraight extends Command {
	
	private final double targetPower;
	private double initialDistance;
	private final double targetDistance;
	private double initialAngle;
	
	public DriveStraight(double inches) {
		this(inches, inches > 0 ? 0.6 : -0.6);
	}

    public DriveStraight(double inches, double power) {
    	requires(Robot.drivetrain);
    	this.targetDistance = inches;
    	this.targetPower = power;
    }

    protected void initialize() {
    	initialAngle = Robot.gyro.getRotation();
    	initialDistance = Robot.drivetrain.getScaledAverageDistance();
    }

    protected void execute() {
    	double setRight = targetPower;
    	double setLeft = targetPower;
    	
    	double angleError = Robot.gyro.getRotation() - initialAngle;
    	double angleP = (angleError / 50);
    	
    	// fix for driving backwards
    	if (targetPower < 0) {
    		angleP *= -1;
    	}
    	
    	Robot.drivetrain.setPower(setRight * (1+angleP), setLeft * (1-angleP));
    }

    protected boolean isFinished() {
    	return Math.abs(Robot.drivetrain.getScaledAverageDistance() - initialDistance) > Math.abs(targetDistance);
    }

    protected void end() {
    	Robot.drivetrain.setPower(0, 0);
    }

    protected void interrupted() {
    	end();
    }
}
