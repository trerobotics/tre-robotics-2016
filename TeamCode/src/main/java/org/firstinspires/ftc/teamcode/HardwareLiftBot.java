package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


public class HardwareLiftBot
{
    /* Public OpMode members. */
    public DcMotor frontLeft    = null;
    public DcMotor frontRight   = null;
    public DcMotor backLeft     = null;
    public DcMotor backRight    = null;
    public DcMotor Thrower      = null;
    public Servo forkServo      = null;

    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public HardwareLiftBot(){

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        backLeft = hwMap.dcMotor.get("bottomLeftDrive");
        backRight = hwMap.dcMotor.get("bottomRightDrive");
        frontLeft = hwMap.dcMotor.get("topLeftDrive");
        frontRight = hwMap.dcMotor.get("topRightDrive");
        forkServo   = hwMap.servo.get("forkServo");
        Thrower     = hwMap.dcMotor.get("Thrower");
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.FORWARD);
        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        frontRight.setDirection(DcMotor.Direction.REVERSE);



        // Set all motors to zero power
        backLeft.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        frontRight.setPower(0);
        Thrower.setPower(0);

        //Set all motors to run with encoders.
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Thrower.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // motors that do not use encoders


    }

    /***
     *
     * waitForTick implements a periodic delay. However, this acts like a metronome with a regular
     * periodic tick.  This is used to compensate for varying processing times for each cycle.
     * The function looks at the elapsed cycle time, and sleeps for the remaining time interval.
     *
     * @param periodMs  Length of wait cycle in mSec.
     */
    public void waitForTick(long periodMs) {

        long  remaining = periodMs - (long)period.milliseconds();

        // sleep for the remaining portion of the regular cycle period.
        if (remaining > 0) {
            try {
                Thread.sleep(remaining);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Reset the cycle clock for the next pass.
        period.reset();
    }

    // Gets the values of the joystick values and squares them. This creates finer control as you get closer to zero
    // on the joysticks.
    public void NonLinearMovement(Gamepad gamepad1, Gamepad gamepad2)
    {
        double input1Y = Math.pow(-gamepad1.left_stick_y, 2);

        double input1X = Math.pow(gamepad1.left_stick_x, 2);

        double input1Z = Math.pow(gamepad1.right_stick_x, 2);

        frontRight.setPower(Range.clip(-input1Y + input1X - input1Z, -1, 1));
        frontLeft.setPower(Range.clip(-input1Y - input1X + input1Z, -1, 1));
        backRight.setPower(Range.clip(input1Y + input1X + input1Z, -1, 1));
        backLeft.setPower(Range.clip(input1Y - input1X - input1Z, -1, 1));
    }

    // Gets the values of the joystick values and keeps them at a linear -1 to 1
    public void LinearMovement(Gamepad gamepad1, Gamepad gamepad2)
    {
        double input1Y = -gamepad1.left_stick_y;

        double input1X = gamepad1.left_stick_x;

        double input1Z = gamepad1.right_stick_x;

        frontRight.setPower(Range.clip(-input1Y + input1X - input1Z, -1, 1));
        frontLeft.setPower(Range.clip(-input1Y - input1X + input1Z, -1, 1));
        backRight.setPower(Range.clip(input1Y + input1X + input1Z, -1, 1));
        backLeft.setPower(Range.clip(input1Y - input1X - input1Z, -1, 1));
    }

    // Sets the fork servo to a defined position based on whether the driver wants it open or closed.
    public void MoveFork(Gamepad gamepad1, Gamepad gamepad2)
    {
        //retracted position
        int closePos = 0;
        //open position
        int openPos = 180;

        // Corresponds to the a button on gamepad 2
        boolean aButton = gamepad2.a;

        // Corresponds to the b button on gamepad 2
        boolean bButton = gamepad2.b;

        if(aButton)
        {
            forkServo.setPosition(openPos);
        }
        else if (bButton)
        {
            forkServo.setPosition(closePos);
        }
    }

    public void BallThrower(Gamepad gamepad1, Gamepad gamepad2)
    {
        double moveArm = gamepad2.right_stick_y;
        double throwSpeed = 1;
        double pickUpSpeed = .3;

        boolean throwBall = gamepad2.right_bumper;
        boolean pickUpBall = gamepad2.left_bumper;

        Thrower.setPower(moveArm);

        int moveDistance = 1440;
        if (throwBall)
        {
            int newThrowDistance = Thrower.getCurrentPosition() + moveDistance;

            Thrower.setTargetPosition(newThrowDistance);
            Thrower.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            Thrower.setPower(throwSpeed);
        } else if (pickUpBall)
        {
            int newPickUpPosition = Thrower.getCurrentPosition() - moveDistance;

            Thrower.setTargetPosition(newPickUpPosition);
            Thrower.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            Thrower.setPower(pickUpSpeed);
        } else
        {
            Thrower.setPower(0);
        }
    }
}
