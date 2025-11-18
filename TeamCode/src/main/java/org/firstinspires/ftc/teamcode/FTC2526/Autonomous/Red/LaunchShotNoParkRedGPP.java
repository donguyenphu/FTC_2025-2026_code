package org.firstinspires.ftc.teamcode.FTC2526.Autonomous.Red;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Rotation2d;
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

@Autonomous(name="Launch Shot Red: GGP (without to human player)")
public class LaunchShotNoParkRedGPP extends LinearOpMode {
    private DcMotorEx shooter;
    private DcMotorEx intake;
    private Servo rotator;
    private Servo passer;
    private ColorSensor detector;
    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d startPose = new Pose2d(-61.00, 11.18, Math.toRadians(90.00));
        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);
        shooter = hardwareMap.get(DcMotorEx.class, "shooter");
        intake = hardwareMap.get(DcMotorEx.class, "intake");
        rotator = hardwareMap.get(Servo.class, "rotator");
        detector = hardwareMap.get(ColorSensor.class, "detector");
        passer = hardwareMap.get(Servo.class, "passer");
        // to use custom PID
        shooter.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rotator.setPosition(5.0/18.0); /// -40
        shooterOneMotor customPIDshooter = new shooterOneMotor(1, 1, 1, 2400, shooter);
        shooterOneMotor customPIDintake = new shooterOneMotor(1, 1, 1, 2400, intake);
        sorterArtifacts sorterSystem = new sorterArtifacts(rotator, detector, passer, 21);
        drive.updatePoseEstimate();
        waitForStart();
        if (isStopRequested()) return;
        //--------------------------------------MAIN-CODE-------------------------------------//
        // first turn go + turn to glance the ID
        TrajectoryActionBuilder tab1 = drive.actionBuilder(startPose)
                .strafeToLinearHeading(new Vector2d(-23.93, 24.14), new Rotation2d(-135.00, 135.00))
                .afterTime(0, customPIDshooter.setPIDVelocityAction());
        // strafe to take first row + go to shooting point
        TrajectoryActionBuilder tab2 = drive.actionBuilder(startPose)
                .splineToLinearHeading(new Pose2d(-12.12, 32.43, Math.toRadians(90.00)), Math.toRadians(260.00))
                .afterTime(0, customPIDintake.setPIDVelocityAction())
                .strafeTo(new Vector2d(-12.12,56.25))
                .afterTime(0, sorterSystem.sensingAction())
                .strafeTo(new Vector2d(-11.60, 11.81))
                .afterTime(0, customPIDintake.setInitialPowerAction());
        // strafe to take the second row + go to shooting point
        TrajectoryActionBuilder tab3 = drive.actionBuilder(startPose)
                .strafeTo(new Vector2d(11.71, 32.43))
                .afterTime(0, customPIDintake.setPIDVelocityAction())
                .strafeTo(new Vector2d(12.12, 56.00))
                .afterTime(0, sorterSystem.sensingAction())
                .strafeTo(new Vector2d(-11.60, 11.81))
                .afterTime(0, customPIDintake.setInitialPowerAction());
        // strafe to take the third row + go to shooting point
        TrajectoryActionBuilder tab4 = drive.actionBuilder(startPose)
                .strafeTo(new Vector2d(35.95, 32.43))
                .afterTime(0, customPIDintake.setPIDVelocityAction())
                .strafeTo(new Vector2d(35.95, 56.00))
                .afterTime(0, sorterSystem.sensingAction())
                .strafeToLinearHeading(new Vector2d(-11.60, 11.81), new Rotation2d(-135.00, 135.00))
                .afterTime(0, customPIDintake.setInitialPowerAction());
        Actions.runBlocking(
                new SequentialAction(
                        tab1.build(),
                        sorterSystem.processMotifAction(),
                        tab2.build(),
                        sorterSystem.processMotifAction(),
                        tab3.build(),
                        sorterSystem.processMotifAction(),
                        tab4.build(),
                        sorterSystem.processMotifAction()
                )
        );
    }
}
