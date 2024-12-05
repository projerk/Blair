package components.abstracts;

import javafx.beans.binding.Bindings;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

/**
 * An abstract class that extends {@link Label} to create a title component.
 * The font size of the title is dynamically bound to its width,
 * providing a responsive design.
 */
public abstract class Title extends Label {

    /**
     * Constructs a new {@code Title} with the specified text.
     * The font size of the label adjusts dynamically based on the width of the label.
     *
     * @param text the text to display in the title
     */
    public Title(String text) {
        super(text);

        // Listen for changes in the height property
        heightProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.doubleValue() > 0) {
                // When the height is greater than 0, bind the font size to the label's width
                fontProperty().bind(
                        Bindings.createObjectBinding(
                                () -> Font.font(getWidth() / 5),
                                widthProperty()
                        )
                );
            }
        });
    }
}
