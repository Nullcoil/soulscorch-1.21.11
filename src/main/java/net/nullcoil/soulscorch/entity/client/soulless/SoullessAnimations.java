package net.nullcoil.soulscorch.entity.client.soulless;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import org.joml.Vector3f;

import java.util.Random;

public class SoullessAnimations {
    private static final Random RANDOM = new Random();
    private static int counter = 0;
    private static int twitchTicksRemaining = 0;

    // In MojMap, Animation is AnimationDefinition
    public static final AnimationDefinition BLANK = AnimationDefinition.Builder.withLength(10f).looping().build();

    public static final AnimationDefinition PASSIVE = AnimationDefinition.Builder.withLength(0.25f)
            .addAnimation("head",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.041676664f, KeyframeAnimations.degreeVec(0f, 0f, 17.5f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.08343333f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("left_arm",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.041676664f, KeyframeAnimations.degreeVec(0f, 0f, -12.5f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.08343333f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("right_arm",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.041676664f, KeyframeAnimations.degreeVec(0f, 0f, -12.5f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.08343333f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR))).build();

    public static final AnimationDefinition NEUTRAL_HEAD_TWITCH0 = AnimationDefinition.Builder.withLength(0.08343333f)
            .addAnimation("head",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.041676664f, KeyframeAnimations.degreeVec(0f, 0f, 17.5f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.08343333f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR))).build();

    public static final AnimationDefinition NEUTRAL_HEAD_TWITCH1 = AnimationDefinition.Builder.withLength(0.125f)
            .addAnimation("head",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.041676664f, KeyframeAnimations.degreeVec(5f, -22.5f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.08343333f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.125f, KeyframeAnimations.degreeVec(5f, -22.5f, 0f),
                                    AnimationChannel.Interpolations.LINEAR))).build();

    public static final AnimationDefinition NEUTRAL_ARM_TWITCH = AnimationDefinition.Builder.withLength(0.16766666f)
            .addAnimation("left_arm",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.041676664f, KeyframeAnimations.degreeVec(-10f, -22.5f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.08343333f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.125f, KeyframeAnimations.degreeVec(-10f, -22.5f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.16766666f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR))).build();

    private static final AnimationDefinition[] NEUTRAL_ANIMATIONS = new AnimationDefinition[]{
            NEUTRAL_HEAD_TWITCH0,
            NEUTRAL_HEAD_TWITCH1,
            NEUTRAL_ARM_TWITCH
    };

    public static AnimationDefinition PASSIVE() {
        if (twitchTicksRemaining > 0) {
            twitchTicksRemaining--;
            return PASSIVE;
        }

        float r = RANDOM.nextFloat();
        if (counter >= 600) {
            counter = -RANDOM.nextInt(1200);
            if (r <= 0.2f) {
                twitchTicksRemaining = 5;
                return PASSIVE;
            }
        }
        counter++;
        return BLANK;
    }

    private static AnimationDefinition lastPlayed = BLANK;

    public static AnimationDefinition NEUTRAL() {
        if (twitchTicksRemaining > 0) {
            twitchTicksRemaining--;
            return lastPlayed;
        }

        float r = RANDOM.nextFloat();
        if (counter >= 600) {
            counter = -RANDOM.nextInt(1200);
            if (r <= 0.2f) {
                lastPlayed = NEUTRAL_ANIMATIONS[RANDOM.nextInt(NEUTRAL_ANIMATIONS.length)];
                twitchTicksRemaining = (int) (lastPlayed.lengthInSeconds() * 20);

                return lastPlayed;
            }
        }
        counter++;
        return BLANK;
    }
}