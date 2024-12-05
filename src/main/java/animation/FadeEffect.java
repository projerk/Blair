package animation;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.util.Duration;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;

/**
 * A utility class for applying fade animations to JavaFX Nodes.
 * Provides methods for fading in, fading out, combining fade in/out animations,
 * and changing content dynamically with a fade effect.
 */
public class FadeEffect {

    // Private constructor to prevent instantiation (utility class).
    private FadeEffect() {

    }

    /**
     * Applies a fade-in effect to the specified Node.
     * @param node the Node to apply the fade-in effect.
     * @param durationInSeconds the duration of the fade-in effect in seconds.
     */
    public static void fadeIn(Node node, double durationInSeconds) {
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(durationInSeconds), node);
        fadeIn.setFromValue(0.0);  // Start with completely transparent
        fadeIn.setToValue(1.0);    // End with fully visible
        fadeIn.play();             // Start the animation
    }

    /**
     * Applies a fade-out effect to the specified Node.
     * @param node the Node to apply the fade-out effect.
     * @param durationInSeconds the duration of the fade-out effect in seconds.
     */
    public static void fadeOut(Node node, double durationInSeconds) {
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(durationInSeconds), node);
        fadeOut.setFromValue(1.0);  // Start with fully visible
        fadeOut.setToValue(0.0);    // End with completely transparent
        fadeOut.play();             // Start the animation
    }

    /**
     * Applies a combined fade-in and fade-out effect to the specified Node.
     * The fade-in animation is followed by a fade-out animation.
     * @param node the Node to apply the effect.
     * @param durationInSeconds the total duration of the fade-in and fade-out effect in seconds.
     */
    public static void fadeInOut(Node node, double durationInSeconds) {
        // Create the fade-in animation
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(durationInSeconds / 2), node);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        // Create the fade-out animation
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(durationInSeconds / 2), node);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        // Chain animations: fade-in is followed by fade-out
        fadeIn.setOnFinished(event -> fadeOut.play());
        fadeIn.play();
    }

    /**
     * Applies a combined fade-out and fade-in effect to the specified Node.
     * The fade-out animation is followed by a fade-in animation.
     * @param node the Node to apply the effect.
     * @param durationInSeconds the total duration of the fade-out and fade-in effect in seconds.
     */
    public static void fadeOutIn(Node node, double durationInSeconds) {
        // Create the fade-in animation
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(durationInSeconds / 2), node);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        // Create the fade-out animation
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(durationInSeconds / 2), node);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        // Chain animations: fade-out is followed by fade-in
        fadeOut.setOnFinished(event -> fadeIn.play());
        fadeOut.play();
    }

    /**
     * Applies a fade-out effect to a VBox, changes its content,
     * and then applies a fade-in effect to the new content.
     * @param node the VBox to apply the effect.
     * @param durationInSeconds the total duration of the fade-out, content change, and fade-in effect in seconds.
     * @param content the new content to set in the VBox after fading out.
     */
    public static void fadeOutInAndChangeContent(VBox node, double durationInSeconds, VBox content) {
        // Create the fade-in animation
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(durationInSeconds / 2), node);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        // Create the fade-out animation
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(durationInSeconds / 2), node);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        // On fade-out completion, change content and start fade-in animation
        fadeOut.setOnFinished(event -> {
            node.getChildren().clear();     // Clear the old content
            node.getChildren().add(content); // Add the new content
            fadeIn.play();                  // Start fade-in animation
        });
        fadeOut.play();                      // Start fade-out animation
    }
}
