package org.firstinspires.ftc.teamcode.FTC2526.Test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp(name="test Turret rotating")
public class testCameraTurret extends LinearOpMode {
    private CRServo turretServo;
    @Override
    public void runOpMode() throws InterruptedException {
        turretServo = hardwareMap.get(CRServo.class, "turret");

        waitForStart();

        while (opModeIsActive()) {
            turretServo.setPower(1);
            telemetry.addData("Turret Power", turretServo.getPower());
            telemetry.update();
        }

    }
}
