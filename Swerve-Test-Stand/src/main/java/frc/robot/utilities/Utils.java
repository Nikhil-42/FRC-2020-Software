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

    /**
     * A function that adds two vectors
     * @param v1 a vector in the form [ang, mag]
     * @param v2 another vector in the form [ang, mag]
     * @return the vector sum, in the form [ang, mag]
     */
    public static double[] addVectors(double[] v1, double[] v2)
    {
        // Seperate the vectors into variables
        double vector1Angle = v1[0];
        double vector1Mag = v1[1];
        double vector2Angle = v2[0];
        double vector2Mag = v2[1];

        // Then seperate into their components
        double vector1x = vector1Mag * Math.cos(vector1Angle);
        double vector1y = vector1Mag * Math.sin(vector1Angle);
        double vector2x = vector2Mag * Math.cos(vector2Angle);
        double vector2y = vector2Mag * Math.sin(vector2Angle);

        // Add the components
        double vectorRx = vector1x + vector2x;
        double vectorRy = vector1y + vector2y;

        // Turn it back into a polar vector
        double vectorRAngle = Math.atan2(vectorRy, vectorRx);
        double vectorRMag = Math.sqrt( Math.pow(vectorRx, 2) + Math.pow(vectorRy, 2) );

        return new double[] {vectorRAngle, vectorRMag};
    }

}