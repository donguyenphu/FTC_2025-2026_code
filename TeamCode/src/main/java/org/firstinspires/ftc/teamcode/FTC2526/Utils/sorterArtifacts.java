package org.firstinspires.ftc.teamcode.FTC2526.Utils;

import android.graphics.Color;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;

// Rev Color Sénsor V3 can detect red, green, blue
// -> Purple: high red + high blue + low green
// -> Green: high green + low red + low blue
// -> If Green is max, then it's green
// -> If Blue is max, then it's purple
// -> If Red is max, then it's purple
public class sorterArtifacts {
    private ColorSensor detector;
    private Servo rotator;
    // 1 is green, 2 is purple
    public double[] slot = {0, 0, 0};
    private double gearRatio = 3; // this/other

    public sorterArtifacts(Servo rotator, ColorSensor detector) {
        this.rotator = rotator;
        this.detector = detector;
    }
/// For Purple
    public Action setPurpleAction() {
        return new setPurpleImplements();
    }
    public class setPurpleImplements implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            setPurple();
            return false;
        }
    }
    public void setPurple() {
        for (int inp = 1; inp <= 3; inp++) {
            if (this.slot[inp - 1] == 2) {
                setAngle(inp);
                break;
            }
        }
    }
/// For Green
    public Action setGreenAction() {
        return new setGreenImplements();
    }
    public class setGreenImplements implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            setGreen();
            return false;
        }
    }
    public void setGreen() {
        for (int inp = 1; inp <= 3; inp++) {
            if (this.slot[inp - 1] == 1) {
                setAngle(inp);
                break;
            }
        }
    }
/// For Angle
    public Action setAngleAction(double input) {
        return new setAngleImplements(input);
    }
    public class setAngleImplements implements Action {
        private double inputParams;
        public setAngleImplements(double inputValue) {
            this.inputParams = inputValue;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            setAngle(this.inputParams);
            return false;
        }
    }
    public void setAngle(double input) {
        if (input == 1) this.rotator.setPosition(convertedAngle(-120));
        else if (input == 2) this.rotator.setPosition(convertedAngle(0));
        else if (input == 3) this.rotator.setPosition(convertedAngle(120));
    }
/// Helper Functions
    public double convertedAngle(double degrees) {
        return ((degrees/gearRatio)+90)/180;
    }
}
