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
    
// heading about a unit circle in radians.
// private static double desiredHeading;  // rotates about the Z axis [0,2PI) rad.
// private static double currentHeading;  // rotates about the Z axis [0,2PI) rad.


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
    private RollingAverage AverageAngle = null;

    private final double headingThreshold = 0.05;
    private final int headdingAverageNumberOfSamples = 10;

      // Speed component for rotation about the Z axis. [-x, x]
    private static double vTheta;


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

        // create and initialize the PID for the heading
        headingPID = new PID(propCoeff, integCoeff, diffCoeff);
        
        // get the initial error and put valid data in the telemetry from the imu
        IMUAngleProcessing();
        
        // set initial desired heading to the current actual heading.
        desiredAngle= currentAngle;
        
        // smooths out the joystick input so it doesn't slam hi/lo
        AverageAngle = new RollingAverage(headdingAverageNumberOfSamples);
        
        // initially setup the PID parameters
        headingPID.setOutputLimits(OutputLowLimit, OutputHighLimit);
        headingPID.setMaxIOutput(MaxIOutput);
        headingPID.setOutputRampRate(OutputRampRate);
        headingPID.setOutputFilter(OutputFilter);
        headingPID.setSetpointRange(SetpointRange);
        headingPID.setContinousInputRange(2 * Math.PI);
        headingPID.setContinous(true);  // lets PID know we are working with a continuous range [0-360)

    }

    public double getCurrentAngle()
    {
        currentAngle = sparkEncoder.getPosition() * 2 * Math.PI;
        return currentAngle;
    }
   
    public void setDesiredAngle(double angle)
    {

        AverageAngle.add(angle);  // average in the latest input

        // if the averaged stick input is greater then the headingThreshold go ahead and adjust the heading.
        // This keeps from updating the desiredHeading value if no joystick input is being made.
        // Otherwise, it will always drive the desiredHeading to 0 (neutral joystick position)

        if (Math.abs(AverageAngle.getAverage()) > headingThreshold) {
          desiredAngle = currentAngle - AverageAngle.getAverage();
          // keep heading a positive angle
          if (desiredAngle < 0) {
            desiredAngle += (2 * Math.PI);
          }
        }

        this.desiredAngle = angle;
        // get PID error signal to send to the motor
        IMUAngleProcessing();
        // send to motor
        sparkMotor.set(vTheta);
    }


    // grab the imu heading and crunch out the values used for navigation and telemetry.
    // This method produces the heading input component to the motors from the PID that holds the
    // desired angle.  The error from the PID is sent to the motors in the vTheta variable.
    private void IMUAngleProcessing() {
        // desired angle in degrees +/- 0 to 180 where CCW is + and CW is -
   //       angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS);
        
        // convert  imu angle range to our [0, 2PI) range
   //       if (angles.firstAngle < 0) {
   //           currentHeading = angles.firstAngle + 2 * Math.PI;
   //       } else {
   //          currentHeading = angles.firstAngle;
   //       }
        
        currentAngle = //TODO Calculate the current angle from the encoder

        vTheta = headingPID.getOutput(currentAngle, desiredAngle);
    }
}