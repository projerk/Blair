package components.text;

import components.abstracts.Title;

/**
 * A class representing a custom book title with a specific text style.
 */
public class BookTitle extends Title {

    /**
     * Constructs a BookTitle object with the provided text.
     *
     * @param text the text of the book title.
     */
    public BookTitle(String text) {
        super(text);
        setCustomStyle();
    }

    /**
     * Sets the custom style for the book title, including text color and font family.
     */
    private void setCustomStyle() {
        this.setStyle("-fx-text-fill: black; -fx-font-family: 'Accent Graphic W00 Medium';");
    }
}
