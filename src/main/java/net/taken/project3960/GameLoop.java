package net.taken.project3960;

import javafx.animation.AnimationTimer;

import java.util.function.Consumer;

public class GameLoop extends AnimationTimer {

    private static final float TIME_STEP = 1f / 60;

    private final Consumer<Float> updater;
    private final Runnable renderer;
    private final Consumer<Integer> fpsReporter;

    private long previousTime = 0;
    private float accumulatedTime = 0;

    private float secondsElapsedSinceLastFpsUpdate = 0f;
    private int framesSinceLastFpsUpdate = 0;


    public GameLoop(Consumer<Float> updater, Runnable renderer, Consumer<Integer> fpsReporter) {
        this.updater = updater;
        this.renderer = renderer;
        this.fpsReporter = fpsReporter;
    }

    @Override
    public void handle(long currentTime) {
        if (previousTime == 0) {
            previousTime = currentTime;
            return;
        }

        float secondsElapsed = (currentTime - previousTime) / 1e9f;
        accumulatedTime += secondsElapsed;
        previousTime = currentTime;

        while (accumulatedTime >= TIME_STEP) {
            updater.accept(TIME_STEP);
            accumulatedTime -= TIME_STEP;
        }
        renderer.run();

        secondsElapsedSinceLastFpsUpdate += secondsElapsed;
        framesSinceLastFpsUpdate++;
        if (secondsElapsedSinceLastFpsUpdate >= 0.5f) {
            int fps = Math.round(framesSinceLastFpsUpdate / secondsElapsedSinceLastFpsUpdate);
            fpsReporter.accept(fps);
            secondsElapsedSinceLastFpsUpdate = 0;
            framesSinceLastFpsUpdate = 0;
        }
    }

    @Override
    public void stop() {
        previousTime = 0;
        accumulatedTime = 0;
        secondsElapsedSinceLastFpsUpdate = 0f;
        framesSinceLastFpsUpdate = 0;
        super.stop();
    }

}
