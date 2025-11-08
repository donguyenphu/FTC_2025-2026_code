package org.firstinspires.ftc.teamcode.FTC2526.Test;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.acmerobotics.dashboard.config.Config;
import org.firstinspires.ftc.teamcode.FTC2526.Utils.shooterOneMotor;

@TeleOp(name = "Fixed Velocity PID Test")
@Config
public class testPIDClass extends LinearOpMode {

    // Adjustable via FTC Dashboard
    public static double kP = 118.8;
    public static double kI = 792.0;
    public static double kD = 4.455;

    // Fixed target velocity in ticks/sec
    public static double targetVelocity = 1500;

    private DcMotorEx motor;
    private FtcDashboard dashboard;

    @Override
    public void runOpMode() {

        motor = hardwareMap.get(DcMotorEx.class, "intake");
        motor.setDirection(DcMotorSimple.Direction.FORWARD);
        motor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        dashboard = FtcDashboard.getInstance();

        telemetry.addLine("Ready to run fixed velocity test!");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {

            // Update PID coefficients from dashboard
            shooterOneMotor customPIDmotor = new shooterOneMotor(kP, kI, kD, targetVelocity, motor);
            customPIDmotor.calculatePID();

            telemetry.addData("Target Velocity", targetVelocity);
            telemetry.addData("Actual Velocity", motor.getVelocity());
            telemetry.update();
        }
    }
}
