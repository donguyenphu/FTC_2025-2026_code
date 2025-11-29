package org.firstinspires.ftc.teamcode.FTC2526.Test;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="Robot Centric Test")
public class RobotCentricTest extends LinearOpMode {
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;
    @Override
    public void runOpMode() throws InterruptedException {
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        waitForStart();
        if (isStopRequested()) return;
        //--------------------------------------MAIN-CODE-------------------------------------//
        while (opModeIsActive()) {
            double forward = -gamepad1.left_stick_y;
            double strafe = gamepad1.left_stick_x;
            double rotate = gamepad1.right_stick_x;

            // Here you would typically set the motor powers based on the calculated values
            // For example:
            frontLeft.setPower(forward + strafe + rotate);
            frontRight.setPower(forward - strafe - rotate);
            backLeft.setPower(forward - strafe + rotate);
            backRight.setPower(forward + strafe - rotate);

            // For testing purposes, you can print the values to telemetry
            telemetry.addData("Forward", forward);
            telemetry.addData("Strafe", strafe);
            telemetry.addData("Rotate", rotate);
            telemetry.update();
        }
    }
}
