package org.firstinspires.ftc.teamcode.FTC2526.Test;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.ImuOrientationOnRobot;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

@TeleOp(name = "Field Centric Test")
public class FieldCentricTest extends LinearOpMode {

    DcMotor fl, fr, bl, br;
    BNO055IMU imu;

    @Override
    public void runOpMode() throws InterruptedException {

        // DRIVE MOTORS
        fl = hardwareMap.dcMotor.get("frontLeft");
        fr = hardwareMap.dcMotor.get("frontRight");
        bl = hardwareMap.dcMotor.get("backLeft");
        br = hardwareMap.dcMotor.get("backRight");

        fr.setDirection(DcMotorSimple.Direction.REVERSE);
        br.setDirection(DcMotorSimple.Direction.REVERSE);

        // IMU INITIALIZATION
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        parameters.loggingEnabled = true;
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        imu.initialize(parameters);

        telemetry.addLine("READY");
        telemetry.update();

        waitForStart();


        while (opModeIsActive()) {

            // ---- READ CONTROLS ----
            double y = -gamepad1.left_stick_y;   // Forward is negative
            double x =  gamepad1.left_stick_x;   // Strafe
            double rx = gamepad1.right_stick_x;  // Rotate

            // ---- READ HEADING ----
            double botHeading = imu.getAngularOrientation().firstAngle;

            // ---- FIELD CENTRIC ROTATION ----
            double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
            double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

            // ---- MOTOR POWERS ----
            double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);

            fl.setPower((rotY + rotX + rx) / denominator);
            bl.setPower((rotY - rotX + rx) / denominator);
            fr.setPower((rotY - rotX - rx) / denominator);
            br.setPower((rotY + rotX - rx) / denominator);

            telemetry.addData("Heading (deg)",
                    Math.toDegrees(botHeading));
            telemetry.update();
        }
    }
}
