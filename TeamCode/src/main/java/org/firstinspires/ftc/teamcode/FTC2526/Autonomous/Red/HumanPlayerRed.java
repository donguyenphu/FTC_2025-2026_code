package org.firstinspires.ftc.teamcode.FTC2526.Autonomous.Red;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Rotation2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.FTC2526.Utils.shooterOneMotor;
import org.firstinspires.ftc.teamcode.FTC2526.Utils.sorterArtifacts;
import org.firstinspires.ftc.teamcode.MecanumDrive;

@Autonomous(name="Human Player Red")
public class HumanPlayerRed extends LinearOpMode {
    private DcMotorEx shooter;
    private DcMotorEx intake;
    private Servo rotator;
    private Servo passer;
    private Servo modify;
    private CRServo turret;
    private ColorSensor detector;
    private WebcamName webcamName;
    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d startPose = new Pose2d(62.05, 11.71, Math.toRadians(180.00));
        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);
        shooter = hardwareMap.get(DcMotorEx.class, "shooter");
        intake = hardwareMap.get(DcMotorEx.class, "intake");
        rotator = hardwareMap.get(Servo.class, "rotator");
        detector = hardwareMap.get(ColorSensor.class, "detector");
        passer = hardwareMap.get(Servo.class, "passer");
        turret = hardwareMap.get(CRServo.class, "turret");
        modify = hardwareMap.get(Servo.class, "modify");
        webcamName = hardwareMap.get(WebcamName.class, "Webcam 1");
        // to use custom PID
        shooter.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rotator.setPosition(5.0/18.0); /// -40
        shooterOneMotor customPIDshooter = new shooterOneMotor(1, 1, 1, 2400, shooter, 2400);
        shooterOneMotor customPIDintake = new shooterOneMotor(1, 1, 1, 2400, intake, 2000);
        sorterArtifacts sorterSystem = new sorterArtifacts(modify, rotator, detector, passer, turret, -1, 24, webcamName);
        drive.updatePoseEstimate();
        waitForStart();
        if (isStopRequested()) return;
        //--------------------------------------MAIN-CODE-------------------------------------//
        // first turn go + turn to glance the ID (camera calib and scan)
        TrajectoryActionBuilder tab1 = drive.actionBuilder(startPose)
                .strafeTo(new Vector2d(36.68, 11.81))
                .afterTime(0, sorterSystem.trackTagIDAction())
                .afterTime(0, sorterSystem.updateDetectionAction())
                .afterTime(0, customPIDshooter.setPIDVelocityAction());
        // strafe to take third row + go to shooting point
        TrajectoryActionBuilder tab2 = drive.actionBuilder(startPose)
                .turnTo(Math.toRadians(90.00))
                .strafeTo(new Vector2d(36.26, 59.46))
                .afterTime(0, customPIDintake.setPIDVelocityAction())
                .afterTime(0, sorterSystem.sensingAction())
                .strafeToLinearHeading(new Vector2d(36.89, 11.81), new Rotation2d(-45.00, 45.00))
                .afterTime(0, customPIDintake.setInitialPowerAction());
        // strafe to take the loading row + go to shooting point
        TrajectoryActionBuilder tab3 = drive.actionBuilder(startPose)
                .splineToLinearHeading(new Pose2d(35.63, 64.69, Math.toRadians(0.00)), Math.toRadians(97.79))
                .strafeTo(new Vector2d(64.06, 64.48))
                .afterTime(0, customPIDintake.setPIDVelocityAction())
                .afterTime(0, sorterSystem.sensingAction())
                .strafeToLinearHeading(new Vector2d(36.89, 11.81), new Rotation2d(-45.00, 45.00))
                .afterTime(0, customPIDintake.setInitialPowerAction());
        // stop shooter
        TrajectoryActionBuilder tab4 = drive.actionBuilder(startPose)
                .turnTo(Math.toRadians(-90.00))
                .strafeTo(new Vector2d(2.00, 55.00))
                .afterTime(0, customPIDshooter.setInitialPowerAction());
        Actions.runBlocking(
                new SequentialAction(
                        tab1.build(),
                        sorterSystem.processMotifAction(),
                        tab2.build(),
                        sorterSystem.processMotifAction(),
                        tab3.build(),
                        sorterSystem.processMotifAction(),
                        tab4.build()
                )
        );
    }
}
