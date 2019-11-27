package frc.robot.components;


import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.EncoderType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.CAN;
import frc.robot.Constants;
import frc.robot.utilities.*;

public class TurnMotor
{
    private double currentAngle = 0.0;
    private double desiredAngle = 0.0;
    
    //private CANPIDController sparkPID;
    private CANSparkMax sparkMotor;
    private CANEncoder sparkEncoder;

    // PID for the heading
    private final double propCoeff = 0.9;
    private final double integCoeff = 0.0;
    private final double diffCoeff = 0.00;
    private final double OutputLowLimit = -1;
    private final double OutputHighLimit = 1;
    private final double MaxIOutput = 1;
    private final double OutputRampRate = 0.1;
    private final double OutputFilter = 0;
    private final double SetpointRange = 2 * Math.PI;

    private PID headingPID = null;
    private RollingAverage averageHeading = null;
    
    public TurnMotor(int motorID, int motorIndex)
    {
        sparkMotor = new CANSparkMax(motorID, MotorType.kBrushed);
        //sparkPID = sparkMotor.getPIDController();
        sparkEncoder = sparkMotor.getEncoder(EncoderType.kQuadrature, 4096 * 6);
    
        sparkEncoder.setPositionConversionFactor(1);
    
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
        currentAngle = sparkEncoder.getPosition() * 2 * Math.PI;
        return currentAngle;
    }
   
    public void setDesiredAngle(double angle)
    {
        this.desiredAngle = angle;
    }
}