package com.example.meepmeeptesting.Red;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class LaunchShotNoParkRed {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(500);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width (depended on your input and your robot's constrainsts)
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();
//        new Pose2d is the initial position
        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-61.00, 11.18, Math.toRadians(180.00)))
                .strafeTo(new Vector2d(-23.93, 24.14))
                .turnTo(Math.toRadians(-145.00))
                .splineToLinearHeading(new Pose2d(-12.12, 32.43, Math.toRadians(90.00)), Math.toRadians(268.55))
                .strafeTo(new Vector2d(-12.12, 56.25))
                .strafeTo(new Vector2d(-11.60, 11.81))
                .turn(Math.toRadians(45.00))
                .splineToLinearHeading(new Pose2d(11.71, 32.43, Math.toRadians(90.00)), Math.toRadians(-40.33))
                .strafeTo(new Vector2d(12.12, 62.00))
                .strafeTo(new Vector2d(-11.60, 11.81))
                .turn(Math.toRadians(45.00))
                .splineToLinearHeading(new Pose2d(35.74, 32.43, Math.toRadians(90.00)), Math.toRadians(-22.47))
                .strafeTo(new Vector2d(35.95, 62.00))
                .strafeTo(new Vector2d(-11.60, 11.81))
                .turn(Math.toRadians(45.00))
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
