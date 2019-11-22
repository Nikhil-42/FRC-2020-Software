/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class IO 
{

    public static Joystick driver = new Joystick(0);

    /**
     * @return the horizontal axis value from the first driver controller
     */
    public double getDriverX()
    {
        return driver.getX();
    }

    /**
     * @return the vertical axis value from the first joystick on the driver controller
     */
    public double getDriverY()
    {
        return driver.getY();
    }

    /**
     * @return the horizontal axis value from the second joystick on the driver controller
     */
    public double getDriverZ()
    {
        return driver.getRawAxis(3);
    }

    /**
     * @return the vertical axis value from the second joystick on the driver controller
     */
    public double getDriverW()
    {
        return driver.getRawAxis(4);
    }


    /**
     * Reads the driver controller first joystick's horizontal value and applies an exponential function based on the exponent provided
     * @param exponent determines how steep the exponential function is
     */
    public double getDriverExpoX(double exponent)
    {
        return getExponential(getDriverX(), exponent);
    }

    /**
     * Reads the driver controller first joystick's vertical value and applies an exponential function based on the exponent provided
     * @param exponent determines how steep the exponential function is
     */
    public double getDriverExpoY(double exponent)
    {
        return getExponential(getDriverY(), exponent);
    }

    /**
     * Reads the driver controller second joystick's horizontal value and applies an exponential function based on the exponent provided
     * @param exponent determines how steep the exponential function is
     */
    public double getDriverExpoZ(double exponent)
    {
        return getExponential(getDriverZ(), exponent);
    }

    /**
     * Reads the driver controller second joystick's vertical value and applies an exponential function based on the exponent provided
     * @param exponent determines how steep the exponential function is
     */
    public double getDriverExpoW(double exponent)
    {
        return getExponential(getDriverW(), exponent);
    }

    /**
     * This function takes a joystick input and applies an exponential scaling
     */
    private double getExponential(double stickInput, double exponent)
    {
        double stickOutput;

        // stickOutput = e^(exponent*|stickInput|) - 1
        stickOutput = Math.exp(Math.abs(stickInput) * exponent) - 1; // Creates an exponential function starting at 0 and with a steepness based on the exponent
        stickOutput /= Math.exp(exponent) - 1; // Scales it back so that at input of 1.0, the output is 1.0
        stickOutput *= Math.signum(stickInput); // Reapplies polarity of the input

        return stickOutput;
    }


}