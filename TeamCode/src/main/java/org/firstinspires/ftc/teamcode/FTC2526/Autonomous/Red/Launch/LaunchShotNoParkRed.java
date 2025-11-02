package org.firstinspires.ftc.teamcode.FTC2526.Autonomous.Red.Launch;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.FTC2526.Utils.shooterOneMotor;
import org.firstinspires.ftc.teamcode.FTC2526.Utils.waitSleep;
import org.firstinspires.ftc.teamcode.MecanumDrive;

@Autonomous(name="LaunchShotNoParkRed")
public class LaunchShotNoParkRed extends LinearOpMode {
    private DcMotorEx shooter;
    private DcMotorEx intake;
    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d startPose = new Pose2d(-25.00, 25.00, Math.toRadians(135.00));
        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);
        shooter = hardwareMap.get(DcMotorEx.class, "shooter");
        intake = hardwareMap.get(DcMotorEx.class, "intake");
        shooterOneMotor customPIDshooter = new shooterOneMotor(1, 1, 1, 2400, shooter);
        shooterOneMotor customPIDintake = new shooterOneMotor(1, 1, 1, 2400, intake);
        drive.updatePoseEstimate();
        waitForStart();
        if (isStopRequested()) return;
        //--------------------------------------MAIN-CODE-------------------------------------//
        // first turn go
        TrajectoryActionBuilder tab1 = drive.actionBuilder(startPose)
                .splineToLinearHeading(new Pose2d(-12.00, 24.00, Math.toRadians(90.00)), Math.toRadians(-90.00));
        // first turn take
        TrajectoryActionBuilder tab2 = drive.actionBuilder(startPose)
                .strafeTo(new Vector2d(-12.00, 50.00))
                .splineToLinearHeading(new Pose2d(-25.00, 25.00, Math.toRadians(135.00)), Math.toRadians(140.00));
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
