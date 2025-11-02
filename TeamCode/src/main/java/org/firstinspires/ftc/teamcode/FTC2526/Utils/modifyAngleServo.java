package org.firstinspires.ftc.teamcode.FTC2526.Utils;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


public class modifyAngleServo {
    private Servo left;
    private Servo right;
    public modifyAngleServo(HardwareMap hardwareMap) {
        this.left = hardwareMap.get(Servo.class, "leftServo");
        this.right = hardwareMap.get(Servo.class, "rightServo");
        left.setPosition(0);
        right.setPosition(0);
    }
    private void setRange(double min, double max) {
        this.left.scaleRange(min, max);
        this.right.scaleRange(min, max);
    }
    private void setInitialPosition() {
        this.left.setPosition(0);
        this.right.setPosition(0);
    }
    private void setMaxPosition() {
        this.left.setPosition(1);
        this.right.setPosition(-1);
    }
    private void setSpecific(double angle) {
        this.left.setPosition(angle);
        this.right.setPosition(-angle);
    }
}
