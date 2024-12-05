package animation;

import javafx.animation.ScaleTransition;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * A utility class for applying scaling and combined scaling-fading animations to JavaFX Nodes.
 * Provides methods to scale elements, scale with fade effects, and perform chained animations.
 */
public class ScaleEffect {

    // Private constructor to prevent instantiation (utility class).
    private ScaleEffect() {
    }

    /**
     * Scales a Node to the specified X and Y dimensions over a given duration.
     * @param node the Node to apply the scale effect.
     * @param durationInSeconds the duration of the scaling effect in seconds.
     * @param toX the target X scale factor.
     * @param toY the target Y scale factor.
     */
    public static void scaleTo(Node node, double durationInSeconds, double toX, double toY) {
        ScaleTransition scaleIn = new ScaleTransition(Duration.seconds(durationInSeconds), node);
        scaleIn.setToX(toX); // Set the target X scale
        scaleIn.setToY(toY); // Set the target Y scale
        scaleIn.play();      // Start the animation
    }

    /**
     * Scales a Node from specific X and Y dimensions to new X and Y dimensions over a given duration.
     * @param node the Node to apply the scale effect.
     * @param durationInSeconds the duration of the scaling effect in seconds.
     * @param fromX the starting X scale factor.
     * @param fromY the starting Y scale factor.
     * @param toX the target X scale factor.
     * @param toY the target Y scale factor.
     */
    public static void scaleOut(Node node, double durationInSeconds, double fromX, double fromY, double toX, double toY) {
        ScaleTransition scaleOut = new ScaleTransition(Duration.seconds(durationInSeconds), node);
        scaleOut.setFromX(fromX); // Set the starting X scale
        scaleOut.setFromY(fromY); // Set the starting Y scale
        scaleOut.setToX(toX);     // Set the target X scale
        scaleOut.setToY(toY);     // Set the target Y scale
        scaleOut.play();          // Start the animation
    }

    /**
     * Performs a scale-in and scale-out animation on a Node.
     * The Node scales to the target dimensions and then back to the original dimensions.
     * @param node the Node to apply the effect.
     * @param durationInSeconds the total duration of the scale-in and scale-out effect in seconds.
     * @param fromX the starting X scale factor.
     * @param toX the target X scale factor.
     * @param fromY the starting Y scale factor.
     * @param toY the target Y scale factor.
     */
    public static void scaleInOut(Node node, double durationInSeconds, double fromX, double toX, double fromY, double toY) {
        // Create the scale-in animation
        ScaleTransition scaleIn = new ScaleTransition(Duration.seconds(durationInSeconds / 2), node);
        scaleIn.setFromX(fromX);
        scaleIn.setToX(toX);
        scaleIn.setFromY(fromY);
        scaleIn.setToY(toY);

        // Create the scale-out animation
        ScaleTransition scaleOut = new ScaleTransition(Duration.seconds(durationInSeconds / 2), node);
        scaleOut.setFromX(toX);
        scaleOut.setToX(fromX);
        scaleOut.setFromY(toY);
        scaleOut.setToY(fromY);

        // Chain animations: scale-in is followed by scale-out
        scaleIn.setOnFinished(event -> scaleOut.play());
        scaleIn.play();
    }

    /**
     * Applies a combined scaling and fading animation to a Node.
     * The Node scales to the target dimensions while fading out, and then fades back in.
     * @param node the Node to apply the effect.
     * @param durationInSeconds the total duration of the effect in seconds.
     * @param toX the target X scale factor.
     * @param toY the target Y scale factor.
     */
    public static void scaleAndFade(Node node, double durationInSeconds, double toX, double toY) {
        // Create the fade-in animation
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(durationInSeconds / 2), node);
        fadeIn.setFromValue(0.0); // Start with fully transparent
        fadeIn.setToValue(1.0);   // End with fully visible

        // Create the fade-out animation
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(durationInSeconds / 2), node);
        fadeOut.setFromValue(1.0); // Start with fully visible
        fadeOut.setToValue(0.0);   // End with fully transparent

        // When fade-out finishes, trigger fade-in animation
        fadeOut.setOnFinished(event -> fadeIn.play());

        // Create the scaling animation
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(durationInSeconds), node);
        scaleTransition.setToX(toX); // Set target X scale
        scaleTransition.setToY(toY); // Set target Y scale

        // Combine scaling and fade-out animations
        ParallelTransition parallelTransition = new ParallelTransition(scaleTransition, fadeOut);
        parallelTransition.play(); // Start the combined animation
    }
}
