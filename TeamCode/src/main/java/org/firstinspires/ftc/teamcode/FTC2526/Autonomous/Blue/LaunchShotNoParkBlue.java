package org.firstinspires.ftc.teamcode.FTC2526.Autonomous.Blue;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.FTC2526.Utils.shooterOneMotor;
import org.firstinspires.ftc.teamcode.FTC2526.Utils.sorterArtifacts;
import org.firstinspires.ftc.teamcode.FTC2526.Utils.waitSleep;
import org.firstinspires.ftc.teamcode.MecanumDrive;

@Autonomous(name="LaunchShotNoParkBlue")
public class LaunchShotNoParkBlue extends LinearOpMode {
    private DcMotorEx shooter;
    private DcMotorEx intake;
    private Servo rotator;
    private ColorSensor detector;
    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d startPose = new Pose2d(-61.00, -11.18, Math.toRadians(180.00));
        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);
        shooter = hardwareMap.get(DcMotorEx.class, "shooter");
        intake = hardwareMap.get(DcMotorEx.class, "intake");
        rotator = hardwareMap.get(Servo.class, "rotator");
        detector = hardwareMap.get(ColorSensor.class, "detector");
        // to use custom PID
        shooter.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rotator.setPosition(5.0/18.0); /// -40
        shooterOneMotor customPIDshooter = new shooterOneMotor(1, 1, 1, 2400, shooter);
        shooterOneMotor customPIDintake = new shooterOneMotor(1, 1, 1, 2400, intake);
        sorterArtifacts sorterSystem = new sorterArtifacts(rotator, detector);
        drive.updatePoseEstimate();
        waitForStart();
        if (isStopRequested()) return;
        //--------------------------------------MAIN-CODE-------------------------------------//
        // first turn go + turn to glance the ID
        TrajectoryActionBuilder tab1 = drive.actionBuilder(startPose)
                .strafeTo(new Vector2d(-23.93, -24.14))
                .turnTo(Math.toRadians(145.00));
        // scan the ID
        // strafe to take first row + go to shooting point
        TrajectoryActionBuilder tab2 = drive.actionBuilder(startPose)
                .splineToLinearHeading(new Pose2d(-12.12, -32.43, Math.toRadians(-90.00)), Math.toRadians(268.55))
                .afterTime(0, customPIDintake.setPIDVelocityAction())
                .splineToLinearHeading(new Pose2d(-11.91, -56.25, Math.toRadians(-90.00)), Math.toRadians(270.00))
                .strafeTo(new Vector2d(-11.60, -11.81))
                .afterTime(0, customPIDintake.setInitialPowerAction());
        // second turn
        TrajectoryActionBuilder tab3 = drive.actionBuilder(startPose)
                .splineToLinearHeading(new Pose2d(12.00, 24.00, Math.toRadians(90.00)), Math.toRadians(-90.00))
                .strafeTo(new Vector2d(12.00, 50.00))
                .strafeTo(new Vector2d(-25.00, 25.00))
                .turn(Math.toRadians(45.00));
        // third turn
        TrajectoryActionBuilder tab4 = drive.actionBuilder(startPose)
                .splineToLinearHeading(new Pose2d(36.18, 24.00, Math.toRadians(90.00)), Math.toRadians(-90.00))
                .strafeTo(new Vector2d(36.18, 50.00))
                .strafeTo(new Vector2d(-25.00, 25.00))
                .turn(Math.toRadians(45.00));
        Actions.runBlocking(
                new SequentialAction(
                        customPIDshooter.setPIDVelocityAction(),
                        new waitSleep(2000),
                        tab1.build(),
                        customPIDintake.setPIDVelocityAction(),
                        tab2.build(),
                        new waitSleep(2000),
                        tab3.build(),
                        new waitSleep(2000),
                        tab4.build(),
                        new waitSleep(2000)
                )
        );
    }
}
