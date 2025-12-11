package org.firstinspires.ftc.teamcode.FTC2526.Test;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.FTC2526.Utils.shooterOneMotor;
import org.firstinspires.ftc.teamcode.FTC2526.Utils.sorterArtifacts;

// integrate also the modify angle servo to see how it affects the velocity?
@TeleOp(name = "Fixed Shooter Velocity PID Test")
@Config
public class testPIDClassShooter extends LinearOpMode {
    // params for 1500 velocity
    public static double kP = 0.0;
    public static double kI = 0.0;
    public static double kD = 0.0;

    public static double targetVelocity = 2400; // desired fixed velocity

    private DcMotorEx motor;
    private Servo modify;
    private FtcDashboard dashboard;
    private WebcamName webcamName;
    private Servo sorter;
    private ColorSensor colorSensor;
    private Servo passer;
    private CRServo turret;

    @Override
    public void runOpMode() {
        motor = hardwareMap.get(DcMotorEx.class, "shooter");
        modify = hardwareMap.get(Servo.class, "modify");
        sorter = hardwareMap.get(Servo.class, "sorter");
        colorSensor = hardwareMap.get(ColorSensor.class, "detector;");
        passer = hardwareMap.get(Servo.class, "passer");
        turret = hardwareMap.get(CRServo.class, "turret");
        webcamName = hardwareMap.get(WebcamName.class, "webcamName");

        motor.setDirection(DcMotorSimple.Direction.FORWARD);
        motor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        // take randomly 21 motif id
        sorterArtifacts sorterSystem = new sorterArtifacts(modify, sorter, colorSensor, passer, turret, 21, 20, webcamName);
        dashboard = FtcDashboard.getInstance();

        // Create PID controller ONCE (1800 safety first)
        shooterOneMotor pidController = new shooterOneMotor(kP, kI, kD, targetVelocity, motor, 2400);

        telemetry.addLine("Ready to run fixed velocity test!");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {

            sorterSystem.updateDetection();
            sorterSystem.trackTagID();

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
