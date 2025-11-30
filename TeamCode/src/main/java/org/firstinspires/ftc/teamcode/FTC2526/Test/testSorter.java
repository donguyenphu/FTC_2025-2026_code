package org.firstinspires.ftc.teamcode.FTC2526.Test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name="testSorter")
public class testSorter extends LinearOpMode {
    private Servo sorter;
    @Override
    public void runOpMode() throws InterruptedException {
        sorter = hardwareMap.get(Servo.class, "sorter");

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.a) {
                sorter.setPosition(0.0/180.0);
            }
            else if (gamepad1.b) {
                sorter.setPosition(15.0/180.0);
            }
            else if (gamepad1.x) {
                sorter.setPosition(30.0/180.0);
            }
        }
    }
}
