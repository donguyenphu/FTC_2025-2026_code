package org.firstinspires.ftc.teamcode.FTC2526.Test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="testOneShooter")
public class testOneShooter extends LinearOpMode {
    private DcMotor motorShooter;

    @Override
    public void runOpMode() {
        motorShooter = hardwareMap.get(DcMotor.class, "motorShooter");
        motorShooter.setPower(0);

        waitForStart();

        while (opModeIsActive()) {
            motorShooter.setPower(1);
        }
    }
}
