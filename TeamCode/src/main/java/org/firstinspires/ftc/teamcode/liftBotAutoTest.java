package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;

@Autonomous(name="LiftBot: Autonomous test", group="liftbot")
@Disabled
public abstract class liftBotAutoTest extends LinearOpMode {
    HardwareLiftBot     robot   = new HardwareLiftBot();   // Use a liftbot hardware
    private ElapsedTime runtime = new ElapsedTime();

    static final double     COUNTS_PER_MOTOR_REV    = 1440 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 2.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    @Override
    public void runOpMode()
    {
        /*
        telemetry.addData("Say", "..");
        telemetry.update();

        // initialize hardware map
        robot.init(hardwareMap);

        telemetry.addData("Say", "initializing");
        telemetry.update();
        waitForStart();

        robot.frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        idle();

        robot.frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //Steps for the autonomous position will go here

        //test movement. this will move through all of the directions


        sleep(10000);
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

    /*public void Move2(int x, int y, int z, int inches, int timout)
    {
        int currentTRPos = robot.frontRight.getCurrentPosition();
        int currentTLPos = robot.frontLeft.getCurrentPosition();
        int currentBRPos = robot.backRight.getCurrentPosition();
        int currentBlPos = robot.backLeft.getCurrentPosition();

        int newTRPos = (int) (currentTRPos + (int) inches * COUNTS_PER_INCH);
        int newTlPos = (int) (currentTLPos + (int) inches * COUNTS_PER_INCH);
        int newBRPos = (int) (currentBRPos + (int) inches * COUNTS_PER_INCH);
        int newBLPos = (int) (currentBlPos + (int) inches * COUNTS_PER_INCH);

        robot.frontRight.setPower(Range.clip(y + x - z, -1, 1));
        robot.frontLeft.setPower(Range.clip(y - x + z, -1, 1));
        robot.backRight.setPower(Range.clip(z + z + z, -1, 1));
        robot.backLeft.setPower(Range.clip(y - x - z, -1, 1));
    }*/
}