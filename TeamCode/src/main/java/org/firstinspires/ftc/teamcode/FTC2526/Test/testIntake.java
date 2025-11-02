package org.firstinspires.ftc.teamcode.FTC2526.Test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="testIntake")
public class testIntake extends LinearOpMode {
    private DcMotor leftFront;

    @Override
    public void runOpMode() {
        leftFront = hardwareMap.get(DcMotor.class, "motorIntake");
        leftFront.setPower(0);

        waitForStart();

        while (opModeIsActive()) {
            leftFront.setPower(1);
        }
    }
}
