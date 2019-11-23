package org.firstinspires.ftc.teamcode;

/**
 * Created by vineelavandanapu on 11/20/17.
 *
 * makes the robot rotate the until it reaches a desired angle (you have to set the speed and
 * use the correct negatives. a pain ikr)
 */

public class AngleRotate implements AutonStep {
    public double speed; //speed of the robot
    private float endAngle; //angle the robot should go to
    public boolean enabled;

    WheelSpeeds wheelSpeeds = new WheelSpeeds();

    public final double INITIAL_RANGE = 5.0;

    public double range = INITIAL_RANGE;

    @Override
    public String name() {
        return "Rotate";
    }

    AngleRotate(float angle, double speed){
        this(angle, speed, false);
    }

    AngleRotate(float angle, double speed, boolean enabled){
        this.speed = speed; //set the speed to the given speed
        this.enabled = enabled;
        this.setEndAngle(angle);
    }

    public void setEndAngle(float angle) {
        if(angle > 180) {
            this.endAngle = angle; //set angle to given angle
        } else {
            this.endAngle = angle + 360;
        }
    }

    @Override
    public void start(Team7593OpMode opmode) {
        wheelSpeeds = WheelSpeeds.mecanumDrive(0.0, 0.0, speed, false);
    }

    @Override
    public void loop(Team7593OpMode opmode) {

        opmode.robot.motorFrontLeft.setPower(wheelSpeeds.v_lf);
        opmode.robot.motorFrontRight.setPower(wheelSpeeds.v_rf);
        opmode.robot.motorRearLeft.setPower(wheelSpeeds.v_lr);
        opmode.robot.motorRearRight.setPower(wheelSpeeds.v_rr);

    }

    @Override
    public boolean isDone(Team7593OpMode opmode) {
        float currentAngle;

        //condition to normalize the negative angles to be 180-360 so the entire thing will be
        currentAngle = opmode.robot.getCurrentAngle();
        if(currentAngle <= 180) {
            currentAngle += 360;
        }

        if(endAngle - range < currentAngle && currentAngle < endAngle+range){
            opmode.robot.motorFrontLeft.setPower(0);
            opmode.robot.motorFrontRight.setPower(0);
            opmode.robot.motorRearLeft.setPower(0);
            opmode.robot.motorRearRight.setPower(0);

            if(enabled == false) {
                return true;
            }

            if (range == INITIAL_RANGE && Math.abs(endAngle - currentAngle) > 1) {
                range = 1;
                // Change the speed depending on the condition to minimum and direction
                if(currentAngle > endAngle){
                    speed = 0.2;
                }else{
                    speed = -0.2;
                }
                opmode.telemetry.log().add("-------");
                opmode.telemetry.log().add("current angle: " + currentAngle);
                opmode.telemetry.log().add("end angle: " + endAngle);
                opmode.telemetry.log().add("current speed: " + speed);
                opmode.telemetry.log().add("-------");
                return false;
            } else {
                return true;
            }
        }else{
            if(range == 1) {
                if(currentAngle > endAngle){
                    speed = 0.2;
                }else{
                    speed = -0.2;
                }
            }
            return false;
        }
    }

    @Override
    public void updateTelemetry(Team7593OpMode opmode) {
        //set telemetry
        opmode.telemetry.addData("Front Left", wheelSpeeds.v_lf);
        opmode.telemetry.addData("Right Front", wheelSpeeds.v_rf);
        opmode.telemetry.addData("Left Rear", wheelSpeeds.v_lr);
        opmode.telemetry.addData("Right Rear", wheelSpeeds.v_rr);
        opmode.telemetry.addData("target angle", endAngle);
        opmode.telemetry.addData("current angle", opmode.robot.getCurrentAngle());
        opmode.telemetry.addData("speed", speed);

    }

}
