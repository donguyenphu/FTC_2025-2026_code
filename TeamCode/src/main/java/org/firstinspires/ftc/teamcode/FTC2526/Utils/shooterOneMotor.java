package org.firstinspires.ftc.teamcode.FTC2526.Utils;


import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class shooterOneMotor {
    private DcMotorEx shooter1;
    private double kP, kI, kD;
    private double desiredVelocity;
    double integral = 0;
    double errorLast = 0;
    double timeNow = 0;
    double timeLast = 0;
    public shooterOneMotor(double kP, double kI, double kD, double fixedVelocity, DcMotorEx shooter) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.desiredVelocity = fixedVelocity;
        this.shooter1 = shooter;
        setToZero();
    }
    /// set PID starts
    public void calculatePID() {
        while (abs(desiredVelocity - this.shooter1.getVelocity()) > 0.01) {
            double errorNow = abs(this.desiredVelocity - this.shooter1.getVelocity()); /// p
            integral += errorNow;
            timeNow = System.nanoTime();
            double derivative = (errorLast - errorNow) / ((timeNow - timeLast) / 1e9);
            double output = this.kP * errorNow + this.kI * integral + this.kD * derivative;
            double minLim = min(0,this.desiredVelocity);
            double maxLim = max(0,this.desiredVelocity);
            output = min(maxLim, max(minLim, output)); /// [0,1]
            setSpecificVelocity(output);
            timeLast = timeNow;
            errorLast = errorNow;
        }
    }
    public class setPIDVelocity implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            calculatePID();
            return false;
        }
    }
    public Action setPIDVelocityAction() {
        return new setPIDVelocity();
    }
    /// set PID ends
    private double getVelocity() {
        return this.shooter1.getVelocity();
    }
    private double getPower() { return this.shooter1.getPower(); }
    public void setSpecificPower(double value) {
        this.shooter1.setPower(value);
    }
    public void setSpecificVelocity(double value) { this.shooter1.setVelocity(value); }
    /// set 0 starts
    public void setToZero() {
        this.shooter1.setPower(0);
    }
    public class setInitialPower implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            setToZero();
            return false;
        }
    }
    public Action setInitialPowerAction() {
        return new setInitialPower();
    }
    /// set 0 ends
    public void setMaxForwardPower() {
        this.shooter1.setPower(1);
    }
    public void setMaxReversePower() {
        this.shooter1.setPower(-1);
    }
}
