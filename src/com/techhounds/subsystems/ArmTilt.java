package com.techhounds.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.techhounds.RobotMap;
import com.techhounds.RobotUtilities;
import com.techhounds.commands.SetTiltPower;

import edu.wpi.first.wpilibj.command.Subsystem;

public class ArmTilt extends Subsystem{
	
	private WPI_TalonSRX tiltMotor;
	
	private double P = 0, I = 0, D = 0;
	
	public void tiltMotor() {
		tiltMotor = RobotUtilities.getTalonSRX(RobotMap.TILT);
		configure(tiltMotor);
	}
	
	/**
	 * TODO: setup soft limits
	 * 
	 * Note: we won't need hard limit switches, since it'll be
	 * an analog potentiometer (we won't need to zero it)
	 */
	private void configure(WPI_TalonSRX talon) {
		talon.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, 0);
		
		talon.enableCurrentLimit(true);
		talon.configContinuousCurrentLimit(20, 0);
		talon.configPeakCurrentLimit(40, 0);
		talon.configPeakCurrentDuration(250, 0);
		talon.setNeutralMode(NeutralMode.Brake);
		
		talon.config_kP(0, P, 0);
		talon.config_kI(0, I, 0);
		talon.config_kD(0, D, 0);
		talon.configClosedloopRamp(0.5, 0);
	}

	public void setPosition(double position) {
		tiltMotor.set(ControlMode.Position, position);
	}
	
	public void setPower(double power) {
		tiltMotor.set(ControlMode.PercentOutput, power);
	}
	
	public double getPosition() {
		return tiltMotor.getSensorCollection().getAnalogIn();
	}
	
	public void stopPower() {
		tiltMotor.set(ControlMode.PercentOutput, 0);
	}

	@Override
	/**
	 * TODO: set to "down" position? Or hold position?
	 */
	protected void initDefaultCommand() {
		setDefaultCommand(new SetTiltPower(0));
	}
	
}