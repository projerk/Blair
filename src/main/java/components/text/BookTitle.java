package components.text;

import components.abstracts.Title;

public class BookTitle extends Title {
    public BookTitle(String text) {
        super(text);
        setCustomStyle();
    }

    private void setCustomStyle() {
        this.setStyle("-fx-text-fill: black; -fx-font-family: 'Accent Graphic W00 Medium';");
    }
}
