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

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;


@TeleOp(name="liftbot: Test", group="liftbot")
public class liftbotTeleopPOV_Linear_v1 extends LinearOpMode {

    /* Declare OpMode members. */
    HardwareLiftBot robot = new HardwareLiftBot();

    double input1X, input1Y, input1Z;

    @Override
    public void runOpMode() {

        // Initialize the hardware variables.
        robot.init(hardwareMap);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            //Update the values of the joystick
            JoyStickVals();

            //method for moving the bottom fork
            MoveFork();

            //method for using the arm
            //Thrower();

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

        //this corresponds to the a button on gamepad 2
        boolean aButton = gamepad2.a;

        //This corresponds to the b button on gamepad 2
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

    /*public void Thrower()
    {
        float armDownButton = -gamepad2.left_trigger;
        float armUpButton = gamepad2.right_trigger;

        robot.Catapulter.setPower(Range.clip(armDownButton + armUpButton, -1, 1));


        if (armDownButton > 0 || armUpButton > 0)
        {
            telemetry.addData("Say", "Arm is moving.");
            telemetry.update();
        }
    }*/
}