package frc.robot.components;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

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

        sparkMotor.setIdleMode(IdleMode.kCoast);
    }

    /**
     * 
     * @param rpm desired RPMs of the motor shaft
     */
    public void setDesiredRPM(double rpm)
    {
        sparkPID.setReference(rpm, ControlType.kVelocity);
    }

    /**
     * 
     * @return motor shaft velocity in RPM
     */
    public double getCurrentRPM()
    {
        return sparkEncoder.getVelocity();
    }

}