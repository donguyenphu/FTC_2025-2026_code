package org.firstinspires.ftc.teamcode.FTC2526.Test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name = "test rotate angle servo")
public class testRotateAngleServo extends LinearOpMode {
    public Servo sorter;
    @Override
    public void runOpMode() {
        sorter = hardwareMap.get(Servo.class, "sorter");

        waitForStart();

        while (opModeIsActive()) {
            sorter.setPosition(1);
        }
    }
}
