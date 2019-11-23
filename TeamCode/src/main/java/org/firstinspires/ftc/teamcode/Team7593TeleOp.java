package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import java.util.ArrayList;

@TeleOp (name = "TeleOp")
public class Team7593TeleOp extends Team7593OpMode {

    //Declare Variables
    public ElapsedTime time = new ElapsedTime(); //a timer
    public double lastReadTimer = 0;

    public ElapsedTime t = new ElapsedTime(); //a timer
    public double lastReadT = 0;

    public int currEncoderVal;  //encoder values for tilt
    public int oldEncoderVal;

    public int cEncoderVal;  //encoder values for rightLift
    public int oEncoderVal;

    public int cuEncoderVal; //encoder values for leftLift
    public int olEncoderVal;

    public boolean pressed = false;
    public boolean pushed = false;

    double position = robot.HOME;
    final double SPEED = 0.02; //sets rate to move servo

    Orientation angles; //to use the imu (mostly for telemetry)

    //double extensionPosition = robot.EXTENSION_HOME;
    final double EXTENSION_SPEED = 0.08; //sets rate to move servo

    @Override
    //code block to that will run at the VERY beginning of Teleop
    public ArrayList<AutonStep> createAutonSteps() {
        return null;
    }

    @Override
    public void init(){
        super.init();

        //stop the motor(s) and reset the motor encoders to 0

        /*robot.tilt.setPower(0);
        robot.rightLift.setPower(0);
        robot.leftLift.setPower(0);

        robot.tilt.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.leftLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.tilt.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.leftLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //get the starting encoder value of tilt (this is so we don't assume the starting econder value is 0)
        oldEncoderVal = robot.tilt.getCurrentPosition();
        oEncoderVal = robot.leftLift.getCurrentPosition();
        olEncoderVal = robot.rightLift.getCurrentPosition();*/

        telemetry.addData("Say", "HELLO FROM THE OTHER SIIIIIDE");
        time.startTime();
        t.startTime();
    }

    public void loop() {

        //use super's loop so that auton steps can run
        super.loop();

        //get the current encoder value of tilt
        /*cuEncoderVal = robot.leftLift.getCurrentPosition();
        cEncoderVal = robot.rightLift.getCurrentPosition();
        currEncoderVal = robot.tilt.getCurrentPosition();*/

        double leftX, rightX, leftY, liftStick, tiltStick, tiltPower; //declaration for the game sticks + power
        boolean hook, latch, slowDrive, slowTilt, slowDrive2; //declaration for the buttons/bumpers
        WheelSpeeds speeds; //variable to hold speeds

        leftX = gamepad1.left_stick_x;
        rightX = gamepad1.right_stick_x;
        leftY = gamepad1.left_stick_y;
        slowDrive = gamepad1.left_bumper;
        slowDrive2 = gamepad1.right_bumper;

        liftStick = gamepad2.left_stick_y;
        tiltStick = gamepad2.right_stick_y;
        slowTilt = gamepad2.right_bumper;
        latch = gamepad2.a;
        hook = gamepad2.x;

        tiltPower = .6;


        //get the speeds
        if(slowDrive || slowDrive2){
            speeds = WheelSpeeds.mecanumDrive(leftX, leftY, rightX, true);
        }else{
            speeds = WheelSpeeds.mecanumDrive(leftX, leftY, rightX, false);
        }

        //power the motors
        robot.powerTheWheels(speeds);


        //slow the tilt motor
        if(slowTilt){
            tiltPower = tiltPower/2;
        }

        /*if(liftStick > 0) {
            if (robot.rightLift.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
                robot.rightLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }
            if(robot.leftLift.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
                robot.leftLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }
            robot.rightLift.setPower(liftStick);
            robot.leftLift.setPower(-liftStick);
            cEncoderVal = oEncoderVal;
            cuEncoderVal = olEncoderVal;
        }else if(liftStick < 0) {
            if (robot.rightLift.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
            robot.rightLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
            if(robot.leftLift.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
                robot.leftLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }
            robot.rightLift.setPower(liftStick);
            robot.leftLift.setPower(-liftStick);
            cEncoderVal = oEncoderVal;
            cuEncoderVal = olEncoderVal;

        }else{
            robot.rightLift.setTargetPosition(oEncoderVal);
            robot.leftLift.setTargetPosition(oldEncoderVal);
            if (robot.rightLift.getMode() != DcMotor.RunMode.RUN_TO_POSITION) {
            robot.rightLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
            if (robot.leftLift.getMode() != DcMotor.RunMode.RUN_TO_POSITION) {
                robot.leftLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
            robot.rightLift.setPower(0.1);
            robot.leftLift.setPower(0.1);
        }

        if (tiltStick > 0) {
            if (robot.tilt.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
                robot.tilt.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }
            robot.tilt.setPower(tiltStick);
            oldEncoderVal = currEncoderVal;
        } else if (tiltStick < 0) {
            if (robot.tilt.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
                robot.tilt.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }
            robot.tilt.setPower(tiltStick);
            oldEncoderVal = currEncoderVal;
        } else {
            robot.tilt.setTargetPosition(oldEncoderVal);
            if (robot.tilt.getMode() != DcMotor.RunMode.RUN_TO_POSITION) {
                robot.tilt.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
            robot.tilt.setPower(0.1);
        }
         */

        if (gamepad2.x && t.time() > lastReadT + 0.5) {
            lastReadT = t.time();
            pressed = !pressed;
            if(pressed == false){
                robot.hook.setPosition(.4);
            }else{
                robot.hook.setPosition(1);
            }
        }

        /*if (gamepad2.a && time.time() > lastReadTimer + 0.5) {
            lastReadTimer = time.time();
            pushed = !pushed;
            if (pushed == false){
                robot.latch.setPosition(1);
            }else{
                robot.latch.setPosition(0.6);
            }
        }*/

        //code to turn servo
        if(gamepad2.dpad_up){
            position += SPEED;
            position = Range.clip(position, robot.MIN, robot.MAX);
            robot.hook.setPosition(position);
        }else if(gamepad2.dpad_down){
            position -= SPEED;
            position = Range.clip(position, robot.MIN, robot.MAX);
            robot.hook.setPosition(position);
        }


        //use the imu
        angles = robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);


        /*
        WHOA TELEMETRY
        */
        telemetry.addLine().addData("slow mode: ", slowDrive);


        //angles on each of the axes
        telemetry.addLine().addData("IMU angle Y:", angles.secondAngle);
        telemetry.addData("IMU angle Z:", angles.firstAngle);
        telemetry.addData("IMU angle X:", angles.thirdAngle);

        //angles it's at
        telemetry.addLine();
        telemetry.addData("Current Angle: ", robot.getCurrentAngle());
        telemetry.addData("Init Angle: ", robot.initAngle);

        //gold pos should be either 1,2,3
        telemetry.addData("Gold Mineral Position:", this.getSharedInfo("Gold"));
    }
}