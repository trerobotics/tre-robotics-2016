package org.firstinspires.ftc.teamcode;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;



@Autonomous(name="LiftBot: Blue Team", group="liftbot")
public class liftbotAutoDrive extends LinearOpMode {

    /* Declare OpMode members. */
    HardwareLiftBot         robot   = new HardwareLiftBot();   // Use a Pushbot's hardware
    private ElapsedTime     runtime = new ElapsedTime();

    /*static final double     COUNTS_PER_MOTOR_REV    = 1440 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 2.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);*/

    static final float     DRIVE_TIME              = 1f;
    static final float     TURN_TIME               = .5f;

    @Override
    public void runOpMode() {

        telemetry.addData("Say", "Starting autonomous.");
        telemetry.update();

        // initialize hardware map
        robot.init(hardwareMap);

        telemetry.addData("Say", "initializing");
        telemetry.update();

        robot.frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        idle();

        robot.frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();

        // Code for autonomous movement goes here

        move(1,0,0,2.5f,2);
        move(0,0,0,2f, 0);
        robot.forkServo.setPosition(180);
        telemetry.addData("Say","servo open");
        telemetry.update();
        sleep(3000);
        robot.forkServo.setPosition(0);
        telemetry.addData("Say","servo closed");
        telemetry.update();
        move(1,0,0,.5f, 2);
        move(-1,0,0,.2f,0);


    }

    public void move(int Y, int X, int Z, float time, long sleepTime)
    {

        int wait = 1;

        robot.frontRight.setPower(Range.clip(-Y + X - Z, -1, 1));
        robot.frontLeft.setPower(Range.clip(-Y - X + Z, -1, 1));
        robot.backRight.setPower(Range.clip(Y + X + Z, -1, 1));
        robot.backLeft.setPower(Range.clip(Y - X - Z, -1, 1));
        runtime.reset();
        while(opModeIsActive() && runtime.seconds() < time)
        {
            telemetry.addData("Path", "Leg: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        runtime.reset();

        while(opModeIsActive() && runtime.seconds() < wait)
        {
            telemetry.addData("Say", "Waiting.");
            telemetry.update();
        }

        sleep(sleepTime);
    }

    public void rotateToDegree(int degree, int speed)
    {
        GyroSensor gyroSensor;
        gyroSensor = (ModernRoboticsI2cGyro)hardwareMap.gyroSensor.get("gyro");
        gyroSensor.calibrate();

        while (!isStopRequested() && gyroSensor.isCalibrating())  {
            sleep(50);
            idle();
        }

        int gyroX = gyroSensor.rawX();
        int gyroY = gyroSensor.rawY();
        int gyroZ = gyroSensor.rawZ();

        int heading = gyroSensor.getHeading();



    }
}