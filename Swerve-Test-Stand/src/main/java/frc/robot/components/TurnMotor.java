package frc.robot.components;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.robot.Constants;
import frc.robot.utilities.PID;

public class TurnMotor
{
    private double currentAngle = 0.0;
    private double desiredAngle = 0.0;
    private PID turnPID;
    private TalonSRX turnMotor;
    

    public TurnMotor(int motorID)
    {
        
        
    }

    public double getCurrentAngle()
    {
        double currentAngle = turnMotor.getSelectedSensorPosition();
        currentAngle *= Constants.angleMultiplier;


        return currentAngle;
    }
   
    public double setDesiredAngle(double angle)
    {
        // TODO 
        // double desiredAngle = cringe.benis();
        return 0;
    }
}