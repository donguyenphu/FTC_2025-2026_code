package org.firstinspires.ftc.teamcode.FTC2526.Test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name = "testVelocityMotor")
public class testVelocityMotor extends LinearOpMode {
    private DcMotorEx motorIntake;

    @Override
    public void runOpMode() {
        motorIntake = hardwareMap.get(DcMotorEx.class, "motorIntake");
        motorIntake.setPower(0);
        motorIntake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); /// set to 0
        motorIntake.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER); /// builtin PID
        waitForStart();

        while (opModeIsActive()) {
            motorIntake.setPower(1);
            telemetry.addData("velocity (ticks/sec): ", motorIntake.getVelocity());
            telemetry.addData("Power ([-1,1]): ", motorIntake.getPower());
            telemetry.update();
        }
    }
}
