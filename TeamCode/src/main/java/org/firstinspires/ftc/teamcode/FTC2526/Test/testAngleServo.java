package org.firstinspires.ftc.teamcode.FTC2526.Test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="testAngleServo")
public class testAngleServo extends LinearOpMode {
    Servo leftModifyServo;
    Servo rightModifyServo;
    @Override
    public void runOpMode() {
        leftModifyServo = hardwareMap.get(Servo.class, "leftModifyServo");
        rightModifyServo = hardwareMap.get(Servo.class,"rightModifyServo");

        waitForStart();

        while(opModeIsActive()) {
            if (gamepad1.left_trigger != 0) {
                leftModifyServo.setPosition(gamepad1.left_trigger);
                rightModifyServo.setPosition(-gamepad1.left_trigger);
            }
            else if (gamepad1.right_trigger != 0) {
                leftModifyServo.setPosition(-gamepad1.left_trigger);
                rightModifyServo.setPosition(gamepad1.left_trigger);
            }
        }
    }
}
