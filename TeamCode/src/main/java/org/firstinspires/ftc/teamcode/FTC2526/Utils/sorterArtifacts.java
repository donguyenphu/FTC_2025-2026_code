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
    private Servo passer;
    private int currentOrder = 1; // current position
    // 1 is green, 2 is purple
    private double[] slot = {-1, 0, 0, 0};  // based on Order intake: green - 1, purple - 2
    private double[] motif = {-1, 0, 0, 0}; // based on AprilTag: green - 1, purple - 2
    private double gearRatio = 3; // this/other

    public sorterArtifacts(Servo rotator, ColorSensor detector, Servo passer, int id) {
        this.rotator = rotator;
        this.detector = detector;
        this.passer = passer;
        if (id == 21) { // GPP
            this.motif[1] = 1;
            this.motif[2] = 2;
            this.motif[3] = 2;
        }
        else if (id == 22) { // PGP
            this.motif[1] = 2;
            this.motif[2] = 1;
            this.motif[3] = 2;
        }
        else if (id == 23) { // PPG
            this.motif[1] = 2;
            this.motif[2] = 2;
            this.motif[3] = 1;
        }
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
    // shift 1 to right if SHOOTER TILT TO RIGHT
    public void setPurple() {
        for (int inp = 1; inp <= 3; inp++) {
            if (this.slot[inp] == 2) {
                if (inp == 3) setAngle(1);
                else setAngle(inp + 1);
                /*
                if (inp == 1) setAngle(3);
                else setAngle(inp - 1);
                 */
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
    // shift 1 to right if SHOOTER TILT TO LEFT
    public void setGreen() {
        for (int inp = 1; inp <= 3; inp++) {
            if (this.slot[inp] == 1) {
                if (inp == 3) setAngle(1);
                else setAngle(inp + 1);
                /*
                if (inp == 1) setAngle(3);
                else setAngle(inp - 1);
                 */
                break;
            }
        }
    }
/// For Angle
    public Action setAngleAction(int input) {
        return new setAngleImplements(input);
    }
    public class setAngleImplements implements Action {
        private int inputParams;
        public setAngleImplements(int inputValue) {
            this.inputParams = inputValue;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            setAngle(this.inputParams);
            return false;
        }
    }
    public void setAngle(int input) {
        if (input == 1) this.rotator.setPosition(convertedAngle(-120));
        else if (input == 2) this.rotator.setPosition(convertedAngle(0));
        else if (input == 3) this.rotator.setPosition(convertedAngle(120));
        this.currentOrder = input;
    }
/// For sensing & changing slot
    public Action sensingAction() {
        return new sensingImplements();
    }
    public class sensingImplements implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            sensing();
            return false;
        }
    }
    public void sensing() {
        int redRGB = detector.red();
        int blueRGB = detector.blue();
        int greenRGB = detector.green();
        // green detected
        if (greenRGB == Math.max(Math.max(redRGB, blueRGB), greenRGB)) {
            this.slot[this.currentOrder] = 1;
        }
        // purple detected
        else {
            this.slot[this.currentOrder] = 2;
        }
        // switch next order (if the end -> turn back to start)
        this.currentOrder++;
        if (this.currentOrder == 4) this.currentOrder = 1;
        setAngle(this.currentOrder);
    }
/// Processing the motif
    public Action processMotifAction() {
        return new processMotifImplements();
    }
    public class processMotifImplements implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            processMotif();
            return false;
        }
    }
    public void processMotif() {
        for (int order = 1; order <= 3; order++) {
            // green
            if (this.motif[order] == 1) {
                setGreen();
            }
            // purple
            else if (this.motif[order] == 2) {
                setPurple();
            }
            this.passer.setPosition(AngleToPower(45));
            this.passer.setPosition(AngleToPower(0));
        }
    }
/// Helper Functions
    public double convertedAngle(double degrees) {
        return ((degrees/gearRatio)+90)/180;
    }
    public double AngleToPower(double degrees) {
        return (degrees + 90)/180;
    }
}
