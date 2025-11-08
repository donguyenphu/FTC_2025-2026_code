package org.firstinspires.ftc.teamcode.FTC2526.Test;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

public class testCameraParams extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        AprilTagProcessor tagProcessor = new AprilTagProcessor.Builder()
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .setDrawTagOutline(true)
                .setDrawTagID(true)
                .build();

        VisionPortal portal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .setCameraResolution(new Size(640, 480))
                .addProcessor(tagProcessor)
                .build();


        waitForStart();


        while (isStopRequested() && opModeIsActive()) {

            telemetry.addData("Camera Params:", String.format("Using Webcam: %b, Resolution: %dx%d", true, 640, 480 ));

            if (tagProcessor.getDetections().size() > 0) {
                AprilTagDetection detection = tagProcessor.getDetections().get(0);

                telemetry.addData("ID: ", detection.id);
                telemetry.addData("X to detected tag: ", detection.ftcPose.x);
                telemetry.addData("Y to detected tag: ", detection.ftcPose.y);
                telemetry.addData("Distance: ", Math.sqrt(Math.pow(detection.ftcPose.x, 2.0) + Math.pow(detection.ftcPose.y, 2.0)));
                telemetry.addData("Z to detected tag: ", detection.ftcPose.z);
                telemetry.addData("Yaw: ", Math.toDegrees(detection.ftcPose.yaw));
                telemetry.addData("Pitch: ", Math.toDegrees(detection.ftcPose.pitch));
                telemetry.addData("Roll: ", Math.toDegrees(detection.ftcPose.roll));

            }
            else {
                telemetry.addData("No Tag(s) Detected", "");
            }

            telemetry.update();
        }
    }
}
