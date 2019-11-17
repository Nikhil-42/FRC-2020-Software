package frc.robot.components;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Constants;

public class DriveMotor
{
    private double currentRPM = 0.0;
    private double desiredRPM = 0.0;

    private CANPIDController sparkPID;
    private CANSparkMax sparkMotor;
    private CANEncoder sparkEncoder;

    /**
     * 
     * @param motorID of the Spark Max
     */
    public DriveMotor(int motorID)
    {
        sparkMotor = new CANSparkMax(motorID, MotorType.kBrushless);
        sparkPID = sparkMotor.getPIDController();
        sparkEncoder = sparkMotor.getEncoder();

        sparkPID.setP(Constants.DRIVE_P);
        sparkPID.setI(Constants.DRIVE_I);
        sparkPID.setD(Constants.DRIVE_D);
        sparkPID.setIZone(Constants.DRIVE_IZ);
        sparkPID.setFF(Constants.DRIVE_FF);
        sparkPID.setOutputRange(Constants.DRIVE_MIN_OUTPUT, Constants.DRIVE_MAX_OUTPUT);

        sparkMotor.setInverted(Constants.DRIVE_INTVERT);

        sparkMotor.setIdleMode(IdleMode.kCoast);
    }

    /**
     * 
     * @param rpm desired RPMs of the motor shaft
     */
    public void setDesiredRPM(double rpm)
    {
        desiredRPM = rpm;
        sparkPID.setReference(desiredRPM, ControlType.kVelocity);
    }

    /**
     * 
     * @return motor shaft velocity in RPM
     */
    public double getCurrentRPM()
    {
        currentRPM = sparkEncoder.getVelocity();
        return currentRPM;
    }

}