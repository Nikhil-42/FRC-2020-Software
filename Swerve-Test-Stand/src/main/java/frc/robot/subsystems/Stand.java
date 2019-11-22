/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.commands.JoystickDrive;
import frc.robot.components.SwervePod;
import frc.robot.utilities.Utils;

/**
 * Add your docs here.
 */
public class Stand extends Subsystem 
{
    
    private SwervePod pod = new SwervePod(Constants.DRIVE_POD_ID, Constants.TURN_POD_ID, 0);

    @Override
    public void initDefaultCommand() 
    {
        setDefaultCommand(new JoystickDrive());
    }

    public void joystickDrive()
    {
        double x = Robot.m_oi.getDriverExpoX(1.5);
        double y = Robot.m_oi.getDriverExpoY(1.5);

        double mag = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) );

        pod.setDesiredRPM(Utils.map(mag, 0, 1, 0, Constants.DRIVE_MAX_RPM * 0.3));
    }

}
