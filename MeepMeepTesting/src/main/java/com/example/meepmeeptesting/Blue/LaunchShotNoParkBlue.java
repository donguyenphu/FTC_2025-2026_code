package com.example.meepmeeptesting.Blue;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Rotation2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class LaunchShotNoParkBlue {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(500);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width (depended on your input and your robot's constrainsts)
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();
//        new Pose2d is the initial position
        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-61.00, -11.18, Math.toRadians(90.00)))
                .strafeToLinearHeading(new Vector2d(-23.93, -24.14), new Rotation2d(-135.00, -135.00))
                .splineToLinearHeading(new Pose2d(-12.12, -32.43, Math.toRadians(-90.00)), Math.toRadians(260.00))
                .strafeTo(new Vector2d(-12.12, -56.25))
                .strafeTo(new Vector2d(-11.60, -11.81))
                .strafeTo(new Vector2d(11.71, -32.43))
                .strafeTo(new Vector2d(12.12, -56.00))
                .strafeTo(new Vector2d(-11.60, -11.81))
                .strafeTo(new Vector2d(35.95, -32.43))
                .strafeTo(new Vector2d(35.95, -56.00))
                .strafeToLinearHeading(new Vector2d(-11.60, -11.81), new Rotation2d(-135.00, -135.00))
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
