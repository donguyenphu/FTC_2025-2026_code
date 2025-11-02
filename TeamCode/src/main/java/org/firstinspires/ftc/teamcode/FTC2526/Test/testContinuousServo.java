package org.firstinspires.ftc.teamcode.FTC2526.Test;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;


@Autonomous(name="testContinuousServo")
public class testContinuousServo extends LinearOpMode {
    private CRServo servo0;

    @Override
    public void runOpMode() {
        servo0 = hardwareMap.get(CRServo.class, "servo0");


        waitForStart();

        while (opModeIsActive()) {
            servo0.setPower(1);
            telemetry.addData("power", servo0.getPower());
            telemetry.update();
        }
    }
}
