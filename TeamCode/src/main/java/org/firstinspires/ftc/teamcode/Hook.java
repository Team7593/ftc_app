package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Hook implements AutonStep {

    boolean y;
    ElapsedTime time = new ElapsedTime();

    public Hook(boolean yeah){
        boolean y = yeah;
    }

    @Override
    public String name() {
        return "hook";
    }

    @Override
    public void start(Team7593OpMode opmode) {
        time.startTime();
    }

    @Override
    public void loop(Team7593OpMode opmode) {
        if(y){
            opmode.robot.hook.setPosition(1);
        }else{
            opmode.robot.hook.setPosition(.4);
        }
    }

    @Override
    public boolean isDone(Team7593OpMode opmode) {
        if(time.time() > .3){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void updateTelemetry(Team7593OpMode opmode) {

    }
}
