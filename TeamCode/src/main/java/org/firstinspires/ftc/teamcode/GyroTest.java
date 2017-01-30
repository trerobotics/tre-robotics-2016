package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;

@Autonomous(name="LiftBot: Gyro Test", group="liftbot")
@Disabled
public abstract class GyroTest extends LinearOpMode {
    HardwareLiftBot     robot   = new HardwareLiftBot();   // Use a liftbot hardware
    private ElapsedTime runtime = new ElapsedTime();



    @Override
    public void runOpMode()
    {
        // initialize hardware map
        robot.init(hardwareMap);

        waitForStart();

        rotateToDegree(90, 1);

        sleep(2000);

        rotateToDegree(90, 1);

        sleep(2000);

        // The negative value in the speed will make the robot spin left, retracing the way it came back.
        rotateToDegree(180, -1);

    }

    // Robot will always rotate right. If you want to rotate left, set the speed to a negative.
    public void rotateToDegree(int targetDegree, int speed)
    {

        GyroSensor gyroSensor;

        gyroSensor = (ModernRoboticsI2cGyro)hardwareMap.gyroSensor.get("gyro");

        //calibrate the gyro
        gyroSensor.calibrate();

        while (!isStopRequested() && gyroSensor.isCalibrating())  {
            sleep(50);
            idle();
        }

        //reset the z axis so the method can be called multiple times independently.
        gyroSensor.resetZAxisIntegrator();

        int gyroX = gyroSensor.rawX();
        int gyroY = gyroSensor.rawY();
        int gyroZ = gyroSensor.rawZ();

        int heading = gyroSensor.getHeading();

        int Y = 0;
        int X = 0;
        int Z;

        if(targetDegree > 180)
        {
            speed = -speed;
        }

        Z = speed;

        while (heading < targetDegree)
        {
            robot.frontRight.setPower(Range.clip(-Y + X - Z, -1, 1));
            robot.frontLeft.setPower(Range.clip(-Y - X + Z, -1, 1));
            robot.backRight.setPower(Range.clip(Y + X + Z, -1, 1));
            robot.backLeft.setPower(Range.clip(Y - X - Z, -1, 1));
            telemetry.addData("Say", "Rotating...");
            telemetry.update();
        }
    }
}