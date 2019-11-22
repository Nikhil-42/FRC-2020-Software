package frc.robot;

import com.revrobotics.CANSparkMax.IdleMode;

public class Constants
{  
    public static final double DRIVE_P = 0.0005;
    public static final double DRIVE_I = 0.0;
    public static final double DRIVE_D = 0.0;
    public static final double DRIVE_IZ = 0.0;
    public static final double DRIVE_FF = 0.0001818;
    public static final double DRIVE_MAX_OUTPUT = 1.0;
    public static final double DRIVE_MIN_OUTPUT = -1.0;
    public static final boolean DRIVE_INVERT[] = {false, false, true, false}; //TODO: fix for later motor values
    public static final IdleMode DRIVE_IDLEMODE[] = {IdleMode.kCoast, IdleMode.kCoast, IdleMode.kCoast, IdleMode.kCoast}; //TODO: fix for later motor values

    // public static final double TURN_P = 0.0005;
    // public static final double TURN_I = 0.0;
    // public static final double TURN_D = 0.0;
    // public static final double TURN_IZ = 0.0;
    // public static final double TURN_FF = 0.0001818;
    public static final double TURN_MAX_OUTPUT = 1.0;
    public static final double TURN_MIN_OUTPUT = -1.0;
    public static final boolean TURN_INVERT[] = {false, false, true, false}; //TODO: fix for later motor values
    public static final IdleMode TURN_IDLEMODE[] = {IdleMode.kCoast, IdleMode.kCoast, IdleMode.kCoast, IdleMode.kCoast}; //TODO: fix for later motor values

    public static final int DRIVE_MAX_RPM = 18730;

    public static final int DRIVE_POD_ID = 1;
    public static final int TURN_POD_ID = 4;

}