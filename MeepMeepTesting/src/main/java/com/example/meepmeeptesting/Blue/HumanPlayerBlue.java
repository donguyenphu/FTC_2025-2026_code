package com.example.meepmeeptesting.Blue;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Rotation2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class HumanPlayerBlue {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(500);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width (depended on your input and your robot's constrainsts)
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();
//        new Pose2d is the initial position
        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(62.05, -11.71, Math.toRadians(180.00)))
                .strafeTo(new Vector2d(36.68, -11.81))
                .turnTo(Math.toRadians(-90.00))
                .strafeTo(new Vector2d(36.26, -59.46))
                .strafeToLinearHeading(new Vector2d(36.89, -11.81), new Rotation2d(-135.00, -135.00))
                .turnTo(Math.toRadians(0.00))
                .strafeTo(new Vector2d(35.63, -64.69))
                .strafeTo(new Vector2d(64.06, -64.48))
                .strafeToLinearHeading(new Vector2d(36.89, -11.81), new Rotation2d(-135.00, -135.00))
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
