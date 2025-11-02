package org.firstinspires.ftc.teamcode.FTC2526.Test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name="testTwoShooter")
public class testTwoShooter extends LinearOpMode {
    private DcMotorEx shooterLeft;
    private DcMotorEx shooterRight;
    @Override
    public void runOpMode() {
        shooterLeft = hardwareMap.get(DcMotorEx.class, "shooterLeft");
        shooterRight = hardwareMap.get(DcMotorEx.class, "shooterRight");
        shooterLeft.setDirection(DcMotorEx.Direction.REVERSE);
        shooterLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        shooterLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        shooterRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        shooterRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        shooterLeft.setPower(0);
        shooterRight.setPower(0);

        waitForStart();

        while(opModeIsActive()) {
            shooterLeft.setPower(1);
            shooterRight.setPower(1);
            telemetry.addData("left Velo ticks/sec", shooterLeft.getVelocity());
            telemetry.addData("right Velo ticks/sec", shooterRight.getVelocity());
            telemetry.update();
        }
    }
}
