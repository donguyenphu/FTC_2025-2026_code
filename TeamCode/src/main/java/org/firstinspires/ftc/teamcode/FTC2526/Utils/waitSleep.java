package org.firstinspires.ftc.teamcode.FTC2526.Utils;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;

public class waitSleep implements Action {
    private double miliseconds;
    private double startTime = 0;
    public waitSleep(double milisecondsInput) {
        this.miliseconds = milisecondsInput;
    }

    @Override
    public boolean run(@NonNull TelemetryPacket packet) {
        if (startTime == 0) {
            startTime = System.nanoTime();
        }

        // Check elapsed time
        double elapsed = (System.nanoTime() - startTime) / 1e9;
        return elapsed < this.miliseconds;
    }
}

