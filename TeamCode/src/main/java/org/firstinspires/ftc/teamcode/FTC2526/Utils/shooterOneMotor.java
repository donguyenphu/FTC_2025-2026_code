package org.firstinspires.ftc.teamcode.FTC2526.Utils;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class shooterOneMotor {
    private DcMotorEx shooter;
    public double kP, kI, kD;
    public double desiredVelocity;
    public double limitVelocity;
    private double integral = 0;
    private double errorLast = 0;
    private long timeLast = 0;

    public shooterOneMotor(double kP, double kI, double kD, double fixedVelocity, DcMotorEx shooter, double limitVelocity) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.desiredVelocity = fixedVelocity;
        this.limitVelocity = limitVelocity;
        this.shooter = shooter;
        setToZero();
        timeLast = System.nanoTime();
    }

    /** Call this every loop; non-blocking PID calculation */
    public void calculatePID() {
        long timeNow = System.nanoTime();
        double dt = (timeNow - timeLast) / 1e9; // seconds
        double error = desiredVelocity - shooter.getVelocity();

        integral += error * dt;
        double derivative = (error - errorLast) / dt;

        double output = kP * error + kI * integral + kD * derivative;

        // Clip output to motor range [-limitVelocity, limitVelocity]
        output = Math.max(-this.limitVelocity, Math.min(this.limitVelocity, output));

        setSpecificVelocity(output);

        errorLast = error;
        timeLast = timeNow;
    }

    public class SetPIDVelocity implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            calculatePID();
            return false; // false so action continues
        }
    }

    public Action setPIDVelocityAction() {
        return new SetPIDVelocity();
    }

    public void setSpecificPower(double value) {
        shooter.setVelocity(value);
    }

    public void setSpecificVelocity(double value) {
        shooter.setVelocity(value);
    }

    public void setToZero() {
        shooter.setPower(0);
    }

    public class SetInitialPower implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            setToZero();
            return false;
        }
    }

    public Action setInitialPowerAction() {
        return new SetInitialPower();
    }

    public void setMaxForwardPower() {
        shooter.setPower(1);
    }

    public void setMaxReversePower() {
        shooter.setPower(-1);
    }

}
