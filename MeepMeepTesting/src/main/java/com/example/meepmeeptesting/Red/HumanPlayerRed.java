package com.example.meepmeeptesting.Red;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Rotation2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class HumanPlayerRed {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(500);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width (depended on your input and your robot's constrainsts)
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();
//        new Pose2d is the initial position
        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(60.92, 12.02, Math.toRadians(90.00)))
                .strafeTo(new Vector2d(60.92, 62.30))
                .strafeTo(new Vector2d(60.92, 12.02))
                .strafeTo(new Vector2d(60.92, 62.30))
                .strafeTo(new Vector2d(60.92, 12.02))
                .strafeTo(new Vector2d(60.92, 62.30))
                .strafeTo(new Vector2d(60.92, 12.02))
                .splineToLinearHeading(new Pose2d(2.00, 55.00, Math.toRadians(-90.00)), -80.00)
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
