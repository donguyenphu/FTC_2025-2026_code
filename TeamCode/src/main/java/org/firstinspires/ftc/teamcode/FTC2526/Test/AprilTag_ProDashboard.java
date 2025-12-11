package org.firstinspires.ftc.teamcode.FTC2526.Test;

import android.util.Size;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

@TeleOp(name = "AprilTag_ProDashboard")
public class AprilTag_ProDashboard extends LinearOpMode {

    private VisionPortal portal;
    private AprilTagProcessor tagProcessor;

    @Override
    public void runOpMode() throws InterruptedException {

        // Combined DS + Dashboard telemetry
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        // AprilTag Pipeline
        tagProcessor = new AprilTagProcessor.Builder()
                .setLensIntrinsics(507.774, 507.774, 316.177, 242.265)
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .setDrawTagOutline(true)
                .setDrawTagID(true)
                .build();

        // VisionPortal
        portal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .setCameraResolution(new Size(640, 480))
                .addProcessor(tagProcessor)
                .build();

        // Stream to dashboard
        FtcDashboard.getInstance().startCameraStream(portal, 30);

        telemetry.addLine("AprilTag Pro Mode Ready");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            TelemetryPacket packet = new TelemetryPacket();


            telemetry.addData("FPS", portal.getFps());
            packet.put("FPS", portal.getFps());

            if (tagProcessor.getDetections().isEmpty()) {
                telemetry.addLine("No Tag Detected");
                packet.put("Status", "No tag detected");
            } else {
                // Pick the closest tag (best quality)
                AprilTagDetection tag = getBestDetection(tagProcessor.getDetections());

                telemetry.addData("ID", tag.id);
                packet.put("ID", tag.id);

                // Ensure pose exists
                if (tag.ftcPose != null) {

                    telemetry.addData("X (in)", tag.ftcPose.x);
                    telemetry.addData("Y (in)", tag.ftcPose.y);
                    telemetry.addData("Z (in)", tag.ftcPose.z);

                    double distanceXY = Math.hypot(tag.ftcPose.x, tag.ftcPose.y);

                    telemetry.addData("Distance XY", distanceXY);

                    telemetry.addData("Yaw (deg)", Math.toDegrees(tag.ftcPose.yaw));
                    telemetry.addData("Pitch (deg)", Math.toDegrees(tag.ftcPose.pitch));
                    telemetry.addData("Roll (deg)", Math.toDegrees(tag.ftcPose.roll));

                    packet.put("X", tag.ftcPose.x);
                    packet.put("Y", tag.ftcPose.y);
                    packet.put("Z", tag.ftcPose.z);
                    packet.put("Distance XY", distanceXY);

                    packet.put("Yaw", Math.toDegrees(tag.ftcPose.yaw));
                    packet.put("Pitch", Math.toDegrees(tag.ftcPose.pitch));
                    packet.put("Roll", Math.toDegrees(tag.ftcPose.roll));

                } else {
                    telemetry.addLine("Pose unavailable (ftcPose = null)");
                    packet.put("Status", "Pose unavailable");
                }
            }

            telemetry.update();
            FtcDashboard.getInstance().sendTelemetryPacket(packet);
        }

        FtcDashboard.getInstance().stopCameraStream();
    }

    /** Picks the closest, most stable detection (removes noisy far detections). */
    private AprilTagDetection getBestDetection(java.util.List<AprilTagDetection> detections) {

        AprilTagDetection best = detections.get(0);

        for (AprilTagDetection d : detections) {
            if (d.ftcPose != null && best.ftcPose != null) {
                double distBest = Math.hypot(best.ftcPose.x, best.ftcPose.y);
                double distNow = Math.hypot(d.ftcPose.x, d.ftcPose.y);

                if (distNow < distBest) best = d;
            }
        }

        return best;
    }
}