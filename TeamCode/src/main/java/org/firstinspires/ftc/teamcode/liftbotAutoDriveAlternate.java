package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
 * This file illustrates the concept of driving a path based on encoder counts.
 * It uses the common Pushbot hardware class to define the drive on the robot.
 * The code is structured as a LinearOpMode
 *
 * The code REQUIRES that you DO have encoders on the wheels,
 *   otherwise you would use: PushbotAutoDriveByTime;
 *
 *  This code ALSO requires that the drive Motors have been configured such that a positive
 *  power command moves them forwards, and causes the encoders to count UP.
 *
 *   The desired path in this example is:
 *   - Drive forward for 48 inches
 *   - Spin right for 12 Inches
 *   - Drive Backwards for 24 inches
 *   - Stop and close the claw.
 *
 *  The code is written using a method called: encoderDrive(speed, leftInches, rightInches, timeoutS)
 *  that performs the actual movement.
 *  This methods assumes that each movement is relative to the last stopping place.
 *  There are other ways to perform encoder based moves, but this method is probably the simplest.
 *  This code uses the RUN_TO_POSITION mode to enable the Motor controllers to generate the run profile
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@Autonomous(name="LiftBot: Red Team", group="liftbot")
@Disabled
public class liftbotAutoDriveAlternate extends LinearOpMode {

    /* Declare OpMode membeer.*/
    HardwareLiftBot         robot   = new HardwareLiftBot();   // Use a Pushbot's hardware
    private ElapsedTime     runtime = new ElapsedTime();

    static final double     COUNTS_PER_MOTOR_REV    = 1440 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 2.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     DRIVE_SPEED             = 0.6;
    static final double     TURN_SPEED              = 0.5;

    @Override
    public void runOpMode() {
/*
        telemetry.addData("Say", "..");
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

        robot.frontRight.setPower(1.0);
        robot.frontLeft.setPower(1.0);
        robot.backRight.setPower(1.0);
        robot.backLeft.setPower(1.0);
        runtime.reset();
        while(opModeIsActive() && runtime.seconds() < 4.0)
        {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        sleep(500);

        robot.frontRight.setPower(-1.0);
        robot.frontLeft.setPower(1.0);
        robot.backRight.setPower(-1.0);
        robot.backLeft.setPower(1.0);
        runtime.reset();
        while(opModeIsActive() && runtime.seconds() < 2.0)
        {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        sleep(500);

        robot.frontRight.setPower(1.0);
        robot.frontLeft.setPower(1.0);
        robot.backRight.setPower(1.0);
        robot.backLeft.setPower(1.0);
        runtime.reset();
        while(opModeIsActive() && runtime.seconds() < 7.0)
        {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        sleep(5000);

        move(8, 7 , 5.0);
    }

    public void move(double direction, double inches, double timeout) {

        int newTRPos;
        int newTLPos;
        int newBRPos;
        int newBLPos;

        if(opModeIsActive()) {
            int currentTRPos = robot.frontRight.getCurrentPosition();
            int currentTLPos = robot.frontLeft.getCurrentPosition();
            int currentBRPos = robot.backRight.getCurrentPosition();
            int currentBlPos = robot.backLeft.getCurrentPosition();

            newTRPos = (int) (currentTRPos + (int) inches * COUNTS_PER_INCH);
            newTLPos = (int) (currentTLPos + (int) inches * COUNTS_PER_INCH);
            newBRPos = (int) (currentBRPos + (int) inches * COUNTS_PER_INCH);
            newBLPos = (int) (currentBlPos + (int) inches * COUNTS_PER_INCH);

            if (direction == 8) {
                robot.frontRight.setPower(newTRPos);
                robot.frontLeft.setPower(newTLPos);
                robot.backRight.setPower(newBRPos);
                robot.backLeft.setPower(newBLPos);
            } else if (direction == 2) {
                robot.frontRight.setPower(-newTRPos);
                robot.frontLeft.setPower(-newTLPos);
                robot.backRight.setPower(-newBRPos);
                robot.backLeft.setPower(-newBLPos);
            } else if (direction == 4) {
                robot.frontRight.setPower(newTRPos);
                robot.frontLeft.setPower(-newTLPos);
                robot.backRight.setPower(-newBRPos);
                robot.backLeft.setPower(newBLPos);
            } else if (direction == 6) {
                robot.frontRight.setPower(-newTRPos);
                robot.frontLeft.setPower(newTLPos);
                robot.backRight.setPower(newBRPos);
                robot.backLeft.setPower(-newBLPos);
            } else if (direction == 7) {
                robot.frontRight.setPower(newTRPos);
                robot.frontLeft.setPower(newTLPos);
                robot.backRight.setPower(-newBRPos);
                robot.backLeft.setPower(-newBLPos);
            } else if (direction == 9) {
                robot.frontRight.setPower(-newTRPos);
                robot.frontLeft.setPower(-newTLPos);
                robot.backRight.setPower(newBRPos);
                robot.backLeft.setPower(newBLPos);
            } else {
                telemetry.addData("Say", "Invalid direction.");
                telemetry.update();
            }

            robot.frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            while (opModeIsActive() && (runtime.seconds() < timeout) && (robot.frontRight.isBusy() && robot.frontLeft.isBusy() && robot.backRight.isBusy() && robot.backLeft.isBusy())) {
                telemetry.addData("Say", "Moving");
                telemetry.update();
            }
        }*/
    }

}
