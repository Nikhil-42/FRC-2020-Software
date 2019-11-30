package frc.robot.components;


import com.revrobotics.CANEncoder;
//import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
//import com.revrobotics.ControlType;
import com.revrobotics.EncoderType;
//import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

//import edu.wpi.first.wpilibj.CAN;
import frc.robot.Constants;
import frc.robot.utilities.*;

public class TurnMotor
{
  // heading about a unit circle in radians.
  private double currentAngle = 0.0;  // rotates about the Z axis [0,2PI) rad.
  private double desiredAngle = 0.0;  // rotates about the Z axis [0,2PI) rad.

  //private CANPIDController sparkPID;
  private CANSparkMax sparkMotor;
  private CANEncoder sparkEncoder;

  private PID anglePID = null;
  private RollingAverage AverageAngle = null;

  // Speed component for rotation about the Z axis. [-x, x]
  private static double vTheta;

  public TurnMotor(int motorID, int motorIndex)
  {
      sparkMotor = new CANSparkMax(motorID, MotorType.kBrushed);
      //sparkPID = sparkMotor.getPIDController();
      sparkEncoder = sparkMotor.getEncoder(EncoderType.kQuadrature, 4096 * 6);
      sparkEncoder.setPositionConversionFactor(1);
      // zero the encoder on init to avoid haveing to power off the bot every time.
      sparkEncoder.setPosition(0.0);

      sparkMotor.setInverted(Constants.TURN_INVERT[motorIndex]);
      sparkMotor.setIdleMode(Constants.TURN_IDLEMODE[motorIndex]);

      // create and initialize the PID for the heading
      anglePID = new PID(Constants.TURN_P, Constants.TURN_I, Constants.TURN_D);
      
      // get the initial error and put valid data in the telemetry from the imu
      AngleProcessing();
      
      // set initial desired heading to the current actual heading.
      desiredAngle = currentAngle;
      
      // smooths out the joystick input so it doesn't slam hi/lo
      AverageAngle = new RollingAverage(Constants.headdingAverageNumberOfSamples);
      
      // initially setup the PID parameters
      anglePID.setOutputLimits(Constants.OutputLowLimit, Constants.OutputHighLimit);
      anglePID.setMaxIOutput(Constants.MaxIOutput);
      anglePID.setOutputRampRate(Constants.OutputRampRate);
      anglePID.setOutputFilter(Constants.OutputFilter);
      anglePID.setSetpointRange(Constants.SetpointRange);
      anglePID.setContinousInputRange(2 * Math.PI);  // sets circular continuous input range
      anglePID.setContinous(true);  // lets PID know we are working with a continuous range [0-360)
  }
  public double getCurrentAngle()
  {
      return this.currentAngle;
  }
 
  public void setDesiredAngle(double angle)
  {
      AverageAngle.add(angle);  // average in the latest input angle to smooth out any noise

      this.desiredAngle = AverageAngle.getAverage();

      // get PID error signal to send to the motor
      AngleProcessing();

      // send to motor, signal -1 to 1
      sparkMotor.set(vTheta);
  }
  // grab the current wheel angel and crunch out the value needed to correct to desired angle.
  // This method produces the angle input component to the motor from the PID that holds the
  // desired angle.  The error from the PID is sent to the motors in the vTheta variable.
  private void AngleProcessing() 
  {
      /* fetch the encoder ( +/- 1 = 1 rotation )
         mod div to get number between -0.99999... and 0.99999... */
      currentAngle = sparkEncoder.getPosition() % 1; 
      
      // always keep in terms of positive angle 0 to .99999...
      if(currentAngle < 0) 
      {
          currentAngle = currentAngle + 1; 
      }    
      //convert to radians 
      currentAngle = currentAngle + (2 * Math.PI);

      vTheta = anglePID.getOutput(currentAngle, desiredAngle);

      System.out.println(currentAngle);
      System.out.println(desiredAngle);
  }
  
  public void zeroEncoder()
  {
    sparkEncoder.setPosition(0.0);
  }

}