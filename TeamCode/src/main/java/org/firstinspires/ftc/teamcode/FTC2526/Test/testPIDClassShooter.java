package org.firstinspires.ftc.teamcode.FTC2526.Test;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.teamcode.FTC2526.Utils.shooterOneMotor;

@TeleOp(name = "Fixed Shooter Velocity PID Test")
@Config
public class testPIDClassShooter extends LinearOpMode {
    // params for 1500 velocity
    public static double kP = 0.0;
    public static double kI = 0.0;
    public static double kD = 0.0;

    public static double targetVelocity = 2400; // desired fixed velocity

    private DcMotorEx motor;
    private FtcDashboard dashboard;

    @Override
    public void runOpMode() {

        motor = hardwareMap.get(DcMotorEx.class, "shooter");
        motor.setDirection(DcMotorSimple.Direction.FORWARD);
        motor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        dashboard = FtcDashboard.getInstance();

        // Create PID controller ONCE
        shooterOneMotor pidController = new shooterOneMotor(kP, kI, kD, targetVelocity, motor, 2400);

        telemetry.addLine("Ready to run fixed velocity test!");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {

            // Update constants dynamically from Dashboard
            pidController.kP = kP;
            pidController.kI = kI;
            pidController.kD = kD;
            pidController.desiredVelocity = targetVelocity;

            // Compute PID & set motor power
            pidController.calculatePID();

            double actualVelo = motor.getVelocity();

            // Telemetry to Driver Station
            telemetry.addData("Target", targetVelocity);
            telemetry.addData("Actual", actualVelo);
            telemetry.update();

            // Telemetry to Dashboard (GRAPH SUPPORT)
            TelemetryPacket packet = new TelemetryPacket();
            packet.put("targetVelocity", targetVelocity);
            packet.put("actualVelocity", actualVelo);
            dashboard.sendTelemetryPacket(packet);
        }
    }
}
