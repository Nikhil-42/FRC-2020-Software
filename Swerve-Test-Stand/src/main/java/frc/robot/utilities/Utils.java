package frc.robot.utilities;

public class Utils
{

    public static double map(double value, double input_min, double input_max, double output_min, double output_max)
    {
        double scaler = (output_max - output_min) / (input_max - input_min);
        double value_scaled = ((value - input_min) * scaler) + output_min;
        
        return value_scaled;
    }

    public static double magnitude(double x, double y)
    {
        return Math.pow(Math.pow(x, 2) + Math.pow(y, 2), 0.5);
    }

    public static double angle(double x, double y) 
    {
        return Math.atan2(y, x);
    }

    

}