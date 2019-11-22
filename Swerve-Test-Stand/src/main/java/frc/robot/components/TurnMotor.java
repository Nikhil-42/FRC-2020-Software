package frc.robot.components;


import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Constants;
public class TurnMotor
{
    private double currentAngle = 0.0;
    private double desiredAngle = 0.0;
    
    //private CANPIDController sparkPID;
    private CANSparkMax sparkMotor;
    //private CANEncoder sparkEncoder;


    public TurnMotor(int motorID, int motorIndex)
    {
        sparkMotor = new CANSparkMax(motorID, MotorType.kBrushed);
        // sparkPID = sparkMotor.getPIDController();
        // sparkEncoder = sparkMotor.getEncoder();

        // sparkPID.setP(Constants.TURN_P);
        // sparkPID.setI(Constants.TURN_I);
        // sparkPID.setD(Constants.TURN_D);
        // sparkPID.setIZone(Constants.TURN_IZ);
        // sparkPID.setFF(Constants.TURN_FF);
        // sparkPID.setOutputRange(Constants.TURN_MIN_OUTPUT, Constants.TURN_MAX_OUTPUT);

        sparkMotor.setInverted(Constants.TURN_INVERT[motorIndex]);

        sparkMotor.setIdleMode(Constants.TURN_IDLEMODE[motorIndex]);
        
    }

    public double getCurrentAngle()
    {
        //TODO FIXME
        //Everything.

        //currentAngle = sparkEncoder.getPosition();
        
        //currentAngle *= Constants.angleMultiplier;

        return currentAngle;
    }
   
    public void setDesiredAngle(double angle)
    {
        this.desiredAngle = angle;
    }
}