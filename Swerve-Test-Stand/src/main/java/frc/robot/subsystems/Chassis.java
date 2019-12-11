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
    
    private SwervePod pod1 = new SwervePod(Constants.DRIVE_POD_ID, Constants.TURN_POD_ID, Constants.POD_FRONT_LEFT);
    private SwervePod pod2 = new SwervePod(Constants.DRIVE_POD_ID, Constants.TURN_POD_ID, Constants.POD_FRONT_RIGHT);
    private SwervePod pod3 = new SwervePod(Constants.DRIVE_POD_ID, Constants.TURN_POD_ID, Constants.POD_BACK_LEFT);
    private SwervePod pod4 = new SwervePod(Constants.DRIVE_POD_ID, Constants.TURN_POD_ID, Constants.POD_BACK_RIGHT);

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

        //double x = Robot.m_oi.getDriverX();
        double speed = Utils.magnitude(Robot.io.getDriverLeftX(), Robot.io.getDriverLeftY());

        // double angle = Utils.angle(Robot.io.getDriverRightX(), Robot.io.getDriverRightY());

        // pod1.setDesiredAngle(angle);
        // pod2.setDesiredAngle(angle);
        // pod3.setDesiredAngle(angle);
        // pod4.setDesiredAngle(angle);

        pod1.setDesiredRPM(speed);
        pod2.setDesiredRPM(speed);
        pod3.setDesiredRPM(speed);
        pod4.setDesiredRPM(speed);

        if(Robot.io.getButtonAPressed())
        {
            pod1.setDesiredAngle(Math.PI);
            pod2.setDesiredAngle(Math.PI);
            pod3.setDesiredAngle(Math.PI);
            pod4.setDesiredAngle(Math.PI);
        }

        if(Robot.io.getButtonBPressed())
        {
            pod1.setDesiredAngle(3*Math.PI/2);
            pod2.setDesiredAngle(3*Math.PI/2);
            pod3.setDesiredAngle(3*Math.PI/2);
            pod4.setDesiredAngle(3*Math.PI/2);
        }

        if(Robot.io.getButtonXPressed())
        {
            pod1.setDesiredAngle(Math.PI/2);
            pod2.setDesiredAngle(Math.PI/2);
            pod3.setDesiredAngle(Math.PI/2);
            pod4.setDesiredAngle(Math.PI/2);
        }

        if(Robot.io.getButtonYPressed())
        {
            pod1.setDesiredAngle(0);
            pod2.setDesiredAngle(0);
            pod3.setDesiredAngle(0);
            pod4.setDesiredAngle(0);
        }
    }

    /**
     * A function that gives every pod angle
     * @return An array of every angle of every pod in the order specified above
     */
    public double[] getAngle()
    {
        return new double[] {pod1.getCurrentAngle(), pod2.getCurrentAngle(), pod3.getCurrentAngle(), pod4.getCurrentAngle()};
    }
}
