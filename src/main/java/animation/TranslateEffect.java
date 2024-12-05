package animation;

import javafx.animation.TranslateTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * A utility class for applying translation and combined translation-fading animations to JavaFX Nodes.
 * Provides methods to move elements, move with fade effects, and perform chained animations.
 */
public class TranslateEffect {

    // Private constructor to prevent instantiation (utility class).
    private TranslateEffect() {
    }

    /**
     * Translates a Node to the specified X and Y coordinates over a given duration.
     * @param node the Node to apply the translation effect.
     * @param durationInSeconds the duration of the translation effect in seconds.
     * @param toX the target X coordinate.
     * @param toY the target Y coordinate.
     */
    public static void translateTo(Node node, double durationInSeconds, double toX, double toY) {
        TranslateTransition translateIn = new TranslateTransition(Duration.seconds(durationInSeconds), node);
        translateIn.setToX(toX); // Set the target X position
        translateIn.setToY(toY); // Set the target Y position
        translateIn.play();      // Start the animation
    }

    /**
     * Translates a Node from specific X and Y coordinates to new X and Y coordinates over a given duration.
     * @param node the Node to apply the translation effect.
     * @param durationInSeconds the duration of the translation effect in seconds.
     * @param fromX the starting X coordinate.
     * @param toX the target X coordinate.
     * @param fromY the starting Y coordinate.
     * @param toY the target Y coordinate.
     */
    public static void translateOut(Node node, double durationInSeconds, double fromX, double toX, double fromY, double toY) {
        TranslateTransition translateOut = new TranslateTransition(Duration.seconds(durationInSeconds), node);
        translateOut.setFromX(fromX); // Set the starting X position
        translateOut.setToX(toX);     // Set the target X position
        translateOut.setFromY(fromY); // Set the starting Y position
        translateOut.setToY(toY);     // Set the target Y position
        translateOut.play();          // Start the animation
    }

    /**
     * Performs a translate-in and translate-out animation on a Node.
     * The Node moves to the target coordinates and then back to the original coordinates.
     * @param node the Node to apply the effect.
     * @param durationInSeconds the total duration of the translate-in and translate-out effect in seconds.
     * @param fromX the starting X coordinate.
     * @param toX the target X coordinate.
     * @param fromY the starting Y coordinate.
     * @param toY the target Y coordinate.
     */
    public static void translateInOut(Node node, double durationInSeconds, double fromX, double toX, double fromY, double toY) {
        // Create the translate-in animation
        TranslateTransition translateIn = new TranslateTransition(Duration.seconds(durationInSeconds / 2), node);
        translateIn.setFromX(fromX);
        translateIn.setToX(toX);
        translateIn.setFromY(fromY);
        translateIn.setToY(toY);

        // Create the translate-out animation
        TranslateTransition translateOut = new TranslateTransition(Duration.seconds(durationInSeconds / 2), node);
        translateOut.setFromX(toX);
        translateOut.setToX(fromX);
        translateOut.setFromY(toY);
        translateOut.setToY(fromY);

        // Chain animations: translate-in is followed by translate-out
        translateIn.setOnFinished(event -> translateOut.play());
        translateIn.play();
    }

    /**
     * Applies a combined translation and fading animation to a Node.
     * The Node moves to the target coordinates while fading out, and then fades back in.
     * @param node the Node to apply the effect.
     * @param durationInSeconds the total duration of the effect in seconds.
     * @param toX the target X coordinate.
     * @param toY the target Y coordinate.
     */
    public static void translateContentAndFade(Node node, double durationInSeconds, double toX, double toY) {
        // Create the fade-in animation
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(durationInSeconds / 2), node);
        fadeIn.setFromValue(0.0); // Start with fully transparent
        fadeIn.setToValue(1.0);   // End with fully visible

        // Create the fade-out animation
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(durationInSeconds / 2), node);
        fadeOut.setFromValue(1.0); // Start with fully visible
        fadeOut.setToValue(0.0);   // End with fully transparent

        // When fade-out finishes, trigger fade-in animation
        fadeOut.setOnFinished(event -> {
            // Uncomment and replace with content update logic if needed
            // fronttrace.setText("New content");
            fadeIn.play();
        });

        // Create the translation animation
        TranslateTransition translateIn = new TranslateTransition(Duration.seconds(durationInSeconds), node);
        translateIn.setToX(toX); // Set target X position
        translateIn.setToY(toY); // Set target Y position

        // Combine translation and fade-out animations
        ParallelTransition parallelTransition = new ParallelTransition(translateIn, fadeOut);
        parallelTransition.play(); // Start the combined animation
    }
}
