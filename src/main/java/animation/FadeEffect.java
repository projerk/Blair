package animation;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.util.Duration;
import javafx.scene.layout.VBox;

public class FadeEffect {

    private FadeEffect() {

    }

    public static void fadeIn(Node node, double durationInSeconds) {
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(durationInSeconds), node);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
    }

    public static void fadeOut(Node node, double durationInSeconds) {
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(durationInSeconds), node);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.play();
    }

    public static void fadeInOut(Node node, double durationInSeconds) {
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(durationInSeconds / 2), node);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(durationInSeconds / 2), node);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        fadeIn.setOnFinished(event -> fadeOut.play());
        fadeIn.play();
    }

    public static void fadeOutIn(Node node, double durationInSeconds) {
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(durationInSeconds / 2), node);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(durationInSeconds / 2), node);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        fadeOut.setOnFinished(event -> fadeIn.play());
        fadeOut.play();
    }

    public static void fadeOutInAndChangeContent(VBox node, double durationInSeconds, VBox content) {
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(durationInSeconds / 2), node);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(durationInSeconds / 2), node);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        fadeOut.setOnFinished(event -> {
            node.getChildren().clear();
            node.getChildren().add(content);
            fadeIn.play();
        });
        fadeOut.play();
    }
}
