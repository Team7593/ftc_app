package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by vineelavandanapu on 10/6/17.
 *
 * MOVE FORWARD AND BACKWARD WHOOOOO (for a specified amount of time)
 */

public class DriveY implements AutonStep {

    public double speed; //speed of the robot
    public ElapsedTime time; //way to see the time
    public double endTime; //time when the movement should end
    public boolean correction = false;

    WheelSpeeds wheelSpeeds;

    public String name() {
        return "DriveY";
    }

    DriveY(double time, double speed){
        this.endTime = time; //set the time to the given time
        this.speed = speed; //set the speed to the given speed
    }

    DriveY(double time, double speed, boolean correction){ //only use for front red and blue
        this(time, speed);
        this.correction = correction;

    }

    @Override
    public void start(Team7593OpMode opmode) {
        time = new ElapsedTime();
        opmode.telemetry.log().add("DriveY Time: " + time.time());
        opmode.telemetry.log().add("Speed is " + speed);
        wheelSpeeds = WheelSpeeds.mecanumDrive(0, speed, 0, false);
//        if(correction){
//            double diff = opmode.robot.voltage.getVoltage()- 0.15 - opmode.v0;
//            //0.15 is because the voltage sensor sees 0.15 more volts than the phones
//            double overshoot = opmode.voltageSlope*diff;
//            double correctionTime = (overshoot/opmode.avgDist)*opmode.calibrationTime;
//            this.endTime = this.endTime - correctionTime;
//        }
    }

    public void loop(Team7593OpMode opmode) {
        //set motor power
        opmode.robot.motorFrontLeft.setPower(wheelSpeeds.v_lf);
        opmode.robot.motorFrontRight.setPower(wheelSpeeds.v_rf);
        opmode.robot.motorRearLeft.setPower(wheelSpeeds.v_lr);
        opmode.robot.motorRearRight.setPower(wheelSpeeds.v_rr);

    }


    public boolean isDone(Team7593OpMode opmode) {
        if(time.time() > endTime ){
            opmode.telemetry.log().add("DriveY Time: " + time.time());

            opmode.robot.motorFrontLeft.setPower(0);
            opmode.robot.motorFrontRight.setPower(0);
            opmode.robot.motorRearLeft.setPower(0);
            opmode.robot.motorRearRight.setPower(0);
            return true;
        }else{
            return false;
        }

    }

    public void updateTelemetry(Team7593OpMode opmode) {
        //set telemetry
        opmode.telemetry.addData("Front Left", wheelSpeeds.v_lf);
        opmode.telemetry.addData("Right Front", wheelSpeeds.v_rf);
        opmode.telemetry.addData("Left Rear", wheelSpeeds.v_lr);
        opmode.telemetry.addData("Right Rear", wheelSpeeds.v_rr);
    }
}