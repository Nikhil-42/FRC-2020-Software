package frc.robot.components;

public class SwervePod
{

    private DriveMotor driveMotor;
    private TurnMotor turnMotor;

    public SwervePod(int driveMotorID, int turnMotorID, int podIndex)
    {
        driveMotor = new DriveMotor(driveMotorID, podIndex);
        turnMotor = new TurnMotor(turnMotorID, podIndex);
    }

    public void setDesiredRPM(double speed)
    {
        driveMotor.setDesiredRPM(speed);
    }

    public void setDesiredAngle(double angle)
    {
        turnMotor.setDesiredAngle(angle);
    }

    public double getCurrentRPM()
    {
        return driveMotor.getCurrentRPM();
    }

    public double getCurrentAngle()
    {
        return turnMotor.getCurrentAngle();
    }

}