package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.commands.JoystickDriveChassis;
import frc.robot.components.SwervePod;
import frc.robot.utilities.Utils;

/**
 * Subsystem to control the entire drive base
 */
public class Chassis extends Subsystem 
{

    //                      ^ Front
    //                      |
    //           ________________________
    //          /						 \
    //          |	1				2	 |
    //          |						 |
    //          |						 |
    //          |						 |
    //          |						 |
    //          |  						 |
    //          |						 |
    //          |						 |
    //          |	3				4	 |
    //          |						 |
    //          \________________________/
    
    private SwervePod pod1 = new SwervePod(Constants.POD_1_DRIVE, Constants.POD_1_TURN, Constants.POD_FRONT_LEFT);
    private SwervePod pod2 = new SwervePod(Constants.POD_2_DRIVE,Constants.POD_2_TURN, Constants.POD_FRONT_RIGHT);
    private SwervePod pod3 = new SwervePod(Constants.POD_3_DRIVE, Constants.POD_3_TURN, Constants.POD_BACK_LEFT);
    private SwervePod pod4 = new SwervePod(Constants.POD_4_DRIVE, Constants.POD_4_TURN, Constants.POD_BACK_RIGHT);

    public Chassis()
    {
        pod1.zeroEncoder();
        pod2.zeroEncoder();
        pod3.zeroEncoder();
        pod4.zeroEncoder();
    }


    @Override
    public void initDefaultCommand() 
    {
        setDefaultCommand(new JoystickDriveChassis());
    }



    /**
     * Function called by the JoystickDriveChassis command to drive the robot
     */
    public void joystickDrive()
    {

        // Always call to process PID for turn motors
        pod1.processPod();
        pod2.processPod();
        pod3.processPod();
        pod4.processPod();

        double x = Robot.io.getDriverLeftX(); // Translation x
        double y = Robot.io.getDriverLeftY(); // Translation y
        double r = Robot.io.getDriverRightX(); // Rotation (x)

        /**
         * IF THIS DOESN'T WORK AND ALL ARE OFF BY 90 DEGREES
         * CHANGE translationHeading TO -Math.PI/2
         */
        double translationHeading = 0; // Straight forward
        double swervePower = 0; // The magnitude of swerve movement

        // FIXME: Dimensions will change! What are the dimensions of the test chassis!
        // TODO: Change in Constants.java
        //Robot dimensions (example)
        //Length = 24 in
        //Width  = 20 in
        //
        //      20
        //________________
        //|              |
        //|              |
        //|              |
        //|              |
        //|              |  24
        //|              |
        //|              |
        //|              |
        //----------------
        // SEE Constants.java

        double length = Constants.ROBOT_LENGTH;
        double width = Constants.ROBOT_WIDTH;

        double thetaChassis = Math.atan(length / width); // Gets the angle created from the center of the robot to the top right corner

        /**
         * rh1 is the angle for pod1, and so on.
         * These represent the angles from each pod to the center of the robot.
         * (The angle each one must turn to, to point perpendicular to the line from the center to the pod)
         */
        double rh1 = thetaChassis + Math.PI / 2;
        double rh2 = thetaChassis;
        double rh3 = 2*Math.PI - thetaChassis;
        double rh4 = thetaChassis + Math.PI;

        double rotationPower = -r; // The magnitude of the rotation that we want to perform

        if (x != 0)
            translationHeading += Math.atan2(y, x);
        else if (y > 0)
            translationHeading += Math.PI / 2;
        else
            translationHeading += -Math.PI / 2;

        if (translationHeading < 0)
            translationHeading += 2*Math.PI;

        // SwervePower ranges from [0,1], but the xbox control ranges from [0,sqrt(2)], so divide by sqrt(2) 
        swervePower = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)) / Math.sqrt(2);

        // We are storing arrays as double arrays in the form [angle, mag]
        double[] finalVector1 = Utils.addVectors(new double[] {rh1, rotationPower}, new double[] {translationHeading, swervePower});
        double[] finalVector2 = Utils.addVectors(new double[] {rh2, rotationPower}, new double[] {translationHeading, swervePower});
        double[] finalVector3 = Utils.addVectors(new double[] {rh3, rotationPower}, new double[] {translationHeading, swervePower});
        double[] finalVector4 = Utils.addVectors(new double[] {rh4, rotationPower}, new double[] {translationHeading, swervePower});

        // Create an array we can loop over of the vectors
        double[][] finalVectors = new double[][] {finalVector1, finalVector2, finalVector3, finalVector4};

        // Find the largest vector
        double maxVectorMagnitude = Math.max(Math.max(finalVector1[1], finalVector2[1]), Math.max(finalVector3[1], finalVector4[1]));

        // Normalize all of the vectors to less than 1.0
        // if at least one is larger than 1.0
        if (maxVectorMagnitude > 1.0)
        {
            // Loop through and divide each by the longest
            for (double[] vector : finalVectors)
            {
                vector[1] /= maxVectorMagnitude;
            }
        }

        // Set all of the pod angles and speeds based on these vectors
        pod1.setDesiredAngle(finalVector1[0]);
        pod2.setDesiredAngle(finalVector2[0]);
        pod3.setDesiredAngle(finalVector3[0]);
        pod4.setDesiredAngle(finalVector4[0]);

        pod1.setDesiredRPM(finalVector1[1]);
        pod2.setDesiredRPM(finalVector2[1]);
        pod3.setDesiredRPM(finalVector3[1]);
        pod4.setDesiredRPM(finalVector4[1]);

        //double x = Robot.m_oi.getDriverX();
        //double speed = Utils.magnitude(Robot.io.getDriverLeftX(), Robot.io.getDriverLeftY());
        
        //double speed = 0.01;

        // double angle = Utils.angle(Robot.io.getDriverRightX(), Robot.io.getDriverRightY());

        // pod1.setDesiredAngle(angle);
        // pod2.setDesiredAngle(angle);
        // pod3.setDesiredAngle(angle);
        // pod4.setDesiredAngle(angle);

        // if(Robot.io.getButtonAPressed())
        // {
        //     pod1.setDesiredAngle(Math.PI);
        //     pod2.setDesiredAngle(Math.PI);
        //     pod3.setDesiredAngle(Math.PI);
        //     pod4.setDesiredAngle(Math.PI);
        //  }

        // if(Robot.io.getButtonBPressed())
        // {
        //     pod1.setDesiredAngle(3*Math.PI/2);
        //     pod2.setDesiredAngle(3*Math.PI/2);
        //     pod3.setDesiredAngle(3*Math.PI/2);
        //     pod4.setDesiredAngle(3*Math.PI/2);
        // }

        // if(Robot.io.getButtonXPressed())
        // {
        //     pod1.setDesiredAngle(Math.PI/2);
        //     pod2.setDesiredAngle(Math.PI/2);
        //     pod3.setDesiredAngle(Math.PI/2);
        //     pod4.setDesiredAngle(Math.PI/2);
        // }

        // if(Robot.io.getButtonYPressed())
        // {
        //     pod1.setDesiredAngle(0);
        //     pod2.setDesiredAngle(0);
        //     pod3.setDesiredAngle(0);
        //     pod4.setDesiredAngle(0);
        // }
    }

    /**
     * A function that gives every pod angle
     * @return An array of every angle of every pod in the order specified above
     */
    public double[] getAngles()
    {
        //return new double[] {pod1.getCurrentAngle(), pod2.getCurrentAngle(), pod3.getCurrentAngle(), pod4.getCurrentAngle()};
        return new double[]{};
    }
}
