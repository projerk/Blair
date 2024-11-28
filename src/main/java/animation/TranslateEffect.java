package animation;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class TranslateEffect {

    private TranslateEffect() {
        // Prevent instantiation
    }

    public static void translateTo(Node node, double durationInSeconds, double toX, double toY) {
        TranslateTransition translateIn = new TranslateTransition(Duration.seconds(durationInSeconds), node);
        translateIn.setToX(toX);
        translateIn.setToY(toY);
        translateIn.play();
    }

    public static void translateOut(Node node, double durationInSeconds, double fromX, double toX, double fromY, double toY) {
        TranslateTransition translateOut = new TranslateTransition(Duration.seconds(durationInSeconds), node);
        translateOut.setFromX(fromX);
        translateOut.setToX(toX);
        translateOut.setFromY(fromY);
        translateOut.setToY(toY);
        translateOut.play();
    }

    public static void translateInOut(Node node, double durationInSeconds, double fromX, double toX, double fromY, double toY) {
        TranslateTransition translateIn = new TranslateTransition(Duration.seconds(durationInSeconds / 2), node);
        translateIn.setFromX(fromX);
        translateIn.setToX(toX);
        translateIn.setFromY(fromY);
        translateIn.setToY(toY);

        TranslateTransition translateOut = new TranslateTransition(Duration.seconds(durationInSeconds / 2), node);
        translateOut.setFromX(toX);
        translateOut.setToX(fromX);
        translateOut.setFromY(toY);
        translateOut.setToY(fromY);

        translateIn.setOnFinished(event -> translateOut.play());
        translateIn.play();
    }

    public static void translateContentAndFade(Node node, double durationInSeconds, double toX, double toY) {
        
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(durationInSeconds / 2), node);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(durationInSeconds / 2), node);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        fadeOut.setOnFinished(event -> {
            //fronttrace.setText("Nội dung mới");
            fadeIn.play();
        });

        TranslateTransition translateIn = new TranslateTransition(Duration.seconds(durationInSeconds), node);
        translateIn.setToX(toX);
        translateIn.setToY(toY);

        ParallelTransition parallelTransition = new ParallelTransition(translateIn, fadeOut);
        parallelTransition.play();
    }
}
