package org.firstinspires.ftc.teamcode.FTC2526.Autonomous.Blue.Net;

import com.acmerobotics.roadrunner.ftc.Actions;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;

@Autonomous(name="NetParkBlue")
public class NetParkBlue extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d startPose = new Pose2d(61.00, -12.00, Math.toRadians(180.00));
        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);
        drive.updatePoseEstimate();
        waitForStart();
        if (isStopRequested()) return;
        //--------------------------------------MAIN-CODE-------------------------------------//
        // go to parking area
        TrajectoryActionBuilder tab1 = drive.actionBuilder(startPose)
                .splineToLinearHeading(new Pose2d(38.00, 33.00, Math.toRadians(180.00)), Math.toRadians(116.09));

        Actions.runBlocking(
                new SequentialAction(
                        tab1.build()
                )
        );
    }
}
