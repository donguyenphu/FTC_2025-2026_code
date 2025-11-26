package org.firstinspires.ftc.teamcode.FTC2526.Test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;


@TeleOp(name="testColorSensor")
public class testColorSensor extends LinearOpMode {
    ColorSensor colorSensor;
    @Override
    public void runOpMode() throws InterruptedException {
        colorSensor = hardwareMap.get(ColorSensor.class, "colorSensor");
        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Red: ", colorSensor.red());
            telemetry.addData("Green: ", colorSensor.green());
            telemetry.addData("Blue: ", colorSensor.blue());
            telemetry.addData("Alpha: ", colorSensor.alpha());
            telemetry.update();
        }
    }
}
