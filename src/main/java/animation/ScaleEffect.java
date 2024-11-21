package animation;

import javafx.animation.ScaleTransition;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class ScaleEffect {

    private ScaleEffect() {
        // Prevent instantiation
    }

    public static void scaleTo(Node node, double durationInSeconds, double toX, double toY) {
        ScaleTransition scaleIn = new ScaleTransition(Duration.seconds(durationInSeconds), node);
        scaleIn.setToX(toX);
        scaleIn.setToY(toY);
        scaleIn.play();
    }

    public static void scaleOut(Node node, double durationInSeconds, double fromX, double fromY, double toX, double toY) {
        ScaleTransition scaleOut = new ScaleTransition(Duration.seconds(durationInSeconds), node);
        scaleOut.setFromX(fromX);
        scaleOut.setFromY(fromY);
        scaleOut.setToX(toX);
        scaleOut.setToY(toY);
        scaleOut.play();
    }

    public static void scaleInOut(Node node, double durationInSeconds, double fromX, double toX, double fromY, double toY) {
        ScaleTransition scaleIn = new ScaleTransition(Duration.seconds(durationInSeconds / 2), node);
        scaleIn.setFromX(fromX);
        scaleIn.setToX(toX);
        scaleIn.setFromY(fromY);
        scaleIn.setToY(toY);

        ScaleTransition scaleOut = new ScaleTransition(Duration.seconds(durationInSeconds / 2), node);
        scaleOut.setFromX(toX);
        scaleOut.setToX(fromX);
        scaleOut.setFromY(toY);
        scaleOut.setToY(fromY);

        scaleIn.setOnFinished(event -> scaleOut.play());
        scaleIn.play();
    }

    public static void scaleAndFade(Node node, double durationInSeconds, double toX, double toY) {
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(durationInSeconds / 2), node);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(durationInSeconds / 2), node);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        fadeOut.setOnFinished(event -> fadeIn.play());

        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(durationInSeconds), node);
        scaleTransition.setToX(toX);
        scaleTransition.setToY(toY);

        ParallelTransition parallelTransition = new ParallelTransition(scaleTransition, fadeOut);
        parallelTransition.play();
    }
}
