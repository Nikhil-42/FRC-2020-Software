package frc.robot.utilities;

public class Utils
{
    public static double map(final double value, final double input_min, final double input_max,
            final double output_min, final double output_max) {
        final double scaler = (output_max - output_min) / (input_max - input_min);
        final double value_scaled = ((value - input_min) * scaler) + output_min;

        return value_scaled;
    }

    public static double magnitude(final double x, final double y) {
        return Math.pow(Math.pow(x, 2) + Math.pow(y, 2), 0.5);
    }

    // convert cartisan joystic coords into angle -PI to PI
    public static double angle(final double x, final double y) 
    {
        double angle;
        angle = Math.atan2(y, x);  // returns -PI to PI
        return angle;
    }

}