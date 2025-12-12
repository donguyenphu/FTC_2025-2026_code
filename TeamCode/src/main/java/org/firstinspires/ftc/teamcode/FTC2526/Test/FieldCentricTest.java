package org.firstinspires.ftc.teamcode.FTC2526.Test;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot.LogoFacingDirection;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot.UsbFacingDirection;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

@TeleOp(name = "Field Centric Drive", group = "drive")
public class FieldCentricTest extends LinearOpMode {

    private DcMotor frontLeft, frontRight, backLeft, backRight;
    private IMU imu;

    @Override
    public void runOpMode() throws InterruptedException {

        // Motors
        frontLeft  = hardwareMap.get(DcMotor.class, "leftFront");
        frontRight = hardwareMap.get(DcMotor.class, "rightFront");
        backLeft   = hardwareMap.get(DcMotor.class, "leftBack");
        backRight  = hardwareMap.get(DcMotor.class, "rightBack");

        // Reverse right side motors
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);

        // IMU (Control Hub internal BHI260AP)
        imu = hardwareMap.get(IMU.class, "imu");

        // Your mounting: Logo RIGHT, USB FORWARD
        RevHubOrientationOnRobot orientation = new RevHubOrientationOnRobot(
                LogoFacingDirection.RIGHT,
                UsbFacingDirection.FORWARD
        );

        IMU.Parameters parameters = new IMU.Parameters(orientation);
        imu.initialize(parameters);

        imu.resetYaw();

        waitForStart();

        while (opModeIsActive()) {

            // Joystick
            double y  = -gamepad1.left_stick_y;  // forward is negative
            double x  =  gamepad1.left_stick_x;
            double rx =  gamepad1.right_stick_x;

            // Read IMU yaw
            YawPitchRollAngles angles = imu.getRobotYawPitchRollAngles();

            // 🔥 FIX: INVERT HEADING FOR LOGO RIGHT, USB FORWARD
            double heading = -angles.getYaw(AngleUnit.RADIANS);

            // Field-centric transform
            double rotX = x * Math.cos(heading) - y * Math.sin(heading);
            double rotY = x * Math.sin(heading) + y * Math.cos(heading);

            rotX *= 1.1; // compensate strafing

            // Mecanum drive calculation
            double frontLeftPower  = rotY + rotX + rx;
            double backLeftPower   = rotY - rotX + rx;
            double frontRightPower = rotY - rotX - rx;
            double backRightPower  = rotY + rotX - rx;

            frontLeft.setPower(frontLeftPower);
            backLeft.setPower(backLeftPower);
            frontRight.setPower(frontRightPower);
            backRight.setPower(backRightPower);

            if (gamepad1.a) {
                imu.resetYaw();
            }

            telemetry.addData("Heading (deg)", Math.toDegrees(heading));
            telemetry.update();
        }
    }
}
