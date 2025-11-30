package org.firstinspires.ftc.teamcode.FTC2526.Utils;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class shooterOneMotor {
    private DcMotorEx shooter1;
    public double kP, kI, kD;
    public double desiredVelocity;
    private double integral = 0;
    private double errorLast = 0;
    private long timeLast = 0;

    public shooterOneMotor(double kP, double kI, double kD, double fixedVelocity, DcMotorEx shooter) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.desiredVelocity = fixedVelocity;
        this.shooter1 = shooter;
        setToZero();
        timeLast = System.nanoTime();
    }

    /** Call this every loop; non-blocking PID calculation */
    public void calculatePID() {
        long timeNow = System.nanoTime();
        double dt = (timeNow - timeLast) / 1e9; // seconds
        double error = desiredVelocity - shooter1.getVelocity();

        integral += error * dt;
        double derivative = (error - errorLast) / dt;

        double output = kP * error + kI * integral + kD * derivative;

        // Clip output to motor range [-2400, 2400]
        output = Math.max(-2400, Math.min(2400, output));

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
        shooter1.setVelocity(value);
    }

    public void setSpecificVelocity(double value) {
        shooter1.setVelocity(value);
    }

    public void setToZero() {
        shooter1.setPower(0);
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
        shooter1.setPower(1);
    }

    public void setMaxReversePower() {
        shooter1.setPower(-1);
    }

}
