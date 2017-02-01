/*
Copyright (c) 2016 Robert Atkinson

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Robert Atkinson nor the names of his contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESSFOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;

/**
 * This OpMode uses the common Pushbot hardware class to define the devices on the robot.
 * All device access is managed through the HardwarePushbot class.
 * The code is structured as a LinearOpMode
 *
 * This particular OpMode executes a POV Game style Teleop for a PushBot
 * In this mode the left stick moves the robot FWD and back, the Right stick turns left and right.
 * It raises and lowers the claw using the Gampad Y and A buttons respectively.
 * It also opens and closes the claws slowly using the left and right Bumper buttons.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="TeleOp: linear Controls", group="liftbot")

public class liftbotTeleopPOV_Linear extends LinearOpMode {

    /* Declare OpMode members. */
    HardwareLiftBot robot           = new HardwareLiftBot();   // Use a Pushbot's hardware
                                                               // could also use HardwarePushbotMatrix class.

    double input1Y, input1X, input1Z;

    @Override
    public void runOpMode() {

        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            //Update the values of the joystick
            JoyStickVals();

            //method for moving the bottom fork
            MoveFork();

            //set power of the wheels
            robot.frontRight.setPower(Range.clip(-input1Y + input1X - input1Z, -1, 1));
            robot.frontLeft.setPower(Range.clip(-input1Y - input1X + input1Z, -1, 1));
            robot.backRight.setPower(Range.clip(input1Y + input1X + input1Z, -1, 1));
            robot.backLeft.setPower(Range.clip(input1Y - input1X - input1Z, -1, 1));

            // Pause for metronome tick.  40 mS each cycle = update 25 times a second.
            robot.waitForTick(40);
        }
    }

    public void JoyStickVals() {
        //Update Joystick values
        input1Y = Math.pow(-gamepad1.left_stick_y, 2);

        input1X = Math.pow(gamepad1.left_stick_x, 2);

        input1Z = Math.pow(gamepad1.right_stick_x, 2);
    }


    public void MoveFork()
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
            robot.forkServo.setPosition(openPos);
        }
        else if (bButton)
        {
            robot.forkServo.setPosition(closePos);
        }
        else
        {
            telemetry.addData("Say", "Fork servo not moving.");
            telemetry.update();
        }
    }
}
