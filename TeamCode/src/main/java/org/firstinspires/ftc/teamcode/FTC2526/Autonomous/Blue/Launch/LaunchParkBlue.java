package org.firstinspires.ftc.teamcode.FTC2526.Autonomous.Blue.Launch;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;

@Autonomous(name="LaunchParkBlue")
public class LaunchParkBlue extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d startPose = new Pose2d(-61.00, -15.00, Math.toRadians(-180.00));
        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);
        drive.updatePoseEstimate();
        waitForStart();
        if (isStopRequested()) return;
        //--------------------------------------MAIN-CODE-------------------------------------//
        // go to parking area
        TrajectoryActionBuilder tab1 = drive.actionBuilder(startPose)
                .strafeTo(new Vector2d(38.85, 33.67));

        Actions.runBlocking(
                new SequentialAction(
                        tab1.build()
                )
        );
    }
}
