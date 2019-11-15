package frc.robot.components;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;

public class DriveMotor
{
    private double currentRPM = 0.0;
    private double desiredRPM = 0.0;

    private CANPIDController sparkPID;
    private CANSparkMax sparkMotor;
}