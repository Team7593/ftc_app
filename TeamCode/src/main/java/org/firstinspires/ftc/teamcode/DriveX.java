package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by NDHSB-Emma on 9/29/17.
 *
 * STRAFING WHOOOOOO (for a specified amount of time)
 */

public class DriveX implements AutonStep {

    public double speed; //speed of the robot
    private ElapsedTime time; //way to see the time
    public double endTime; //time when the movement of the robot ends

    WheelSpeeds wheelSpeeds;

    //name of the class
    public String name () {
        return "DriveX";
    }

    public DriveX(double time, double speed){
        endTime = time; //set the time to the given time
        this.speed = speed; //set the speed to the given speed
    }

    @Override
    public void start(Team7593OpMode opmode) {
        time = new ElapsedTime();
        opmode.telemetry.log().add("DriveX Time: " + time.time());
        wheelSpeeds = WheelSpeeds.mecanumDrive(speed, 0.0, 0.0, false);
    }

    public void loop (Team7593OpMode opmode){
        //set motor power
        opmode.robot.motorFrontLeft.setPower(wheelSpeeds.v_lf);
        opmode.robot.motorFrontRight.setPower(wheelSpeeds.v_rf);
        opmode.robot.motorRearLeft.setPower(wheelSpeeds.v_lr);
        opmode.robot.motorRearRight.setPower(wheelSpeeds.v_rr);
    }

    public boolean isDone(Team7593OpMode opmode){
        if(time.time() > endTime ){
            opmode.telemetry.log().add("DriveX Time: " + time.time());

            opmode.robot.motorFrontLeft.setPower(0);
            opmode.robot.motorFrontRight.setPower(0);
            opmode.robot.motorRearLeft.setPower(0);
            opmode.robot.motorRearRight.setPower(0);
            return true;
        }else{
            return false;
        }

    }


    public void updateTelemetry (Team7593OpMode opmode){
        //set telemetry
        opmode.telemetry.addData("Front Left", wheelSpeeds.v_lf);
        opmode.telemetry.addData("Right Front", wheelSpeeds.v_rf);
        opmode.telemetry.addData("Left Rear", wheelSpeeds.v_lr);
        opmode.telemetry.addData("Right Rear", wheelSpeeds.v_rr);

    }
}